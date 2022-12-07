package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_MagesValleyTest {
    Sc_MagesValley magesValley;
    Board board;
    @Before
    public void init() {
        magesValley = new Sc_MagesValley();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Mages Valley küldetéskártya teszt ha van szerzett pont.
     */
    @Test
    public void MagesValleyTest1() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Farm);
        ps.addTile(0, 0, TerrainType.Forest);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(2, 2, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = magesValley.score(sheet);
        Assert.assertEquals(3, actual);
    }

    /**
     * Mages Valley küldetéskártya teszt ha nincs szerzett pont.
     */
    @Test
    public void MagesValleyTest2() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 0, TerrainType.Farm);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(2, 2, TerrainType.Water);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = magesValley.score(sheet);
        Assert.assertEquals(0, actual);
    }

}