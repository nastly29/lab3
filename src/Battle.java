import droids.DoctorDroid;
import droids.Droid;
import droids.FireDroid;
import textoutput.Console;
import java.util.*;

public class Battle {
    private final Random random = new Random();
    private final List<String> attackLog = new ArrayList<>();

    //Метод для бою один на один
    public List<Droid> oneVSone(Droid droid1, Droid droid2) {
        List<Droid> deadDroids = new ArrayList<>();
        String result;

        while (droid1.isAlive() && droid2.isAlive()) {
            performAttack(droid1, droid2);
            if (!droid2.isAlive()) {
                deadDroids.add(droid2);
                break;
            }
            performAttack(droid2, droid1);
            if (!droid1.isAlive()) {
                deadDroids.add(droid1);
                break;
            }
        }
        if (!droid1.isAlive()) {
            result = Console.YELLOW + droid2.getName() + Console.RESET + " виграв бій!\uD83E\uDD47";
        } else {
            result = Console.YELLOW + droid1.getName() + Console.RESET + " виграв бій!\uD83E\uDD47";
        }
        logResult(result);
        return deadDroids;
    }

    //Метод для бою командами
    public List<Droid> teamVSteam(List<Droid> team1, List<Droid> team2) {
        List<Droid> deadDroids = new ArrayList<>();
        String result;

        while (!team1.isEmpty() && !team2.isEmpty()) {
            Droid player1 = selectRandomDroid(team1);
            Droid opponent2 = selectRandomDroid(team2);

            performAttack(player1, opponent2);
            if (!opponent2.isAlive()) {
                deadDroids.add(opponent2);
                team2.remove(opponent2);
            }

            if (!team2.isEmpty()) {
                Droid player2 = selectRandomDroid(team2);
                Droid opponent1 = selectRandomDroid(team1);

                performAttack(player2, opponent1);
                if (!opponent1.isAlive()) {
                    deadDroids.add(opponent1);
                    team1.remove(opponent1);
                }
            }

            performRepairs(team1);
            performRepairs(team2);
        }
        if (team1.isEmpty()) {
            result = "\nВиграла команда №2!\uD83E\uDD47";
        } else {
            result = "\nВиграла команда №1!\uD83E\uDD47";
        }
        logResult(result);
        return deadDroids;
    }

    //Виконання атаки відповідно до виду дроїда
    private void performAttack(Droid attacker, Droid defender) {
        String attackDetails;
        if (attacker.isFireDroid) {
            attackDetails = fireAttack((FireDroid) attacker, defender);
        } else {
            attackDetails = attack(attacker, defender);
        }
        attackLog.add(attackDetails);
    }

    //Метод для зцілення дроїда
    private void performRepairs(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.isDoctorDroid) {
                Droid teammate = selectRandomDroid(team);
                if (teammate != droid && teammate.getHealth() > 0) {
                    String repairLog = repair((DoctorDroid) droid, teammate);
                    attackLog.add(repairLog);
                }
            }
        }
    }

    private void logResult(String result) {
        System.out.println(Console.GREEN + result + Console.RESET);
        attackLog.add(result);
    }

    //Метод для вибору випадкового дроїда з команди
    private Droid selectRandomDroid(List<Droid> team) {
        return team.get(random.nextInt(team.size()));
    }

    public List<String> getAttackLog() {
        return attackLog;
    }

    //Метод для звичайної атаки
    public static String attack(Droid attacker, Droid defender) {
        String attackMessage = Console.YELLOW + attacker.getName() + Console.RESET +
                " атакує " + Console.YELLOW + defender.getName() +
                Console.RESET + " на " + attacker.getDamage() + " одиниць шкоди.";
        System.out.println(attackMessage);

        String damageLog = defender.getDamaged(attacker.getDamage());
        return attackMessage + "\n" + damageLog;
    }

    //Метод для атаки FireDroid
    public static String fireAttack(FireDroid attacker, Droid defender) {
        int totalDamage = attacker.calculateDamage();
        String attackMessage = formatAttackMessage(attacker, defender, totalDamage);
        String damageLog = defender.getDamaged(totalDamage);
        return attackMessage + "\n" + damageLog;
    }

    // Формування повідомлення про атаку FireDroid
    private static String formatAttackMessage(FireDroid attacker, Droid defender, int totalDamage) {
        String attackType = (attacker.getFireDamage() > 0) ? "потужний вогняний удар" : "звичайний удар";
        String message = Console.YELLOW + attacker.getName() + Console.RESET + " завдав " + attackType + " " +
                Console.YELLOW + defender.getName() + Console.RESET + " на " + totalDamage + " одиниць шкоди.";
        System.out.println(message);
        return message;
    }

    // Метод для відновлення іншого дроїда
    public static String repair(DoctorDroid doctor, Droid droid) {
        if (droid == null) {
            return logFailure("Невідомий дроїд");
        }

        if (droid.getHealth() <= 0) {
            return logFailure(droid.getName() + " мертвий і не може бути відновлений");
        }

        int countRepair = doctor.getCountRepair();
        int newHealth = Math.min(droid.getHealth() + countRepair, droid.getMaxHealth());
        droid.setHealth(newHealth);

        String successMessage = Console.YELLOW + doctor.getName() + Console.RESET +
                " збільшив здоров'я для " + Console.YELLOW + droid.getName() + Console.RESET +
                " на " + countRepair + " одиниць. Нове здоров'я: " + newHealth + "\n";
        System.out.println(successMessage);
        return successMessage;
    }

    private static String logFailure(String reason) {
        String failureMessage = "Не вдалося відновити здоров'я: " + reason + "\n";
        System.out.println(Console.RED + failureMessage + Console.RESET);
        return failureMessage;
    }
}