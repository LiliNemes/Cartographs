package Engine.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoard implements Serializable {
    private final List<ScoreBoardEntry> scores;

    public ScoreBoard() {
        this.scores = new ArrayList<>();
    }

    public void addScore(String name, int points) {
        this.scores.add(new ScoreBoardEntry(name, points));
        this.scores.sort((o1, o2) -> o2.getPoints() - o1.getPoints());
        if (this.scores.size() > 10) {
            this.scores.remove(10);
        }
    }

    public List<ScoreBoardEntry> getScores() {
        return scores;
    }

    public String[][] getScoresArray() {
        var result = new String[scores.size()][2];
        for (int i=0; i<scores.size(); i++) {
            result[i] = new String[] { scores.get(i).getName(), Integer.toString(scores.get(i).getPoints()) };
        }
        return result;
    }

    public void save(String fileName) {
        try {
            FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            o.close();
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ScoreBoard load(String fileName) {
        try {
            FileInputStream f = new FileInputStream(fileName);
            ObjectInputStream  o = new ObjectInputStream (f);
            var scoreBoard = (ScoreBoard) o.readObject();
            o.close();
            f.close();
            return scoreBoard;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

