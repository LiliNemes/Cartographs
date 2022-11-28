package UI.Fakes;

import Engine.Model.PlayerTilesSelection;
import Engine.Model.Seasons;
import Engine.Model.TerrainType;
import Engine.Model.ValidationResult;
import UI.IGameEngine;
import UI.TileInfo;

import java.util.ArrayList;
import java.util.List;

public class FakeGameEngine implements IGameEngine {

    private TileInfo[][] tiles;
    private int size;

    public FakeGameEngine(int size) {
        this.size = size;
        this.tiles = new TileInfo[size][size];
        for (int row=0;row<size;row++) {
            for (int col=0;col<size;col++) {
                tiles[col][row] = new TileInfo(TerrainType.Empty, false);
            }
        }
        tiles[size/2][size/2] = new TileInfo(TerrainType.Rift, false);
        tiles[1][1] = new TileInfo(TerrainType.Village, false);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public TileInfo getTileInfo(int x, int y) {
        return tiles[x][y];
    }

    @Override
    public ValidationResult executePlayerSelection(PlayerTilesSelection selection) {
        var selectedTiles = selection.getSelectedTiles();
        for (int i=0; i<selectedTiles.size();i ++) {
            var selectedTile = selectedTiles.get(i);
            tiles[selectedTile.getX()][selectedTile.getY()].setTerrainType(selectedTile.getTerrainType());
        }
        return ValidationResult.Ok;
    }

    private int roundCount = 0;
    private int time = 0;
    private Seasons currentSeason = Seasons.spring;

    @Override
    public void drawNextCard() {
        roundCount++;
        this.time += 1;
        if (this.time >= 6) {
            this.time = 0;
            this.currentSeason = Seasons.values()[((this.currentSeason.ordinal() + 1) % 4)];
        }
    }

    @Override
    public Seasons getCurrentSeason() {
        return this.currentSeason;
    }

    @Override
    public int getCurrentTime() {
        return this.time;
    }

    @Override
    public List<TerrainType> getPossibleTerrainTypes() {
        if (roundCount % 3 == 0)
            return List.of(TerrainType.Village, TerrainType.Forest, TerrainType.Water);
        else if (roundCount % 3 == 1)
            return List.of(TerrainType.Farm, TerrainType.Water);
        return List.of(TerrainType.Forest);
    }

    @Override
    public int getGolds() {
        return 0;
    }

    @Override
    public List<String> getDrawnDiscoveryCards() {
        if (roundCount % 3 == 0)
            return List.of("farmland", "hamlet");
        else if (roundCount % 3 == 1)
            return List.of("riftlands");
        return List.of("fishingvillage");
    }

    @Override
    public List<String> getDrawnScoreCards() {
        List<String> names = new ArrayList<>();
        names.add("Borderlands");
        names.add("CanalLake");
        names.add("GreatCity");
        names.add("Greenbough");
        return names;
    }
}
