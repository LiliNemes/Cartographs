package UI;

import Engine.Model.*;

import java.util.List;

public interface IGameEngine extends IBoardInfo {
    ExecutionSeasonResult executePlayerSelection(PlayerTilesSelection selection);

    void drawNextCard();

    List<TerrainType> getPossibleTerrainTypes();

    Seasons getCurrentSeason();

    int getCurrentTime();

    int getGolds();

    List<String> getDrawnDiscoveryCards();
    List<String> getDrawnScoreCards();
    List<ScoreCardBase> getSeasonalScoreCards(Seasons s);
    PlayerSheet getCurrentSheet();
    void setCurrentSeason(Seasons currentSeason);
}

