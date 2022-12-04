package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sc_GreenboughTest {

    Sc_Greenbough greenbough;
    Board board;
    @Before
    public void init() {
        greenbough = new Sc_Greenbough();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * Greenbough küldetéskártya teszt ha van szerzett pont.
     * @throws Exception Nem jó a mező kitöltése.
     */
    @Test
    public void GreenboughTest1() throws Exception{
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Farm);
        ps.addTile(0, 0, TerrainType.Forest);
        ps.addTile(0, 2, TerrainType.Forest);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = greenbough.score(sheet);
        Assert.assertEquals(3, actual);
    }

    /**
     * Greenbough küldetéskártya teszt ha van szerzett pont.
     * @throws Exception Nem jó a mező kitöltése.
     */
    @Test
    public void GreenboughTest2() throws Exception {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Farm);
        ps.addTile(0, 0, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Water);
        ps.addTile(2,2, TerrainType.Farm);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = greenbough.score(sheet);
        Assert.assertEquals(0, actual);
    }
}