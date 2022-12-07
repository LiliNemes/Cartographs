package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sc_GreatCityTest {
    Sc_GreatCity greatCity;
    Board board;
    @Before
    public void init() {
        greatCity = new Sc_GreatCity();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * GreatCity küldetéskártya teszt ha van pont.
     */
    @Test
    public void GreatCityTest1() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Village);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = greatCity.score(sheet);
        Assert.assertEquals(1, actual);
    }

    /**
     * GreatCity küldetéskártya teszt ha nincs pont.
     */
    @Test
    public void GreatCityTest2() {
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Village);
        ps.addTile(0, 0, TerrainType.Village);
        ps.addTile(0, 2, TerrainType.Village);
        ps.addTile(2,2, TerrainType.Water);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = greatCity.score(sheet);
        Assert.assertEquals(0, actual);
    }
}