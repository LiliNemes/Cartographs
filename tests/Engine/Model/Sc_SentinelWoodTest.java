package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sc_SentinelWoodTest {
    Sc_SentinelWood sentinelWood;
    Board board;
    @Before
    public void init() {
        sentinelWood = new Sc_SentinelWood();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Sentinel Wood küldetéskártya teszt ha van szerzett pont.
     * @throws Exception Nem jó a mező kitöltése.
     */
    @Test
    public void sentinelWoodTest1() throws Exception{
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Farm);
        ps.addTile(0, 0, TerrainType.Forest);
        ps.addTile(0, 2, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = sentinelWood.score(sheet);
        Assert.assertEquals(2, actual);
    }

    /**
     * Sentinel Wood küldetéskártya teszt ha nincs szerzett pont.
     * @throws Exception Nem jó a mező kitöltése.
     */
    @Test
    public void sentinelWoodTest2() throws Exception{
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Farm);
        ps.addTile(0, 0, TerrainType.Water);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = sentinelWood.score(sheet);
        Assert.assertEquals(0, actual);
    }

}