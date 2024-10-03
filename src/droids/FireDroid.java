package droids;

public class FireDroid extends Droid {
    private int fireDamage;

    public FireDroid(String name){
        super(name, 100, 25);
        this.fireDamage = 8;
        this.isFireDroid = true;
    }

    public int getFireDamage() { return fireDamage;}

    public String toString(){
        return super.toString() +  "| Вогняні удари: " + fireDamage;
    }

    // Метод для розрахунку загальної шкоди
    public int calculateDamage() {
        if (fireDamage > 0) {
            int totalDamage = getDamage() * 2;  // Подвоєна шкода при вогняній атаці
            fireDamage = Math.max(0, fireDamage - 2);  // Зменшити вогняну шкоду
            return totalDamage;
        }
        return getDamage();  // Звичайна атака
    }
}
