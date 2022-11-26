package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_BorderlandsTest {
    Board board;
    Sc_Borderlands borderlands;
    @Before
    public void init() {
        borderlands = new Sc_Borderlands();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));


    }

    /**
     * Borderlands küldetéskártya teszt ha van megszerzett pont.
     * @throws Exception invalid kijelölt típus.
     */
    @Test
    public void BorderlandsTest1() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 0, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Farm);
        board.execute(ps, false);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = borderlands.score(sheet);
        Assert.assertEquals(6, actual);
    }

    /**
     * Borderlands küldetéskártya teszt ha nincs megszerzett pont.
     * @throws Exception invalid kijelölt típus.
     */
    @Test
    public void BorderlandsTest2() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 0, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Farm);
        board.execute(ps, false);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = borderlands.score(sheet);
        Assert.assertEquals(0, actual);
    }

}