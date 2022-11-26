package Engine.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    /**
     * Board létrehozása test.
     */
    @Test
    public void initWorks() {
        var board = new Board(5, Coordinate.parseList("1,1"), Coordinate.parseList("2,2"));
        assertEquals(TerrainType.Empty, board.getTerrainType(new Coordinate(0,0)));
        assertEquals(TerrainType.Mountain, board.getTerrainType(new Coordinate(1,1)));
        assertEquals(TerrainType.Rift, board.getTerrainType(new Coordinate(2,2)));
    }

    /**
     * Check test jó adatokkal.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void checkOK() throws Exception {
        Board b = new Board(10, Coordinate.parseList("2,3"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(3, 4, TerrainType.Forest);
        ps.addTile(4,5, TerrainType.Forest);
        assertEquals(ValidationResult.Ok, b.check(ps, false));
    }

    /**
     * Rossz indexelés esetén, ha a kiválasztott mezők lelógnának a térképről.
     * @throws Exception ha rossz kitöltés.
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void checkOut() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(4, 1, TerrainType.Forest);
        assertEquals(ValidationResult.Ok, b.check(ps, false));
    }

    /**
     * Ha olyan helyet választ ahol már van valami.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void checkReserved() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1, 1, TerrainType.Forest);
        assertEquals(ValidationResult.TileNotEmpty, b.check(ps, false));
    }

    /**
     * execution test ha a check jó de nem ad aranyat.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void executionOKNoGold() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ExecutionResult expected = new ExecutionResult(ValidationResult.Ok, 0);
        ExecutionResult actual = b.execute(ps, false);
        assertTrue(expected.Equals(actual));

    }

    /**
     * execution test ha a check jó és ad aranyat.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void executionOKGold() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ExecutionResult expected = new ExecutionResult(ValidationResult.Ok, 1);
        ExecutionResult actual = b.execute(ps, false);
        assertTrue(expected.Equals(actual));
    }

    /**
     * Execution test ha a check nem jó de adna aranyat.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void executionNotOk() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(1,1, TerrainType.Water);
        ExecutionResult expected = new ExecutionResult(ValidationResult.TileNotEmpty, 0);
        ExecutionResult actual = b.execute(ps, false);
        assertTrue(expected.Equals(actual));
    }

    /**
     * getNeighbours check
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void getNeighbours() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(0,0, TerrainType.Village);
        b.execute(ps, false);
        List<TerrainType> actual = new ArrayList<>();
        actual=b.getNeighboursTerrainType(new Coordinate(0, 1));
        List<TerrainType> expected = new ArrayList<>();
        expected.add(b.getTerrainType(new Coordinate(1,1)));
        expected.add(b.getTerrainType(new Coordinate(0,0)));
        expected.add(b.getTerrainType(new Coordinate(0,2)));
        assertEquals(expected, actual);
    }

    /**
     * sameTerrainTypesNeighbours test.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void getSameTerrainNeighbours() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0,0, TerrainType.Water);
        b.execute(ps, false);
        List<Coordinate> actual = new ArrayList<>();
        actual = b.sameTerrainTypeNeighbours(new Coordinate(0, 1));
        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(0,0));
        assertEquals(expected.size(), actual.size());
       for(int i=0; i< actual.size(); i++) {
           assertTrue(expected.get(i).Equals(actual.get(i)));
       }
    }

    /**
     * neighbourNotInIt ha nincs olyan szomszéd amire igaz.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void neighbourNotInItNull() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Village);
        b.execute(ps, false);
        List<Coordinate> region = new ArrayList<>();
        List<Coordinate> actual=b.neighbourNotInIt(region, new Coordinate(0,0));
        List<Coordinate> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    /**
     * neighbourNotInIt ha van olyan szomszéd amire igaz.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void neighbourNotInItOK() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Village);
        ps.addTile(0, 1, TerrainType.Village);
        b.execute(ps, false);
        List<Coordinate> region = new ArrayList<>();
        region.add(new Coordinate(0, 0));
        List<Coordinate> actual=b.neighbourNotInIt(region, new Coordinate(0,0));
        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(0, 1));
        assertEquals(expected.size(), actual.size());
        for(int i=0; i< actual.size(); i++) {
            assertTrue(expected.get(i).Equals( actual.get(i)));
        }
    }

    /**
     * getRegions test ha csak egy régió van.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void getRegionsOneRegion() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Water);
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Water);
        b.execute(ps, false);
        List<List<Coordinate>> actual = b.getRegions(TerrainType.Water);
        List<List<Coordinate>> expected = new ArrayList<>();
        List<Coordinate> param=new ArrayList<>();
        param.add(new Coordinate(0,0));
        param.add(new Coordinate(1, 0));
        param.add(new Coordinate(0, 1));
        expected.add(param);
        assertEquals(expected.size(), actual.size());
        for(int i=0; i< actual.size(); i++) {
            assertEquals(expected.get(i).size(), actual.get(i).size());
            for(int j=0; j<actual.get(i).size(); j++) {
                assertTrue(expected.get(i).get(j).Equals(actual.get(i).get(j)));
            }
        }
    }

    /**
     * getRegions test ha nincs egy megadott típusú régió sem.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void getRegionsNoRegion() throws Exception{
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Water);
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Water);
        b.execute(ps, false);
        List<List<Coordinate>> actual = b.getRegions(TerrainType.Farm);
        List<List<Coordinate>> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    /**
     * getRegions test ha több régió van.
     * @throws Exception ha rossz kitöltés.
     */
    @Test
    public void getRegionsMoreRegions() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Water);
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(2,2, TerrainType.Water);
        b.execute(ps, false);
        List<List<Coordinate>> actual = b.getRegions(TerrainType.Water);
        List<List<Coordinate>> expected = new ArrayList<>();
        List<Coordinate> param=new ArrayList<>();
        param.add(new Coordinate(0,0));
        param.add(new Coordinate(1, 0));
        param.add(new Coordinate(0, 1));
        expected.add(param);
        List<Coordinate> param2 = new ArrayList<>();
        param2.add(new Coordinate(2,2));
        expected.add(param2);
        assertEquals(expected.size(), actual.size());
        for(int i=0; i< actual.size(); i++) {
            assertEquals(expected.get(i).size(), actual.get(i).size());
            for(int j=0; j<actual.get(i).size(); j++) {
                assertTrue(expected.get(i).get(j).Equals(actual.get(i).get(j)));
            }
        }
    }
}
