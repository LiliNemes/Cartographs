package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_GreengoldPlainsTest {
    Sc_GreengoldPlains greengoldPlains;
    Board board;
    @Before
    public void init() {
        greengoldPlains = new Sc_GreengoldPlains();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Greengold Plains küldetéskártya teszt ha van szerzett pont.
     */
    @Test
    public void GreengoldPlainsTest1() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Farm);
        ps.addTile(2,2, TerrainType.Village);
        ps.addTile(2,1, TerrainType.Village);
        ps.addTile(2, 0, TerrainType.Water);
        ps.addTile(1, 2, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = greengoldPlains.score(sheet);
        Assert.assertEquals(6, actual);
    }

    /**
     * Greengold Plains küldetéskártya teszt ha nincs szerzett pont.
     */
    @Test
    public void GreengoldPlainsTest2() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(1, 0, TerrainType.Farm);
        ps.addTile(2,2, TerrainType.Village);
        ps.addTile(2,1, TerrainType.Village);
        ps.addTile(1, 2, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = greengoldPlains.score(sheet);
        Assert.assertEquals(0, actual);
    }

}