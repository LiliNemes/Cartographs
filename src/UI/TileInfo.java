package UI;

import Engine.Model.TerrainType;


/**
 * Olyan osztály, mely a mezőkről 2 információt tárol: A kitöltésük típusát, illetve hogy van-e rajtuk rom.
 */
public class TileInfo {
    private final TerrainType terrainType;
    private final boolean hasRuins;

    /**
     * Konstruktor.
     * @param terrainType Milyen típusú a kitöltése.
     * @param hasRuins Van-e rajta rom.
     */
    public TileInfo(TerrainType terrainType, boolean hasRuins) {
        this.terrainType = terrainType;
        this.hasRuins = hasRuins;
    }

    /**
     *
     * @return A kitöltés típusa.
     */
    public TerrainType getTerrainType() {
        return terrainType;
    }

    /**
     *
     * @return Van-e rajta rom (true ha igen, false ha nem).
     */
    public boolean hasRuins() {
        return hasRuins;
    }

}
