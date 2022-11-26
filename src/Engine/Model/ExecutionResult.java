package Engine.Model;

/**
 * Visszaadja, hogy sikeres-e egy execution (ValidationResult), illetve ha igen akkor hány aranyat eredményez.
 */
public class ExecutionResult {
    private ValidationResult result;
    private int goldYield;

    /**
     * Konstruktor.
     * @param vr Validáció eredménye.
     * @param g Hány arany jár érte.
     */
    public ExecutionResult(ValidationResult vr, int g) {
        result=vr;
        goldYield=g;
    }

    /**
     *
     * @return Validáció eredménye.
     */
    public ValidationResult getResult() {
        return result;
    }

    /**
     *
     * @return Hány aranyat eredményezett a lépés.
     */
    public int getGoldYield() {
        return goldYield;
    }

    /**
     * Két ExecutionResult egyenlőségét vizsgálja.
     * @param other A másik ExecutionResult.
     * @return True ha egyenlőek, false ha nem.
     */
    public boolean Equals(ExecutionResult other){
        if(other.getGoldYield()==goldYield && other.getResult()==result)
            return true;
        return false;
    }
}
