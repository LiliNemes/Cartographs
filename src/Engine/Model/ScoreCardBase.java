package Engine.Model;

public class ScoreCardBase {
    private String name;
    public ScoreCardBase(String s) {name=s;}
    public int score(PlayerSheet sheet) {
        return 0;
    }

    public String getName() {
        return name;
    }
}
