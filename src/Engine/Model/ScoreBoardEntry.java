package Engine.Model;

import java.io.Serializable;

/**
 * A ranglistának 1 bejegyzését megvalósító osztály.
 */
public class ScoreBoardEntry implements Serializable {
    private final String name;
    private final int points;

    /**
     * Konstruktor.
     * @param name A játékos neve.
     * @param points A játékos által elért pontok.
     */
    public ScoreBoardEntry(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     *
     * @return A játékos neve.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return A játékos által elért pontok.
     */
    public int getPoints() {
        return points;
    }
}
