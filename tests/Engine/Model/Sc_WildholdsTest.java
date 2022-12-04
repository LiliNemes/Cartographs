package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sc_WildholdsTest {
    Sc_Wildholds wildholds;
    Board board;
    @Before
    public void init() {
        wildholds = new Sc_Wildholds();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Wildholds küldetéskártya teszt ha van pont.
     * @throws Exception Rossz a kitöltés értéke.
     */
    @Test
    public void WildholdsTest1() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        ps.addTile(1, 2, TerrainType.Village);
        ps.addTile(2, 0, TerrainType.Forest);
        ps.addTile(2, 1, TerrainType.Village);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = wildholds.score(sheet);
        Assert.assertEquals(8, actual);
    }
    /**
     * Wildholds küldetéskártya teszt ha nincs pont.
     * @throws Exception Rossz a kitöltés értéke.
     */
    @Test
    public void WildholdsTest2() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        ps.addTile(1, 2, TerrainType.Village);
        ps.addTile(2, 0, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = wildholds.score(sheet);
        Assert.assertEquals(0, actual);
    }

}