package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékos által kiválasztott mezőket, illetve a játékos által a mezőknek adott lehetséges kitöltési értékeket
 * tartalmazó osztály.
 */
public class PlayerTilesSelection {
    private ArrayList<SelectedTile> selectedTiles;

    private static final List<TerrainType> VALID_TERRAIN_TYPES = List.of(TerrainType.Farm,
                                                                       TerrainType.Forest,
                                                                       TerrainType.Village,
                                                                       TerrainType.Water);

    /**
     * Konstruktor.
     */
    public PlayerTilesSelection() {
        selectedTiles = new ArrayList<SelectedTile>();
    }

    /**
     *
     * @return A játékos által kiválasztott mezők.
     */
    public ArrayList<SelectedTile> getSelectedTiles() {return selectedTiles;}

    /**
     * A függvény egy új mezőt ad hozzá a játékos által kiválasztott mezőkhöz, ha annak a kitöltési értéke megfelelő.
     * Ha a mező már korábban ki lett választva a régit eldobja és az új az érvényes.
     * @param x Az új mező x koordinátája.
     * @param y Az új mező y koordinátája.
     * @param terrainType Az új mező kiválasztott kitöltési értéke.
     * @throws Exception
     */
    public void addTile(int x, int y, TerrainType terrainType) throws Exception {
        if (!VALID_TERRAIN_TYPES.contains(terrainType)) {
            throw new Exception("Selected terrain type is not valid.");
        }
        var tile = GetTileByCoordinates(x, y);
        if (tile != null) {
            selectedTiles.remove(tile);
        }
        selectedTiles.add(new SelectedTile(x, y, terrainType));
    }

    /**
     * A paraméterként megadott koordinátákon lévő mező kitöltési típusát adja vissza.
     * @param x A megadott mező x koordinátája.
     * @param y A megadott mező y koordinátája.
     * @return A paraméterként megadott koordinátákon lévő mező kitöltési típusa.
     * @throws Exception Ha nincs ilyen koordinátán mező.
     */
    public TerrainType GetTerrainType(int x, int y) throws Exception {
        var tile = GetTileByCoordinates(x, y);
        if (tile == null) {
            throw new Exception(String.format("Tile not found at coordinates %s, %s.",x, y));
        }
        return tile.getTerrainType();
    }

    /**
     * Visszaadja a bal felső (0,0)-s sarokba 'vetített' (odatolt) formát amit a kijelölt mezőkből képez.
     * @return Az új forma.
     */
    public Layout GetLayout() {
        if (selectedTiles.size() == 0) {
            return null;
        }
        List<Coordinate> coordinates = new ArrayList<>();
        for(int i=0; i< selectedTiles.size(); i++) {
            SelectedTile tile = selectedTiles.get(i);
            coordinates.add(new Coordinate(tile.getX(), tile.getY()));
        }
        Layout ret = new Layout(coordinates);
        ret=ret.project();
        return ret;
    }

    /**
     * Visszaadja a kijelölés típusát (kijelölés első elemének típusát, de úgyis van olyan függvény ami ellenőrzi,
     * hogy mind ugyanolyan-e.
     * @return A kijelölés első mezőjének típusa.
     */
    public TerrainType getTerrain() {
        return this.selectedTiles.get(0).getTerrainType();
    }

    /**
     * Megnézi, hogy minden kijelölt mező azonos típusú-e.
     * @return True ha igen, false ha nem.
     */
    public boolean areAllTerrainsSame() {
        TerrainType terrain = this.selectedTiles.get(0).getTerrainType();
        for(int i=0; i<this.selectedTiles.size(); i++) {
            if(this.selectedTiles.get(i).getTerrainType()!= terrain)
                return false;
        }
        return true;
    }

    /**
     * A paraméterként megadott koordinátákon lévő mezőt adja vissza, ha az a mező a kiválasztottak között van.
     * @param x Mező x koordinátája.
     * @param y Mező y koordinátája.
     * @return A mező ami a megadott koordinátákkal rendelkezik, null ha nincs ilyen.
     */
    private SelectedTile GetTileByCoordinates(int x, int y) {
        for (int i = 0; i < selectedTiles.size(); i++) {
            SelectedTile tile = selectedTiles.get(i);
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    //TODO
    public void ClearAll() {
        selectedTiles.clear();
    }
}
