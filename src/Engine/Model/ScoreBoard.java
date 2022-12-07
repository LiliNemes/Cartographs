package Engine.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ranglistát megvalósító osztály.
 */
public class ScoreBoard implements Serializable {
    private final List<ScoreBoardEntry> scores;

    /**
     * Konstruktor.
     */
    public ScoreBoard() {
        this.scores = new ArrayList<>();
    }

    /**
     * Új eredményt rak fel a ranglistára. A ranglistát a pontok nagysága szerint csökkenő sorrendbe rendezi, ha 10-nél
     * több bejegyzés keletkezne a 10.-et törli.
     * @param name A játékos neve.
     * @param points A játékos által elért pontok száma.
     */
    public void addScore(String name, int points) {
        this.scores.add(new ScoreBoardEntry(name, points));
        this.scores.sort((o1, o2) -> o2.getPoints() - o1.getPoints());
        if (this.scores.size() > 10) {
            this.scores.remove(10);
        }
    }

    /**
     *
     * @return Visszatér a ranglista elemeit alkotó listával.
     */
    public List<ScoreBoardEntry> getScores() {
        return scores;
    }

    /**
     *
     * @return A ranglist aelemeit szétbontva(név, pont) tartalmazó kétdimenziós String tömb.
     */
    public String[][] getScoresArray() {
        var result = new String[scores.size()][2];
        for (int i=0; i<scores.size(); i++) {
            result[i] = new String[] { scores.get(i).getName(), Integer.toString(scores.get(i).getPoints()) };
        }
        return result;
    }

    /**
     * Mentésre, filebaírásra szolgál ez a függvény.
     * @param fileName Annak a filenak a neve, ahova elmentendő.
     */
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

    /**
     * Segítségével fileból be lehet tölteni a ranglistát.
     * @param fileName Annak a filenak a neve ahonnan beolvasandó.
     * @return A beolvasott ranglista.
     */
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

