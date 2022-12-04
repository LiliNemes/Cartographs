package Engine.Model;

import java.io.Serializable;

public class ScoreBoardEntry implements Serializable {
    private String name;
    private int points;

    public ScoreBoardEntry(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
