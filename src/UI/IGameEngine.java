package UI;

import Engine.Model.PlayerTilesSelection;
import Engine.Model.Seasons;
import Engine.Model.TerrainType;
import Engine.Model.ValidationResult;

import java.util.List;

public interface IGameEngine extends IBoardInfo {
    ValidationResult executePlayerSelection(PlayerTilesSelection selection);

    void drawNextCard();

    List<TerrainType> getPossibleTerrainTypes();

    Seasons getCurrentSeason();

    int getCurrentTime();

    int getGolds();

    List<String> getDrawnDiscoveryCards();
     List<String> getDrawnScoreCards();
}

