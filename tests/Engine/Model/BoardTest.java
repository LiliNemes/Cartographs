package Engine.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
     */
    @Test
    public void checkOK() {
        Board b = new Board(10, Coordinate.parseList("2,3"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(3, 4, TerrainType.Forest);
        ps.addTile(4,5, TerrainType.Forest);
        assertEquals(ValidationResult.Ok, b.check(ps));
    }

    /**
     * Rossz indexelés esetén, ha a kiválasztott mezők lelógnának a térképről.
     */
    @Test
    public void checkOut() throws Exception {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(4, 1, TerrainType.Forest);
        assertEquals(ValidationResult.TileNotEmpty, b.check(ps));
    }

    /**
     * Ha olyan helyet választ ahol már van valami.
     */
    @Test
    public void checkReserved() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1, 1, TerrainType.Forest);
        assertEquals(ValidationResult.TileNotEmpty, b.check(ps));
    }

    /**
     * execution test ha a check jó de nem ad aranyat.
     */
    @Test
    public void executionOKNoGold() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ExecutionResult expected = new ExecutionResult(ValidationResult.Ok, 0);
        ExecutionResult actual = b.execute(ps);
        assertTrue(expected.Equals(actual));

    }

    /**
     * execution test ha a check jó és ad aranyat.
     */
    @Test
    public void executionOKGold() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ExecutionResult expected = new ExecutionResult(ValidationResult.Ok, 1);
        ExecutionResult actual = b.execute(ps);
        assertTrue(expected.Equals(actual));
    }

    /**
     * Execution test ha a check nem jó de adna aranyat.
     */
    @Test
    public void executionNotOk() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(1,1, TerrainType.Water);
        ExecutionResult expected = new ExecutionResult(ValidationResult.TileNotEmpty, 0);
        ExecutionResult actual = b.execute(ps);
        assertTrue(expected.Equals(actual));
    }

    /**
     * getNeighbours check
     */
    @Test
    public void getNeighbours() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(1,2, TerrainType.Water);
        ps.addTile(0,1, TerrainType.Water);
        ps.addTile(2, 1, TerrainType.Water);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(0,0, TerrainType.Village);
        b.execute(ps);
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
     */
    @Test
    public void getSameTerrainNeighbours() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0,0, TerrainType.Water);
        b.execute(ps);
        List<Coordinate> actual = new ArrayList<>();
        actual = b.sameTerrainTypeNeighbours(new Coordinate(0, 1));
        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(0,0));
        assertEquals(expected.size(), actual.size());
       for(int i=0; i< actual.size(); i++) {
           assertTrue(expected.get(i).equals(actual.get(i)));
       }
    }

    /**
     * neighbourNotInIt ha nincs olyan szomszéd amire igaz.
     */
    @Test
    public void neighbourNotInItNull() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Village);
        b.execute(ps);
        List<Coordinate> region = new ArrayList<>();
        List<Coordinate> actual=b.neighbourNotInIt(region, new Coordinate(0,0));
        List<Coordinate> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    /**
     * neighbourNotInIt ha van olyan szomszéd amire igaz.
     */
    @Test
    public void neighbourNotInItOK() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Village);
        ps.addTile(0, 1, TerrainType.Village);
        b.execute(ps);
        List<Coordinate> region = new ArrayList<>();
        region.add(new Coordinate(0, 0));
        List<Coordinate> actual=b.neighbourNotInIt(region, new Coordinate(0,0));
        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(0, 1));
        assertEquals(expected.size(), actual.size());
        for(int i=0; i< actual.size(); i++) {
            assertTrue(expected.get(i).equals( actual.get(i)));
        }
    }

    /**
     * getRegions test ha csak egy régió van.
     */
    @Test
    public void getRegionsOneRegion() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Water);
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Water);
        b.execute(ps);
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
                assertTrue(expected.get(i).get(j).equals(actual.get(i).get(j)));
            }
        }
    }

    /**
     * getRegions test ha nincs egy megadott típusú régió sem.
     */
    @Test
    public void getRegionsNoRegion() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Water);
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Water);
        b.execute(ps);
        List<List<Coordinate>> actual = b.getRegions(TerrainType.Farm);
        List<List<Coordinate>> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    /**
     * getRegions test ha több régió van.
     */
    @Test
    public void getRegionsMoreRegions() {
        Board b = new Board(3, Coordinate.parseList("1,1"), Coordinate.parseList("1,1"));
        PlayerTilesSelection ps = new PlayerTilesSelection();
        ps.addTile(0,0, TerrainType.Water);
        ps.addTile(0, 1, TerrainType.Water);
        ps.addTile(0, 2, TerrainType.Forest);
        ps.addTile(1, 0, TerrainType.Water);
        ps.addTile(2,2, TerrainType.Water);
        b.execute(ps);
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
                assertTrue(expected.get(i).get(j).equals(actual.get(i).get(j)));
            }
        }
    }

    @Test
    public void canLayoutBePlacedAtTest() {
        Board b = new Board(3, null, null);
        Layout layout = Layout.createLayout("0,0;1,0;2,0");
        assertTrue(b.canLayoutBePlacedAt(0,0, layout));
        assertTrue(b.canLayoutBePlacedAt(0,1, layout));
        assertTrue(b.canLayoutBePlacedAt(0,2, layout));
        assertFalse(b.canLayoutBePlacedAt(1,0, layout));
    }

    @Test
    public void getNumberOfFreeTilesCoveredByLayoutAtTest() {
        Board b = new Board(3, null, null);
        PlayerTilesSelection playerSelection = new PlayerTilesSelection();
        playerSelection.addTile(1,1 , TerrainType.Village);
        b.execute(playerSelection);

        Layout layout = Layout.createLayout("0,0;1,0;2,0");
        assertEquals(3, b.getNumberOfFreeTilesCoveredByLayoutAt(0,0, layout));
        assertEquals(2, b.getNumberOfFreeTilesCoveredByLayoutAt(0,1, layout));
        assertEquals(1, b.getNumberOfFreeTilesCoveredByLayoutAt(2,2, layout));
        assertEquals(2, b.getNumberOfFreeTilesCoveredByLayoutAt(-1,0, layout));
    }
}
