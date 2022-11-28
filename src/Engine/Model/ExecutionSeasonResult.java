package Engine.Model;

public class ExecutionSeasonResult {


    ValidationResult vr;

    public boolean isEndOfSeason() {
        return endOfSeason;
    }

    boolean endOfSeason;

    public Seasons getSeason() {
        return season;
    }

    Seasons season;

    public ExecutionSeasonResult(ValidationResult v, boolean b, Seasons s) {
        vr=v;
        endOfSeason=b;
        season=s;
    }
    public ValidationResult getVr() {
        return vr;
    }
}
