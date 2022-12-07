package UI;

public class ScoreCardInfo {
    private final String name;
    private final String description;

    public ScoreCardInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
