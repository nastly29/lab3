import droids.Droid;
import droids.DoctorDroid;
import droids.FireDroid;
import colors.Console;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
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
            System.out.println(Console.BOLD + "1. Створити дроїда" + Console.RESET);
            System.out.println(Console.BOLD + "2. Показати список дроїдів" + Console.RESET);
            System.out.println(Console.BOLD + "3. Запустити бій 1 на 1" + Console.RESET);
            System.out.println(Console.BOLD + "4. Запустити бій команда на команду" + Console.RESET);
            System.out.println(Console.BOLD + "5. Зберегти бій у файл" + Console.RESET);
            System.out.println(Console.BOLD + "6. Відтворити бій з файлу" + Console.RESET);
            System.out.println(Console.BOLD + "7. Вийти" + Console.RESET);

            System.out.print(Console.GREEN+"Ваш вибір -> "+Console.RESET);
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
                    System.out.println(Console.GREEN + "\nВихід з гри." + Console.RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Console.RED+"Неправильний вибір, спробуйте знову."+Console.RESET);
                    break;
            }
        }
    }

    //Метод для створення нового дроїда
    private static void createDroid() {
        String name;
        while(true){
            System.out.print("Введіть ім'я дроїда: ");
            name = scanner.next();
            if(UniqueName(name)) break;
            else System.out.println(Console.RED+"Дроїд з таким ім'ям вже існує. Введіть інше ім'я.\n"+Console.RESET);
        }

        int type;
        while(true){
            System.out.print("Виберіть тип дроїда (1 - DoctorDroid, 2 - FireDroid): ");
            type = getInt();

            if(type == 1 || type == 2) break;
            else System.out.println(Console.RED+"Неправильний вибір, спробуйте знову!\n"+Console.RESET);
        }

        // Створюємо дроїда відповідного типу
        Droid droid = (type == 1) ? new DoctorDroid(name) : new FireDroid(name);
        droids.add(droid);
        System.out.println("\nДроїд " + droid.getName() + " успішно створений!\n");
    }

    //Метод для перевірки унікальності ім'я дроїда
    private static boolean UniqueName(String name){
        for(Droid droid: droids){
            if(droid.getName().equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    //Метод для виведення списку всіх дродів
    private static void showDroids() {
        if(droids.isEmpty()){
            System.out.println(Console.RED+"Список дроїдів порожній."+Console.RESET);
            return;
        }
        System.out.println(Console.BOLD + "Список дроїдів:" + Console.RESET);
        for (int i = 0; i < droids.size(); i++) {
            System.out.println((i + 1) + ". " + droids.get(i));
        }
    }

    //Метод для проведення бою 1 на 1
    private static void Battle_1() {
        if (droids.size() < 2) {
            System.out.println(Console.RED + "Для бою 1 на 1 потрібно щонайменше 2 дроїда." + Console.RESET);
            return;
        }

        int index1 = selectDroid(Console.BOLD + "Виберіть 1 дроїда: " + Console.RESET);
        int index2 = selectDroid(Console.BOLD + "Виберіть 2 дроїда: " + Console.RESET);

        if (index1 == index2) {
            System.out.println(Console.RED + "Не можна вибрати одного і того ж дроїда для бою!" + Console.RESET);
            return;
        }

        Droid droid1 = droids.get(index1);
        Droid droid2 = droids.get(index2);

        System.out.println(Console.GREEN+"\nБій розпочинається!\n"+Console.RESET);
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
            System.out.println(Console.RED+"Неправильний вибір!"+Console.RESET+"\n");
            return selectDroid(text);
        }
        System.out.println("\n");
        return index;
    }

    // Метод для додавання дроїдів у команду для командного бою
    private static List<Droid> addMember(List<Droid> team2, String text){
        System.out.println(Console.BOLD + text + Console.RESET);
        List<Droid> team = new ArrayList<>();

        while(true){
            showDroids();
            System.out.print("Виберіть номер дроїда(0 для завершення): ");
            int index1 = getInt();

            if(index1 == 0) break;
            if(index1 > 0 && index1 <= droids.size()){
                Droid chooseDroid = droids.get(index1 - 1);
                if(team.contains(chooseDroid) || team2.contains(chooseDroid) )
                    System.out.println(Console.RED+"Цей дроїд вже був обраний!"+Console.RESET);
                else {
                    team.add(chooseDroid);
                    System.out.println("Дроїд "+ chooseDroid.getName() + " доданий до команди.");
                }
            } else
                System.out.println(Console.RED+"Неправильний вибір!"+Console.RESET);
        }
        return team;
    }

    //Метод для провдедення бою командами
    private static void Battle_2(){
        if(droids.size() < 2){
            System.out.println(Console.RED+"Для бою командами потрібно щонайменше 2 дроїда."+Console.RESET);
            return;
        }

        List<Droid> team1 = new ArrayList<>();
        List<Droid> team2 = new ArrayList<>();

        team1 = addMember(team2,"Оберіть склад 1 команди.");
        team2 = addMember(team1,"\nОберіть склад 2 команди.");

        if(team1.isEmpty() || team2.isEmpty()){
            System.out.println(Console.RED+"Кожна команда має містити принаймі 1 дроїда."+Console.RESET);
            return;
        }

        // Виводимо склад обох команд
        printTeam(team1, "1");
        printTeam(team2, "2");

        System.out.println(Console.GREEN+"\nБій розпочинається!\n"+Console.RESET);

        Battle battle = new Battle();
        List<Droid> deadDroids = battle.teamVSteam(team1, team2);

        if (!battleLog.isEmpty() && battleLog.size() > 1){
            battleLog.clear();
        }
        battleLog.addAll(battle.getAttackLog());

        for(Droid dead_droid: deadDroids)
            droids.remove(dead_droid);
    }

    private static void printTeam(List<Droid> team, String teamName) {
        System.out.println("\nСклад " + teamName + " команди:");
        for (Droid droid : team) {
            System.out.println(droid);
        }
    }

    //Метод для безпечного вводу цілих чисел
    public static int getInt(){
        while(true){
            try{
                return scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println(Console.RED+"Неправильний ввід!Будь ласка, введіть ціле число."+Console.RESET);
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
            System.out.println(Console.GREEN + "Бій успішно збережено у файл: " + filename + Console.RESET);
        } catch (IOException e) {
            System.out.println(Console.RED + "Сталася помилка при збереженні файлу!" + Console.RESET);
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
            System.out.println(Console.RED + "Помилка при зчитуванні вмісту файлу!" + Console.RESET);
        }
    }
}
