package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_TreetowerTest {
    Sc_Treetower treetower;
    Board board;
    @Before
    public void init() {
        treetower = new Sc_Treetower();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Treetower küldetéskártya teszt ha van pont.
     */
    @Test
    public void TreetowerTest1() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        ps.addTile(1, 2, TerrainType.Forest);
        ps.addTile(2, 0, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = treetower.score(sheet);
        Assert.assertEquals(1, actual);
    }

    /**
     * Treetower küldetéskártya teszt ha nincs pont.
     */
    @Test
    public void TreetowerTest2() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        ps.addTile(2, 0, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = treetower.score(sheet);
        Assert.assertEquals(0, actual);
    }

}