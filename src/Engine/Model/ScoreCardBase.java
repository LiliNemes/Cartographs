package Engine.Model;

import java.io.Serializable;

/**
 * ScoreCard ősosztály.
 */
public class ScoreCardBase implements Serializable {
    private final String name;
    private final String description;

    /**
     * Konstruktor.
     * @param s A kártya neve.
     * @param desc A kártyán szereplő szöveg.
     */
    public ScoreCardBase(String s, String desc) {
        name=s;
        description=desc;
    }

    /**
     * Pontozó függvény.
     * @param sheet A pontozandó lap.
     * @return konstans, mivel minden leszármazottnak felül kell írnia.
     */
    public int score(PlayerSheet sheet) {
        return 74;
    }

    /**
     *
     * @return A kártya neve.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return A kártya szabálya/leírása.
     */
    public String getDescription() {
        return description;
    }
}
