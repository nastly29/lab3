import droids.DoctorDroid;
import droids.Droid;
import droids.FireDroid;
import textoutput.Console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DroidManager {
    private static List<Droid> droids;
    private static Scanner scanner;

    public DroidManager(List<Droid> droids, Scanner scanner) {
        DroidManager.droids = droids;
        DroidManager.scanner = scanner;
    }

    //Метод для створення нового дроїда
    public void createDroid() {
        String name;
        while (true) {
            System.out.print("Введіть ім'я дроїда: ");
            name = scanner.next();
            if (UniqueName(name)) break;
            else System.out.println(Console.RED + "Дроїд з таким ім'ям вже існує. Введіть інше ім'я.\n" + Console.RESET);
        }

        int type;
        while(true){
            System.out.print("Виберіть тип дроїда (1 - DoctorDroid, 2 - FireDroid): ");
            type = getInt();

            if(type == 1 || type == 2) break;
            else System.out.println(Console.RED+"Неправильний вибір, спробуйте знову!\n"+ Console.RESET);
        }

        // Створюємо дроїда відповідного типу
        Droid droid = (type == 1) ? new DoctorDroid(name) : new FireDroid(name);
        droids.add(droid);
        System.out.println("\nДроїд " + Console.YELLOW + droid.getName() + Console.RESET + " успішно створений!\n");
    }

    //Метод для перевірки унікальності імені дроїда
    private static boolean UniqueName(String name) {
        for (Droid droid : droids) {
            if (droid.getName().equalsIgnoreCase(name)) {
                return false; // Якщо ім'я вже існує
            }
        }
        return true; // Якщо ім'я унікальне
    }

    //Метод для вибору дроїда за індексом зі списку
    public int selectDroid(String text) {
        Console.showDroids(droids);
        System.out.print(text);
        int index = getInt() - 1;
        if (index < 0 || index >= droids.size()) {
            System.out.println(Console.RED+"Неправильний вибір!"+ Console.RESET+"\n");
            return selectDroid(text);
        }
        System.out.println("\n");
        return index;
    }

    // Метод для додавання дроїдів у команду для командного бою
    public List<Droid> addMember(List<Droid> team2, String text){
        System.out.println(Console.BOLD + text + Console.RESET);
        List<Droid> team = new ArrayList<>();

        while(true){
            Console.showDroids(droids);
            System.out.print("Виберіть номер дроїда(0 для завершення): ");
            int index1 = getInt();

            if(index1 == 0) break;
            if(index1 > 0 && index1 <= droids.size()){
                Droid chooseDroid = droids.get(index1 - 1);
                if(team.contains(chooseDroid) || team2.contains(chooseDroid) )
                    System.out.println(Console.RED+"Цей дроїд вже був обраний!"+ Console.RESET);
                else {
                    team.add(chooseDroid);
                    System.out.println("Дроїд "+ Console.YELLOW + chooseDroid.getName() + Console.RESET+ " доданий до команди.");
                }
            } else
                System.out.println(Console.RED+"Неправильний вибір!"+ Console.RESET);
        }
        return team;
    }

    //Метод для безпечного вводу цілих чисел
    public int getInt(){
        while(true){
            try{
                return scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println(Console.RED+"Неправильний ввід!Будь ласка, введіть ціле число."+ Console.RESET);
                scanner.next();
            }
        }
    }
}
