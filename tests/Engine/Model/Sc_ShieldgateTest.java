package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_ShieldgateTest {
    Sc_Shieldgate shieldgate;
    Board board;
    @Before
    public void init() {
        shieldgate = new Sc_Shieldgate();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Shieldgate küldetéskártya teszt ha van pont.
     */
    @Test
    public void ShieldgateTest1() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = shieldgate.score(sheet);
        Assert.assertEquals(2, actual);
    }

    /**
     * Shieldgate küldetéskártya teszt ha nincs pont.
     */
    @Test
    public void ShieldgateTest2() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Water);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = shieldgate.score(sheet);
        Assert.assertEquals(0, actual);
    }
}