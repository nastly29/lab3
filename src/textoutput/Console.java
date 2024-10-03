package textoutput;
import java.util.List;
import droids.DoctorDroid;
import droids.Droid;

public class Console {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BOLD = "\033[1m";

    //Меню
    public static void menu(){
        System.out.println("====================================");
        System.out.println(BOLD + "1. Створити дроїда" + RESET);
        System.out.println(BOLD + "2. Показати список дроїдів" + RESET);
        System.out.println(BOLD + "3. Запустити бій 1 на 1" + RESET);
        System.out.println(BOLD + "4. Запустити бій команда на команду" + RESET);
        System.out.println(BOLD + "5. Зберегти бій у файл" + RESET);
        System.out.println(BOLD + "6. Відтворити бій з файлу" + RESET);
        System.out.println(BOLD + "7. Вийти" + RESET);
        System.out.print(GREEN+"Ваш вибір -> "+ RESET);
    }

    //Метод для виведення всього списку дроїдів
    public static void showDroids(List<Droid> droids) {
        if (droids.isEmpty()) {
            System.out.println(RED+"Список дроїдів порожній."+RESET);
            return;
        }
        System.out.println(BOLD + "Список дроїдів:" + RESET);
        for (int i = 0; i < droids.size(); i++) {
            System.out.println((i + 1) + ". " + droids.get(i));
        }
    }

    //Метод для виведення складу команди
    public static void printTeam(List<Droid> team, String teamName) {
        System.out.println("\nСклад " + teamName + " команди:");
        for (Droid droid : team) {
            String droidImage = getDroidImage(droid);  // Отримуємо малюнок дроїда
            System.out.println(droidImage + " " + droid);
        }
    }

    //Отримання малюнку дроїда відповідно до його виду
    private static String getDroidImage(Droid droid) {
        if (droid instanceof DoctorDroid) {
            return "(\uD83E\uDDD1\u200D⚕\uFE0F DoctorDroid)";
        } else {
            return "(\uD83D\uDD25 FireDroid)";
        }
    }
}
