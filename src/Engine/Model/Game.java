package Engine.Model;

import UI.IGameEngine;
import UI.ScoreCardInfo;
import UI.TileInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A játékot reprezentáló osztály.
 */
public class Game implements IGameEngine, Serializable {

    private final PlayerSheet currentSheet;
    private final Map<Seasons, DiscoveryCardDeck> discoveryCardDecks;
    private final Map<Seasons, List<ScoreCardBase>> scoreCards;
    private final Map<Seasons, Integer> seasonTimes;
    private final Stack<DiscoveryCardBase> discoveryCardsInPlay;
    private Seasons currentSeason;

    /**
     * Konstruktor.
     *
     * @param currentSheet       A játékos lapja.
     * @param discoveryCardDecks A felfedező kártyák paklija évszakonként.
     * @param scoreCards         A pontozókártyák évszakonként.
     * @param seasonTimes        Az évszakok és időtartamaik.
     */
    public Game(PlayerSheet currentSheet, Map<Seasons, DiscoveryCardDeck> discoveryCardDecks, Map<Seasons, List<ScoreCardBase>> scoreCards, Map<Seasons, Integer> seasonTimes) {
        this.currentSheet = currentSheet;
        this.discoveryCardDecks = discoveryCardDecks;
        this.scoreCards = scoreCards;
        this.seasonTimes = seasonTimes;
        this.currentSeason = Seasons.spring;
        this.discoveryCardsInPlay = new Stack<>();
    }

    /**
     *
     * @return A játékos lapja.
     */
    private PlayerSheet getCurrentSheet() {
        return currentSheet;
    }

    /**
     *
     * @return Visszaadja a játékos lapján szereplő nevet.
     */
    @Override
    public String getPlayerName() {
        return currentSheet.getName();
    }

    /**
     * Visszaadja a paraméterként megadott évszakhoz tartozó ScoreCard-okat.
     * @param s Az adott évszak.
     * @return Az évszakhoz tartozó ScoreCard-ok listája.
     */
    public List<ScoreCardBase> getSeasonalScoreCards(Seasons s) {
        List<ScoreCardBase> ret = new ArrayList<>();
        ret.add(this.scoreCards.get(s).get(0));
        ret.add(this.scoreCards.get(s).get(1));
        return ret;
    }

    /**
     * Ruin esetén addig húz, amíg nem rendes kártya jön. Ambush esetén visszatér a kiszámolt, lehelyezendő formával.
     * @return Kiválasztott mezők ha ambush card-ot húzott.
     */
    @Override
    public PlayerTilesSelection drawNextCard() {
        if (discoveryCardsInPlay.empty()) {
            DiscoveryCardDeck currentDeck = discoveryCardDecks.get(currentSeason);
            var newCard = currentDeck.draw();
            discoveryCardsInPlay.push(newCard);
            while (discoveryCardsInPlay.peek().getCardType() == DiscoveryCardType.Ruin) {
                var newCard2 = currentDeck.draw();
                discoveryCardsInPlay.push(newCard2);
            }
        }

        if (discoveryCardsInPlay.peek().getCardType() == DiscoveryCardType.Ambush) {
            var ambushCard = (AmbushCard) discoveryCardsInPlay.peek();
            return ambushCard.calculateAmbush(this.currentSheet.getBoard());
        }
        return null;
    }

    /**
     * Végrehajtja a játékos egy lépését. Ha vége az adott évszaknak egy új kezdődik.
     *
     * @param playerTilesSelection A játékos egy lépése.
     * @return ValidationResult, hogy sikerült-e.
     */

    @Override
    public ExecutionSeasonResult executePlayerSelection(PlayerTilesSelection playerTilesSelection) {
        var isThereRuinCard = this.discoveryCardsInPlay.size() > 1;
        ValidationResult vr = this.currentSheet.execute(playerTilesSelection, this.discoveryCardsInPlay.peek(), isThereRuinCard);
        if (vr != ValidationResult.Ok)
            return new ExecutionSeasonResult(vr, false, this.currentSeason);
        this.discoveryCardsInPlay.clear();
        if (this.discoveryCardDecks.get(currentSeason).getTime() >= this.seasonTimes.get(currentSeason)) {
            if (currentSeason == Seasons.winter) {
                int a = this.getSeasonalScoreCards(Seasons.winter).get(0).score(this.getCurrentSheet());
                int b = this.getSeasonalScoreCards(Seasons.winter).get(1).score(this.getCurrentSheet());
                int money = this.getCurrentSheet().getAccumulatedGold();
                int monster = this.getCurrentSheet().getMonsterPoints();
                this.getCurrentSheet().setSeasonResults(Seasons.winter, new int[]{a, b, money, monster});
                return new ExecutionSeasonResult(ValidationResult.Ok, true, currentSeason);
            } else if (currentSeason == Seasons.spring) {
                int a = this.getSeasonalScoreCards(Seasons.spring).get(0).score(this.getCurrentSheet());
                int b = this.getSeasonalScoreCards(Seasons.spring).get(1).score(this.getCurrentSheet());
                int money = this.getCurrentSheet().getAccumulatedGold();
                int monster = this.getCurrentSheet().getMonsterPoints();
                this.getCurrentSheet().setSeasonResults(Seasons.spring, new int[]{a, b, money, monster});
                return new ExecutionSeasonResult(ValidationResult.Ok, true, currentSeason);
            } else if (currentSeason == Seasons.summer) {
                int a = this.getSeasonalScoreCards(Seasons.summer).get(0).score(this.getCurrentSheet());
                int b = this.getSeasonalScoreCards(Seasons.summer).get(1).score(this.getCurrentSheet());
                int money = this.getCurrentSheet().getAccumulatedGold();
                int monster = this.getCurrentSheet().getMonsterPoints();
                this.getCurrentSheet().setSeasonResults(Seasons.summer, new int[]{a, b, money, monster});
                return new ExecutionSeasonResult(ValidationResult.Ok, true, currentSeason);
            } else if (currentSeason == Seasons.autumn) {
                int a = this.getSeasonalScoreCards(Seasons.autumn).get(0).score(this.getCurrentSheet());
                int b = this.getSeasonalScoreCards(Seasons.autumn).get(1).score(this.getCurrentSheet());
                int money = this.getCurrentSheet().getAccumulatedGold();
                int monster = this.getCurrentSheet().getMonsterPoints();
                this.getCurrentSheet().setSeasonResults(Seasons.autumn, new int[]{a, b, money, monster});
                return new ExecutionSeasonResult(ValidationResult.Ok, true, currentSeason);
            }
        }
        return new ExecutionSeasonResult(ValidationResult.Ok, false, currentSeason);
    }

