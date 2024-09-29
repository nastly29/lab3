import droids.DoctorDroid;
import droids.Droid;
import java.util.*;
import colors.Console;

public class Battle {
    private final Random random = new Random();
    private final List<String> attackLog = new ArrayList<>();

    public List<Droid> oneVSone(Droid droid1, Droid droid2) {
        List<Droid> deadDroids = new ArrayList<>();
        String result;

        while (droid1.getHealth() > 0 && droid2.getHealth() > 0) {
            performAttack(droid1, droid2);
            if (droid2.getHealth() > 0) {
                performAttack(droid2, droid1);
            }
        }

        result = determineWinner(droid1, droid2);
        attackLog.add(result);
        return deadDroids;
    }

    public List<Droid> teamVSteam(List<Droid> team1, List<Droid> team2) {
        List<Droid> deadDroids = new ArrayList<>();
        String result;

        while (!team1.isEmpty() && !team2.isEmpty()) {
            Droid player1 = selectRandomDroid(team1);
            Droid opponent2 = selectRandomDroid(team2);

            performAttack(player1, opponent2);
            if (opponent2.getHealth() <= 0) {
                deadDroids.add(opponent2);
                team2.remove(opponent2);
            }

            if (!team2.isEmpty()) {
                Droid player2 = selectRandomDroid(team2);
                Droid opponent1 = selectRandomDroid(team1);

                performAttack(player2, opponent1);
                if (opponent1.getHealth() <= 0) {
                    deadDroids.add(opponent1);
                    team1.remove(opponent1);
                }
            }

            performRepairs(team1);
            performRepairs(team2);
        }

        result = team1.isEmpty() ? "\nВиграла команда №2!" : "\nВиграла команда №1!";
        logResult(result);
        return deadDroids;
    }

    private void performAttack(Droid attacker, Droid defender) {
        List<String> attackDetails = attacker.attack(defender);
        attackLog.addAll(attackDetails);
    }

    private void performRepairs(List<Droid> team) {
        for (Droid droid : team) {
            if (droid instanceof DoctorDroid doctor) {
                Droid teammate = selectRandomDroid(team);
                if (teammate != doctor && teammate.getHealth() > 0) {
                    String repairLog = doctor.repair(teammate);
                    attackLog.add(repairLog);
                }
            }
        }
    }

    private String determineWinner(Droid droid1, Droid droid2) {
        if (droid1.getHealth() <= 0) {
            return droid2.getName() + " виграв бій!";
        } else {
            return droid1.getName() + " виграв бій!";
        }
    }

    private void logResult(String result) {
        System.out.println(Console.GREEN + result + Console.RESET);
        attackLog.add(result);
    }

    private Droid selectRandomDroid(List<Droid> team) {
        return team.get(random.nextInt(team.size()));
    }

    // Method to get the attack log
    public List<String> getAttackLog() {
        return attackLog;
    }
}
