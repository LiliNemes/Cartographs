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

    int a;

    int b;
    int money;
    public Seasons getSeason() {
        return season;
    }

    public boolean isEndOfSeason() {
        return endOfSeason;
    }

    boolean endOfSeason;
    Seasons season;

    public ExecutionSeasonResult(ValidationResult v, boolean e, int one, int two, int m, Seasons s) {
        vr=v;
        a=one;
        b=two;
        season=s;
        endOfSeason=e;
        money=m;
    }
    public ValidationResult getVr() {
        return vr;
    }
}
