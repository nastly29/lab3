package droids;

import colors.Console;

public class DoctorDroid extends Droid {
    private final int countRepair;

    public DoctorDroid(String name) {
        super(name, 120, 20);
        this.countRepair = 30;
    }

    // Метод для ремонту іншого дроїда
    public String repair(Droid droid) {
        if (droid == null) {
            return logFailure("Невідомий дроїд");
        }

        if (droid.getHealth() <= 0) {
            return logFailure(droid.getName() + " мертвий і не може бути відновлений");
        }

        int newHealth = Math.min(droid.getHealth() + countRepair, droid.getMaxHealth());
        droid.setHealth(newHealth);

        String successMessage = this.getName() + " збільшив здоров'я для " + droid.getName() +
                " на " + countRepair + " одиниць. Нове здоров'я: " + newHealth + "\n";
        System.out.println(successMessage);
        return successMessage;
    }

    // Логування повідомлень про помилки
    private String logFailure(String reason) {
        String failureMessage = "Не вдалося відновити здоров'я: " + reason + "\n";
        System.out.println(Console.RED + failureMessage + Console.RESET);
        return failureMessage;
    }

    public String toString() {
        return super.toString() + " | Лікувальна сила: " + countRepair;
    }
}