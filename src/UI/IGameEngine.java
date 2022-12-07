package UI;

import Engine.Model.*;

import java.util.List;

public interface IGameEngine extends IBoardInfo {
    ExecutionSeasonResult executePlayerSelection(PlayerTilesSelection selection);

    //returns playerTilesSelection in case of monster ambush
    PlayerTilesSelection drawNextCard();

    List<TerrainType> getPossibleTerrainTypes();


    int getCurrentTime();

    int getGolds();

    List<String> getDrawnDiscoveryCards();

    List<ScoreCardInfo> getDrawnScoreCards();

    List<ScoreCardBase> getSeasonalScoreCards(Seasons s);

    Seasons getCurrentSeason();

    void setCurrentSeason(Seasons currentSeason);

    String getPlayerName();

    int[] getSeasonScores(Seasons s);

    int getCurrentSeasonTime();

    int getFinalScore();
}

