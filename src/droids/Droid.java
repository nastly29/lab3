package droids;

public class Droid {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\033[0;33m";

    public String name;
    public int health;
    public int damage;
    public final int maxHealth;

    public Droid(String name, int health, int damage){
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
    }

    public String getName() { return name;}

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setHealth(int health){ this.health = health; }

    public String toString(){
        return "*** Дроїд: " + YELLOW + getName() + RESET + " *** Здоров'я: " + health + "| Пошкодження: " + damage;
    }


    public String attack(Droid droid){
        String attackMessage = YELLOW+this.getName() +RESET+ " аткакує " + YELLOW+droid.getName()
                +RESET+ " на " + damage + " одиниць шкоди.";
        System.out.println(attackMessage);
        String damageLog = droid.getDamaged(damage);
        return attackMessage + "\n" + damageLog;
    }

    public String getDamaged(int damage){
        health -= damage;
        String damageLog = YELLOW + this.getName() + RESET + " отримав " + damage + " одиниць шкоди. Здоров'я: "+ health + "\n";
        System.out.println(damageLog);
        if(health <= 0) {
            String text = YELLOW  + this.getName() + RESET + " знищено!\uD83D\uDE35\n";
            System.out.println(RED + text + RESET);
            return text;
        }
        return damageLog;
    }
}
