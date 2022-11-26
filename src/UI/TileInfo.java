package UI;

import Engine.Model.TerrainType;

public class TileInfo {
    private TerrainType terrainType;
    private boolean hasRuins;

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

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }
}