    /**
     * @return A kihúzott DiscoveryCard-ok.
     */
    @Override
    public List<String> getDrawnDiscoveryCards() {
        List<String> names = new ArrayList<>();
        for (DiscoveryCardBase discoveryCardBase : discoveryCardsInPlay) {
            names.add(discoveryCardBase.getName());
        }
        return names;
    }

    /**
     *
     * @return A kihúzott, játékban lévő ScoreCard-ok.
     */
    @Override
    public List<ScoreCardInfo> getDrawnScoreCards() {
        List<ScoreCardInfo> names = new ArrayList<>();
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.spring).get(0).getName(), this.scoreCards.get(Seasons.spring).get(0).getDescription()));
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.summer).get(0).getName(), this.scoreCards.get(Seasons.summer).get(0).getDescription()));
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.autumn).get(0).getName(), this.scoreCards.get(Seasons.autumn).get(0).getDescription()));
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.winter).get(0).getName(), this.scoreCards.get(Seasons.winter).get(0).getDescription()));
        return names;
    }

    /**
     *
     * @return A kihúzott (és nem ruin) felfedezőkártya által lerakható TerrainType(ok).
     */
    @Override
    public List<TerrainType> getPossibleTerrainTypes() {

        var lastCard = this.discoveryCardsInPlay.get(this.discoveryCardsInPlay.size() - 1);
        if (lastCard.getCardType() == DiscoveryCardType.Ambush) {
            return List.of(TerrainType.Monster);
        }
        DiscoveryCard dc = (DiscoveryCard) lastCard;
        return dc.getPossibleTerrainTypes();
    }

    /**
     *
     * @return Az éppen aktuális évszak.
     */
    @Override
    public Seasons getCurrentSeason() {
        return this.currentSeason;
    }

    /**
     * Beállítja az aktuális évszakot.
     * @param currentSeason aktuális évszak.
     */
    @Override
    public void setCurrentSeason(Seasons currentSeason) {
        this.currentSeason = currentSeason;
    }

    /**
     *
     * @param season Adott évszak.
     * @return Visszaadja a paraméterként kapott évszakban a ScoreCard-okból szerzett pontokat.
     */
    @Override
    public int[] getSeasonScores(Seasons season) {
        return currentSheet.getSeasonScores(season);
    }

    /**
     *
     * @return Visszaadja, hogy milyen hosszú az aktuális évszak.
     */
    @Override
    public int getCurrentSeasonTime() {
        return seasonTimes.get(currentSeason);
    }

    /**
     *
     * @return A játékban megszerzett pontok.
     */
    @Override
    public int getFinalScore() {
        return this.currentSheet.getScoreSum();
    }

    /**
     *
     * @return Mennyi idő telt el azaktuális évszakban.
     */
    @Override
    public int getCurrentTime() {
        return this.discoveryCardDecks.get(currentSeason).getTime();
    }

    /**
     *
     * @return Eddig megszerzett arany.
     */
    @Override
    public int getGolds() {
        return this.currentSheet.getAccumulatedGold();
    }

    /**
     *
     * @return A játékpálya mérete.
     */
    @Override
    public int getSize() {
        return this.currentSheet.getBoard().getBoardSize();
    }

    /**
     *
     * @param x X koordináta értéke.
     * @param y Y koordináta értéke.
     * @return TileInfo ami tartalmazza a koordinátáival megadott mező típusát, hogy van-e rajta rom.
     */
    @Override
    public TileInfo getTileInfo(int x, int y) {
        var terrainType = this.currentSheet.getBoard().getTerrainType(new Coordinate(x, y));
        var hasRuin = this.currentSheet.getBoard().hasRuin(new Coordinate(x, y));
        return new TileInfo(terrainType, hasRuin);
    }
}


