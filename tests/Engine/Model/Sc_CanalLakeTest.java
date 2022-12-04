package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sc_CanalLakeTest {
    Sc_CanalLake canallake;
    Board board;
    @Before
    public void init() {
        canallake = new Sc_CanalLake();
        board = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
    }

    /**
     * CanalLake küldetéskártya teszt ha van pont.
     * @throws Exception Ha rossz kitöltés.
     */
    @Test
    public void canalLakeTest1() throws Exception{
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Farm);
        ps.addTile(0, 0, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Water);
        ps.addTile(2,2, TerrainType.Farm);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = canallake.score(sheet);
        Assert.assertEquals(3, actual);
    }

    /**
     * CanalLake küldetéskártya teszt ha nincs pont.
     * @throws Exception Ha rossz kitöltés.
     */
    @Test
    public void canalLakeTest2() throws Exception{
        PlayerTilesSelection ps=new PlayerTilesSelection();
        ps.addTile(0, 0, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Water);
        ps.addTile(2,2, TerrainType.Farm);
        board.execute(ps);
        PlayerSheet sheet = new PlayerSheet("Lili", board);
        int actual = canallake.score(sheet);
        Assert.assertEquals(0, actual);
    }

}