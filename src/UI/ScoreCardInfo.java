package UI;

/**
 * ScoreCard-ok neveit és leírásait tároló osztály.
 */
public class ScoreCardInfo {
    private final String name;
    private final String description;

    /**
     * Konstruktor.
     * @param name Név.
     * @param description Szabály/leírás.
     */
    public ScoreCardInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     *
     * @return Név.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Szabály/leírás.
     */
    public String getDescription() {
        return description;
    }
}
