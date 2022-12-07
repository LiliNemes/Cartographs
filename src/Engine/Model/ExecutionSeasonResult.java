package Engine.Model;

/**
 * Visszaadja, hogy sikeres-e egy execution (ValidationResult), illetve hogy évszak ért-e véget vele, melyik évszakban történt.
 */
public class ExecutionSeasonResult {

    ValidationResult vr;

    /**
     *
     * @return Melyik évszakban történt.
     */
    public Seasons getSeason() {
        return season;
    }

    /**
     *
     * @return true ha ezzel véget ért az évszak, false ha nem.
     */
    public boolean isEndOfSeason() {
        return endOfSeason;
    }

    boolean endOfSeason;
    Seasons season;

    /**
     * Konstruktor.
     * @param v ValidationResult
     * @param e Vége van-e az évszaknak.
     * @param s Az adott évszak.
     */
    public ExecutionSeasonResult(ValidationResult v, boolean e,  Seasons s) {
        this.vr=v;
        this.season=s;
        this.endOfSeason=e;
    }

    /**
     *
     * @return  sikeres-e egy execution.
     */
    public ValidationResult getVr() {
        return vr;
    }
}
