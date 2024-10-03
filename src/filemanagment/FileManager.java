package filemanagment;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    //Метод для збереження бою у файл
    public static void saveBattleToFile(Scanner scanner, List<String> battleLog) {
        System.out.print("Введіть ім'я файлу для збереження: ");
        String filename = scanner.next();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String log : battleLog) {
                writer.write(log);
                writer.newLine();
            }
            System.out.println("Бій успішно збережено у файл: " + filename);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні бою у файл!");
        }
    }

    //Метод для виведення бою з файлу
    public static void printFileContent(Scanner scanner) {
        System.out.print("Введіть ім'я файлу для відтворення бою: ");
        String filename = scanner.next();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні вмісту файлу!");
        }
    }
}
