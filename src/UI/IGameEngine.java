package UI;

import Engine.Model.*;

import java.util.List;

public interface IGameEngine extends IBoardInfo {
    /**
     * Berajzolja a táblára (ellenőrzés után) a játékos által kiválasztott mezőket.
     * @param selection A játékos által kiválasztott mezők.
     * @return ExecuteSeasonResult arról, hogy sikerült-e, évszak vége van-e.
     */
    ExecutionSeasonResult executePlayerSelection(PlayerTilesSelection selection);

    /**
     * Felhúzza a következő kártyát. Ruin esetén az első nem ruin kártyáig húz.
     * @return Ambush Card esetén visszatér a lehelyezendő mezőkkel.
     */
    PlayerTilesSelection drawNextCard();

    /**
     *
     * @return A kihúzott (és nem ruin) felfedezőkártya által lerakható TerrainType(ok).
     */
    List<TerrainType> getPossibleTerrainTypes();
    /**
     *
     * @return Mennyi idő telt el azaktuális évszakban.
     */
    int getCurrentTime();
    /**
     *
     * @return Eddig megszerzett arany.
     */
    int getGolds();
    /**
     * @return A kihúzott DiscoveryCard-ok.
     */
    List<String> getDrawnDiscoveryCards();
    /**
     *
     * @return A kihúzott, játékban lévő ScoreCard-ok.
     */
    List<ScoreCardInfo> getDrawnScoreCards();
    /**
     * Visszaadja a paraméterként megadott évszakhoz tartozó ScoreCard-okat.
     * @param s Az adott évszak.
     * @return Az évszakhoz tartozó ScoreCard-ok listája.
     */
    List<ScoreCardBase> getSeasonalScoreCards(Seasons s);
    /**
     *
     * @return Az éppen aktuális évszak.
     */
    Seasons getCurrentSeason();
    /**
     * Beállítja az aktuális évszakot.
     * @param currentSeason aktuális évszak.
     */
    void setCurrentSeason(Seasons currentSeason);
    /**
     *
     * @return Visszaadja a játékos lapján szereplő nevet.
     */
    String getPlayerName();
    /**
     *
     * @param s Adott évszak.
     * @return Visszaadja a paraméterként kapott évszakban a ScoreCard-okból szerzett pontokat.
     */
    int[] getSeasonScores(Seasons s);
    /**
     *
     * @return Visszaadja, hogy milyen hosszú az aktuális évszak.
     */
    int getCurrentSeasonTime();
    /**
     *
     * @return A játékban megszerzett pontok.
     */
    int getFinalScore();
}

