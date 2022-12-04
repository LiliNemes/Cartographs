package Engine.Model;

public class ExecutionSeasonResult {


    ValidationResult vr;

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getMoney() {
        return money;
    }

    public int getMonster() { return monster; }

    int a;
    int b;
    int money;
    int monster;
    public Seasons getSeason() {
        return season;
    }

    public boolean isEndOfSeason() {
        return endOfSeason;
    }

    boolean endOfSeason;
    Seasons season;

    public ExecutionSeasonResult(ValidationResult v, boolean e, int one, int two, int m, int monster, Seasons s) {
        this.vr=v;
        this.a=one;
        this.b=two;
        this.season=s;
        this.endOfSeason=e;
        this.money=m;
        this.monster = monster;
    }
    public ValidationResult getVr() {
        return vr;
    }
}
