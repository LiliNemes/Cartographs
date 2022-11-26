package Engine.Model;

/**
 * Egy mező a játéktáblán.
 */
public class SelectedTile {
    private int x;
    private int y;
    private TerrainType terrainType;

    /**
     * Konstruktor.
     * @param x a mező x koordinátája.
     * @param y a mező y koordinátája.
     * @param terrainType a mező kitöltésének típusa.
     */
    public SelectedTile(int x, int y, TerrainType terrainType) {
        this.x = x;
        this.y = y;
        this.terrainType = terrainType;
    }

    /**
     *
     * @return x koordináta.
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y koordináta.
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return a mező kitöltésének típusa.
     */
    public TerrainType getTerrainType() {
        return terrainType;
    }
}
