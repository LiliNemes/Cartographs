package Engine.Model;

import org.junit.Assert;
import org.junit.Test;

public class ScoreBoardTest {
    @Test
    public void scoreBoardOrdered() {
        ScoreBoard sb = new ScoreBoard();
        sb.addScore("A1", 10);
        sb.addScore("A4", 40);
        sb.addScore("A2", 20);
        sb.addScore("A3", 30);

        var scores = sb.getScores();
        Assert.assertEquals("A4", scores.get(0).getName());
        Assert.assertEquals("A3", scores.get(1).getName());
        Assert.assertEquals("A2", scores.get(2).getName());
        Assert.assertEquals("A1", scores.get(3).getName());
    }

    @Test
    public void scoreBoarLimited() {
        ScoreBoard sb = new ScoreBoard();
        for (int i=0; i < 20; i++) {
            sb.addScore("A" + i , 10 * i);
        }

        var scores = sb.getScores();
        Assert.assertEquals(10, scores.size());
        Assert.assertEquals("A19", scores.get(0).getName());
        Assert.assertEquals("A10", scores.get(9).getName());
    }
}
