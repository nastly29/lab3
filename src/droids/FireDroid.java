package droids;

import java.util.ArrayList;
import java.util.List;

public class FireDroid extends droids.Droid {
    private int fireDamage;

    public FireDroid(String name){
        super(name, 100, 25);
        this.fireDamage = 8;
    }

    public String toString(){
        return super.toString() +  "| Вогняні удари: " + fireDamage;
    }

    public List<String> attack(Droid droid) {
        List<String> log = new ArrayList<>();
        int totalDamage = calculateDamage();
        String attackMessage = formatAttackMessage(droid, totalDamage);
        String damageLog = droid.getDamaged(totalDamage);

        log.add(attackMessage);
        log.add(damageLog);
        return log;
    }

    // Метод для розрахунку загальної шкоди
    private int calculateDamage() {
        if (fireDamage > 0) {
            int totalDamage = getDamage() * 2;  // Подвоєна шкода при вогняній атаці
            fireDamage = Math.max(0, fireDamage - 2);  // Зменшити вогняну шкоду
            return totalDamage;
        }
        return getDamage();  // Звичайна атака
    }

    // Формування повідомлення про атаку
    private String formatAttackMessage(Droid targetDroid, int totalDamage) {
        String attackType = (fireDamage > 0) ? "потужний вогняний удар" : "звичайний удар";
        String message = this.getName() + " завдав " + attackType + " " + targetDroid.getName() + " на " + totalDamage + " одиниць шкоди.";
        System.out.println(message);
        return message;
    }
}
