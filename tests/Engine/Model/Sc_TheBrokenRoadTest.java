package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_TheBrokenRoadTest {
    Sc_TheBrokenRoad theBrokenRoad;
    Board board;
    @Before
    public void init() {
        theBrokenRoad = new Sc_TheBrokenRoad();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * TheBrokenRoad küldetéskártya teszt ha van pont.
     */
    @Test
    public void TheBrokenRoadTest1() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = theBrokenRoad.score(sheet);
        Assert.assertEquals(6, actual);
    }

    /**
     * TheBrokenRoad küldetéskártya teszt ha nincs pont.
     */
    @Test
    public void TheBrokenRoadTest2() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = theBrokenRoad.score(sheet);
        Assert.assertEquals(0, actual);
    }

}