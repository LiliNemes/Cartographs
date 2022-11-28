package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sc_TheCauldronsTest {
    Sc_TheCauldrons theCauldrons;
    Board board;
    @Before
    public void init() {
        theCauldrons = new Sc_TheCauldrons();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * The Cauldrons küldetéskártya teszt ha van pont.
     * @throws Exception Rossz a kitöltés értéke.
     */
    @Test
    public void TheCauldronsTest1() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        board.execute(ps, false);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = theCauldrons.score(sheet);
        Assert.assertEquals(1, actual);
    }

    /**
     * The Cauldrons küldetéskártya teszt ha nincs pont.
     * @throws Exception Rossz a kitöltés értéke.
     */
    @Test
    public void TheCauldronsTest2() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        board.execute(ps, false);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = theCauldrons.score(sheet);
        Assert.assertEquals(0, actual);
    }

}