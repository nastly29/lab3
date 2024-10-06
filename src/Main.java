import droids.DoctorDroid;
import droids.Droid;
import droids.FireDroid;
import filemanagment.FileManager;
import textoutput.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Droid> droids = new ArrayList<>();  // список всіх створених дроїдів
    static Scanner scanner = new Scanner(System.in);
    static List<String> battleLog = new ArrayList<>();
    static DroidManager droidManager = new DroidManager(droids, scanner);

    static {
        droids.add(new DoctorDroid("MediBot"));
        droids.add(new DoctorDroid("Healix"));
        droids.add(new DoctorDroid("Docron"));
        droids.add(new DoctorDroid("Cureton"));
        droids.add(new FireDroid("Flare"));
        droids.add(new FireDroid("Blazex"));
        droids.add(new FireDroid("Ember"));
        droids.add(new FireDroid("Inferno"));
    }

    public static void main(String[] args) {
        while (true) {
            Console.menu();
            int choice = droidManager.getInt();
            System.out.println("====================================");

            switch (choice) {
                case 1:
                    droidManager.createDroid();
                    break;
                case 2:
                    Console.showDroids(droids);
                    break;
                case 3:
                    Battle_1();
                    break;
                case 4:
                    Battle_2();
                    break;
                case 5:
                    FileManager.saveBattleToFile(scanner, battleLog);
                    break;
                case 6:
                    FileManager.printFileContent(scanner);
                    break;
                case 7:
                    System.out.println(Console.GREEN + "\nВихід з гри." + Console.RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Console.RED + "Неправильний вибір, спробуйте знову." + Console.RESET);
                    break;
            }
        }
    }

    // Метод для проведення бою 1 на 1
    private static void Battle_1() {
        if (droids.size() < 2) {
            System.out.println(Console.RED + "Для бою 1 на 1 потрібно щонайменше 2 дроїда." + Console.RESET);
            return;
        }

        int index1 = droidManager.selectDroid(Console.BOLD + "Виберіть 1 дроїда: " + Console.RESET);
        int index2 = droidManager.selectDroid(Console.BOLD + "Виберіть 2 дроїда: " + Console.RESET);

        if (index1 == index2) {
            System.out.println(Console.RED + "Не можна вибрати одного і того ж дроїда для бою!" + Console.RESET);
            return;
        }

        Droid droid1 = droids.get(index1);
        Droid droid2 = droids.get(index2);

        System.out.println(Console.GREEN + "Бій розпочинається!\n" + Console.RESET);
        Battle battle = new Battle();
        List<Droid> deadDroids = battle.oneVSone(droid1, droid2);

        if (!battleLog.isEmpty() && battleLog.size() > 1) {
            battleLog.clear();
        }
        battleLog.addAll(battle.getAttackLog());

        for (Droid dead_droid : deadDroids)
            droids.remove(dead_droid);
    }

    // Метод для провдедення бою командами
    private static void Battle_2() {
        if (droids.size() < 2) {
            System.out.println(Console.RED + "Для бою командами потрібно щонайменше 2 дроїда." + Console.RESET);
            return;
        }

        List<Droid> team1 = droidManager.addMember(new ArrayList<>(), "Оберіть склад 1 команди.");
        List<Droid> team2 = droidManager.addMember(team1, "\nОберіть склад 2 команди.");

        if (team1.isEmpty() || team2.isEmpty()) {
            System.out.println(Console.RED + "Кожна команда має містити принаймі 1 дроїда." + Console.RESET);
            return;
        }

        // Виводимо склад обох команд
        Console.printTeam(team1, "1");
        Console.printTeam(team2, "2");

        System.out.println(Console.GREEN + "\nБій розпочинається!\n" + Console.RESET);

        Battle battle = new Battle();
        List<Droid> deadDroids = battle.teamVSteam(team1, team2);

        if (!battleLog.isEmpty() && battleLog.size() > 1) {
            battleLog.clear();
        }
        battleLog.addAll(battle.getAttackLog());

        for (Droid dead_droid : deadDroids)
            droids.remove(dead_droid);
    }
}
