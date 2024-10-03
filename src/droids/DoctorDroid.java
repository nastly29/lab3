package droids;

public class DoctorDroid extends Droid {
    private final int countRepair;

    public DoctorDroid(String name) {
        super(name, 120, 20);
        this.countRepair = 30;
        this.isDoctorDroid = true;
    }

    public int getCountRepair(){ return countRepair;}

    public String toString() {
        return super.toString() + " | Лікувальна сила: " + countRepair;
    }
}