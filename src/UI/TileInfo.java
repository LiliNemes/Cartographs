package UI;

import Engine.Model.TerrainType;

public class TileInfo {
    private final TerrainType terrainType;
    private final boolean hasRuins;

    public TileInfo(TerrainType terrainType, boolean hasRuins) {
        this.terrainType = terrainType;
        this.hasRuins = hasRuins;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public boolean hasRuins() {
        return hasRuins;
    }

}
