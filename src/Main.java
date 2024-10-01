import droids.Droid;
import droids.DoctorDroid;
import droids.FireDroid;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BOLD = "\033[1m";

    static List<Droid> droids = new ArrayList<>();  //список всіх створених дроїдів
    static Scanner scanner = new Scanner(System.in);
    static List<String> battleLog = new ArrayList<>();

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

    //Меню
    public static void main(String[] args) {
        while (true) {
            System.out.println("====================================");
            System.out.println(BOLD + "1. Створити дроїда" + RESET);
            System.out.println(BOLD + "2. Показати список дроїдів" + RESET);
            System.out.println(BOLD + "3. Запустити бій 1 на 1" + RESET);
            System.out.println(BOLD + "4. Запустити бій команда на команду" + RESET);
            System.out.println(BOLD + "5. Зберегти бій у файл" + RESET);
            System.out.println(BOLD + "6. Відтворити бій з файлу" + RESET);
            System.out.println(BOLD + "7. Вийти" + RESET);

            System.out.print(GREEN+"Ваш вибір -> "+RESET);
            int choice = getInt();
            System.out.println("====================================");

            switch (choice) {
                case 1:
                    createDroid();
                    break;
                case 2:
                    showDroids();
                    break;
                case 3:
                    Battle_1();
                    break;
                case 4:
                    Battle_2();
                    break;
                case 5:
                    saveBattleToFile();
                    break;
                case 6:
                    printFileContent();
                    break;
                case 7:
                    System.out.println(GREEN + "\nВихід з гри." + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED+"Неправильний вибір, спробуйте знову."+RESET);
                    break;
            }
        }
    }

    //Метод для створення нового дроїда
    private static void createDroid() {
        String name;

        while (true) {
            System.out.print("Введіть ім'я дроїда: ");
            name = scanner.next();
            if (UniqueName(name)) break;
            else System.out.println(RED + "Дроїд з таким ім'ям вже існує. Введіть інше ім'я.\n" + RESET);
        }

        int type;
        while(true){
            System.out.print("Виберіть тип дроїда (1 - DoctorDroid, 2 - FireDroid): ");
            type = getInt();

            if(type == 1 || type == 2) break;
            else System.out.println(RED+"Неправильний вибір, спробуйте знову!\n"+RESET);
        }

        // Створюємо дроїда відповідного типу
        Droid droid = (type == 1) ? new DoctorDroid(name) : new FireDroid(name);
        droids.add(droid);
        System.out.println("\nДроїд " + YELLOW + droid.getName() + RESET + " успішно створений!\n");
    }

    private static boolean UniqueName(String name) {
        for (Droid droid : droids) {
            if (droid.getName().equalsIgnoreCase(name)) {
                return false; // Якщо ім'я вже існує
            }
        }
        return true; // Якщо ім'я унікальне
    }


    //Метод для виведення списку всіх дродів
    private static void showDroids() {
        if(droids.isEmpty()){
            System.out.println(RED+"Список дроїдів порожній."+RESET);
            return;
        }
        System.out.println(BOLD + "Список дроїдів:" + RESET);
        for (int i = 0; i < droids.size(); i++) {
            System.out.println((i + 1) + ". " + droids.get(i));
        }
    }

    //Метод для проведення бою 1 на 1
    private static void Battle_1() {
        if (droids.size() < 2) {
            System.out.println(RED + "Для бою 1 на 1 потрібно щонайменше 2 дроїда." + RESET);
            return;
        }

        int index1 = selectDroid(BOLD + "Виберіть 1 дроїда: " + RESET);
        int index2 = selectDroid(BOLD + "Виберіть 2 дроїда: " + RESET);

        if (index1 == index2) {
            System.out.println(RED + "Не можна вибрати одного і того ж дроїда для бою!" + RESET);
            return;
        }

        Droid droid1 = droids.get(index1);
        Droid droid2 = droids.get(index2);

        System.out.println(GREEN+"Бій розпочинається!\n"+RESET);
        Battle battle = new Battle();
        List<Droid> deadDroids = battle.oneVSone(droid1, droid2);

        if (!battleLog.isEmpty() && battleLog.size() > 1){
            battleLog.clear();
        }
        battleLog.addAll(battle.getAttackLog());

        for (Droid dead_droid : deadDroids)
            droids.remove(dead_droid);
    }

    //Метод для вибору дроїда за індексом зі списку
    private static int selectDroid(String text) {
        showDroids();
        System.out.print(text);
        int index = getInt() - 1;
        if (index < 0 || index >= droids.size()) {
            System.out.println(RED+"Неправильний вибір!"+RESET+"\n");
            return selectDroid(text);
        }
        System.out.println("\n");
        return index;
    }

    // Метод для додавання дроїдів у команду для командного бою
    private static List<Droid> addMember(List<Droid> team2, String text){
        System.out.println(BOLD + text + RESET);
        List<Droid> team = new ArrayList<>();

        while(true){
            showDroids();
            System.out.print("Виберіть номер дроїда(0 для завершення): ");
            int index1 = getInt();

            if(index1 == 0) break;
            if(index1 > 0 && index1 <= droids.size()){
                Droid chooseDroid = droids.get(index1 - 1);
                if(team.contains(chooseDroid) || team2.contains(chooseDroid) )
                    System.out.println(RED+"Цей дроїд вже був обраний!"+RESET);
                else {
                    team.add(chooseDroid);
                    System.out.println("Дроїд "+ YELLOW + chooseDroid.getName() + RESET+ " доданий до команди.");
                }
            } else
                System.out.println(RED+"Неправильний вибір!"+ RESET);
        }
        return team;
    }



    //Метод для провдедення бою командами
    private static void Battle_2(){
        if(droids.size() < 2){
            System.out.println(RED+"Для бою командами потрібно щонайменше 2 дроїда."+RESET);
            return;
        }

        List<Droid> team1 = new ArrayList<>();
        List<Droid> team2 = new ArrayList<>();

        team1 = addMember(team2,"Оберіть склад 1 команди.");
        team2 = addMember(team1,"\nОберіть склад 2 команди.");

        if(team1.isEmpty() || team2.isEmpty()){
            System.out.println(RED+"Кожна команда має містити принаймі 1 дроїда."+RESET);
            return;
        }

        // Виводимо склад обох команд
        printTeam(team1, "1");
        printTeam(team2, "2");

        System.out.println(GREEN+"\nБій розпочинається!\n"+RESET);

        Battle battle = new Battle();
        List<Droid> deadDroids = battle.teamVSteam(team1, team2);

        if (!battleLog.isEmpty() && battleLog.size() > 1){
            battleLog.clear();
        }
        battleLog.addAll(battle.getAttackLog());

        for(Droid dead_droid: deadDroids)
            droids.remove(dead_droid);
    }

    //Вивід складу команди
    private static void printTeam(List<Droid> team, String teamName) {
        System.out.println("\nСклад " + teamName + " команди:");

        for (Droid droid : team) {
            String droidImage = getDroidImage(droid);  // Отримуємо малюнок дроїда
            System.out.println(droidImage + " " + droid);
        }
    }

    private static String getDroidImage(Droid droid) {
        if (droid instanceof DoctorDroid) {
            return "(\uD83E\uDDD1\u200D⚕\uFE0F DoctorDroid)";
        } else {
            return "(\uD83D\uDD25 FireDroid)";
        }
    }


    //Метод для безпечного вводу цілих чисел
    public static int getInt(){
        while(true){
            try{
                return scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println(RED+"Неправильний ввід!Будь ласка, введіть ціле число."+RESET);
                scanner.next();
            }
        }
    }

    private static void saveBattleToFile() {
        System.out.print("Введіть назву файлу для збереження бою: ");
        String filename = scanner.next();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String log : battleLog) {
                writer.write(log);
                writer.newLine();
            }
            System.out.println(GREEN + "Бій успішно збережено у файл: " + filename + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Сталася помилка при збереженні файлу!" + RESET);
        }
    }

    // Метод для зчитування та виведення вмісту файлу
    public static void printFileContent() {
        System.out.print("Введіть назву файлу для відтворення бою: ");
        String filename = scanner.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(RED + "Помилка при зчитуванні вмісту файлу!" + RESET);
        }
    }
}
