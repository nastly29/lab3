package droids;

public class DoctorDroid extends Droid {
    private final int countRepair;

    public DoctorDroid(String name) {
        super(name, 120, 20);
        this.countRepair = 30;
    }

    // Метод для відновлення іншого дроїда
    public String repair(Droid droid) {
        if (droid == null) {
            return logFailure("Невідомий дроїд");
        }

        if (droid.getHealth() <= 0) {
            return logFailure(droid.getName() + " мертвий і не може бути відновлений");
        }

        int newHealth = Math.min(droid.getHealth() + countRepair, droid.getMaxHealth());
        droid.setHealth(newHealth);

        String successMessage = YELLOW+this.getName() +RESET+ " збільшив здоров'я для " + YELLOW+droid.getName() +RESET+
                " на " + countRepair + " одиниць. Нове здоров'я: " + newHealth + "\n";
        System.out.println(successMessage);
        return successMessage;
    }

    private String logFailure(String reason) {
        String failureMessage = "Не вдалося відновити здоров'я: " + reason + "\n";
        System.out.println(RED + failureMessage + RESET);
        return failureMessage;
    }

    public String toString() {
        return super.toString() + " | Лікувальна сила: " + countRepair;
    }
}