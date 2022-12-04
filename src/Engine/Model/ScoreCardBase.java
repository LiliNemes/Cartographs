package Engine.Model;

import java.io.Serializable;

public class ScoreCardBase implements Serializable {
    private String name;
    private String description;
    public ScoreCardBase(String s, String desc) {
        name=s;
        description=desc;
    }

    public int score(PlayerSheet sheet) {
        return 74;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
