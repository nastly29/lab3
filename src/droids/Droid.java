package droids;
import colors.Console;

import java.util.ArrayList;
import java.util.List;

public class Droid {
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

    public String getName() {
        return Console.YELLOW + name + Console.RESET;
    }

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
        return "*** Дроїд: " + getName() + " *** Здоров'я: " + health + "| Пошкодження: " + damage;
    }


    public List<String> attack(Droid droid){
        List<String> log = new ArrayList<>();
        String attackMessage = this.getName() + " аткакує " + droid.getName() + " на " + damage + " одиниць шкоди.";
        System.out.println(attackMessage);
        String damageLog = droid.getDamaged(damage);
        log.add(attackMessage);
        log.add(damageLog);
        return log;
    }

    public String getDamaged(int damage){
        health -= damage;
        String damageLog = this.getName() + " отримав " + damage + " одиниць шкоди. Здоров'я: "+ health + "\n";
        System.out.println(damageLog);
        if(health <= 0) {
            String text2 = this.getName() + " знищено!\n";
            System.out.println(Console.RED + text2 + Console.RESET);
            return text2;
        }
        return damageLog;
    }
}
