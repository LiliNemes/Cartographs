package UI;

import Engine.Model.*;

import java.util.List;

public interface IGameEngine extends IBoardInfo {
    ExecutionSeasonResult executePlayerSelection(PlayerTilesSelection selection);

    //returns playerTilesSelection in case of monster ambush
    PlayerTilesSelection drawNextCard();

    List<TerrainType> getPossibleTerrainTypes();

    Seasons getCurrentSeason();

    int getCurrentTime();

    int getGolds();

    List<String> getDrawnDiscoveryCards();
    List<ScoreCardInfo> getDrawnScoreCards();
    List<ScoreCardBase> getSeasonalScoreCards(Seasons s);
    void setCurrentSeason(Seasons currentSeason);
    String getPlayerName();
}

