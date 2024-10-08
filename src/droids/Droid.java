package droids;
import textoutput.Console;

public class Droid {
    public String name;
    public int health;
    public int damage;
    public final int maxHealth;
    public boolean isFireDroid = false;
    public boolean isDoctorDroid = false;

    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String toString() {
        return "*** Дроїд: " + Console.YELLOW + getName() + Console.RESET + " *** Здоров'я: " + health + " | Пошкодження: " + damage;
    }

    //Метод для обробки отриманого пошкодження
    public String getDamaged(int damage) {
        health -= damage;
        String damageLog = Console.YELLOW + this.getName() + Console.RESET + " отримав " + damage + " одиниць шкоди. Здоров'я: " + health + "\n";
        System.out.println(damageLog);
        if (health <= 0) {
            String text = Console.YELLOW + this.getName() + Console.RESET + " знищено!\uD83D\uDE35\n";
            System.out.println(Console.RED + text + Console.RESET);
            return text;
        }
        return damageLog;
    }

    public boolean isAlive() { return health > 0; }
}