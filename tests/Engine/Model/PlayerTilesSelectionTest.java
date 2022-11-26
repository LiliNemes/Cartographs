package Engine.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTilesSelectionTest {

    PlayerTilesSelection selection;

    @Before
    public void init() {
        selection = new PlayerTilesSelection();
    }

    /**
     * Már korábban kiválasztott mező felülírása.
     * @throws Exception ha nem jó a típus.
     */
    @Test
    public void canChangeTerrainType() throws Exception {
        selection.addTile(1,1, TerrainType.Farm);
        selection.addTile(1,1, TerrainType.Water);

        assertEquals(TerrainType.Water, selection.GetTerrainType(1,1));
    }

    /**
     * Mező hozzáadása invalid kitöltéssel.
     * @throws Exception ha nem jó a típus.
     */
    @Test(expected = Exception.class)
    public void invalidTerrainTypeThrowsException() throws Exception {
        selection.addTile(1, 1, TerrainType.Monster);
    }

    /**
     * getTerrainType függvény testje.
     * @throws Exception ha nem jó a típus.
     */
    @Test
    public void getTerrainTypeTest() throws Exception {
        selection.addTile(1, 2, TerrainType.Village);
        assertEquals(TerrainType.Village, selection.GetTerrainType(1, 2));
    }
    /**
     * Ellenőrzi, hogy működik-e a vetítéssel forma létrehozása.
     * @throws Exception ha nem jó a típus.
     */
    @Test
    public void getLayoutTest() throws Exception {
        selection.addTile(5, 5, TerrainType.Farm);
        selection.addTile(6, 5, TerrainType.Farm);
        selection.addTile(7, 5, TerrainType.Farm);
        Layout layout = selection.GetLayout();
        Layout expectedLayout = Layout.createLayout("0,0;1,0;2,0");
        assertTrue(layout.isMatch(expectedLayout));
    }

    /**
     * allAreSame nem jó értékkel.
     * @throws Exception ha nem jó a típus.
     */
    @Test
    public void allAreSameNotOK() throws Exception {
        selection.addTile(5,4, TerrainType.Farm);
        selection.addTile(5, 5, TerrainType.Forest);
        assertFalse(this.selection.areAllTerrainsSame());
    }

    /**
     * allAreSame jó értékekkel.
     * @throws Exception ha nem jó a típus.
     */
    @Test
    public void allAreSameOK() throws Exception {
        selection.addTile(5,4, TerrainType.Farm);
        selection.addTile(5, 5, TerrainType.Farm);
        assertTrue(this.selection.areAllTerrainsSame());
    }
}
