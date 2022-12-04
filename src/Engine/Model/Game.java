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

    private PlayerSheet getCurrentSheet() {
        return currentSheet;
    }

    @Override
    public String getPlayerName() {
        return currentSheet.getName();
    }

    private PlayerSheet currentSheet;
    private Map<Seasons, DiscoveryCardDeck> discoveryCardDecks;

    private DiscoveryCardDeck ambushDeck;

    public List<ScoreCardBase> getSeasonalScoreCards(Seasons s) {
        List<ScoreCardBase> ret=new ArrayList<>();
        ret.add(this.scoreCards.get(s).get(0));
        ret.add(this.scoreCards.get(s).get(1));
        return ret;
    }

    private Map<Seasons, List<ScoreCardBase>> scoreCards;

    private Map<Seasons, Integer> seasonTimes;

    public void setCurrentSeason(Seasons currentSeason) {
        this.currentSeason = currentSeason;
    }

    private Seasons currentSeason;

    private Stack<DiscoveryCardBase> discoveryCardsInPlay;

    /**
     * Konstruktor.
     * @param currentSheet A játékos lapja.
     * @param discoveryCardDecks A felfedező kártyák paklija évszakonként.
     * @param ambushDeck A szörnykáryták
     * @param scoreCards A pontozókártyák évszakonként.
     * @param seasonTimes Az évszakok és időtartamaik.
     */
    public Game(PlayerSheet currentSheet, Map<Seasons, DiscoveryCardDeck> discoveryCardDecks, DiscoveryCardDeck ambushDeck, Map<Seasons, List<ScoreCardBase>> scoreCards, Map<Seasons, Integer> seasonTimes) {
        this.currentSheet = currentSheet;
        this.discoveryCardDecks = discoveryCardDecks;
        this.ambushDeck = ambushDeck;
        this.scoreCards = scoreCards;
        this.seasonTimes=seasonTimes;
        this.currentSeason = Seasons.spring;
        this.discoveryCardsInPlay = new Stack<>();
    }

    /**
     * Addig húz, amíg nem ruin kártya jön.
     */
    //TODO could not be tested! :( Tapasztalati alapon okk.
    public PlayerTilesSelection drawNextCard() {
        //ckeck if inplay cards is empty
        // draw till not ruin
        if(discoveryCardsInPlay.empty()) {
            DiscoveryCardDeck currentDeck =discoveryCardDecks.get(currentSeason);
            var newCard = currentDeck.draw();
            discoveryCardsInPlay.push(newCard);
            while(discoveryCardsInPlay.peek().getCardType()==DiscoveryCardType.Ruin) {
                var newCard2 = currentDeck.draw();
                discoveryCardsInPlay.push(newCard2);
            }
        }

        if (discoveryCardsInPlay.peek().getCardType()==DiscoveryCardType.Ambush) {
            var ambushCard = (AmbushCard)discoveryCardsInPlay.peek();
            return ambushCard.calculateAmbush(this.currentSheet.getBoard());
        }
        return null;
    }

    /**
     * Végrehajtja a játékos egy lépését. Ha vége az adott évszaknak egy új kezdődik.
     * @param playerTilesSelection A játékos egy lépése.
     * @return ValidationResult, hogy sikerült-e.
     */

    //TODO could not be tested :(
    public ExecutionSeasonResult executePlayerSelection(PlayerTilesSelection playerTilesSelection) {
        var isThereRuinCard = this.discoveryCardsInPlay.size() > 1;
        ValidationResult vr = this.currentSheet.execute(playerTilesSelection, this.discoveryCardsInPlay.peek(), isThereRuinCard);
        //ha hiba akkor vissza hibával
        if(vr!=ValidationResult.Ok)
            return new ExecutionSeasonResult(vr, false, -1, -1, -1, -1, this.currentSeason);
        //ha jó
        //clear cards in play
        //current seasonhöz tartozó deck time-ja nagyobb e mint a season time
        //ha igen end of season (next seasn + score)
        this.discoveryCardsInPlay.clear();
        if(this.discoveryCardDecks.get(currentSeason).getTime()>=this.seasonTimes.get(currentSeason)) {
            /*score
            for (int j=0; j<scoreCards.get(currentSeason).size(); j++) {
                int points=scoreCards.get(currentSeason).get(i).score(currentSheet);
                currentSheet.setScore(points);
            }*/
            currentSheet.setScore(currentSheet.getAccumulatedGold());
            if(currentSeason==Seasons.winter) {
                int a= this.getSeasonalScoreCards(Seasons.winter).get(0).score(this.getCurrentSheet());
                int b= this.getSeasonalScoreCards(Seasons.winter).get(1).score(this.getCurrentSheet());
                int money=this.getCurrentSheet().getAccumulatedGold();
                int monster=this.getCurrentSheet().getMonsterPoints();
                return new ExecutionSeasonResult(ValidationResult.Ok, true, a, b, money, monster, currentSeason);
            }
            else if(currentSeason==Seasons.spring) {
                int a= this.getSeasonalScoreCards(Seasons.spring).get(0).score(this.getCurrentSheet());
                int b= this.getSeasonalScoreCards(Seasons.spring).get(1).score(this.getCurrentSheet());
                int money=this.getCurrentSheet().getAccumulatedGold();
                int monster=this.getCurrentSheet().getMonsterPoints();
                return new ExecutionSeasonResult(ValidationResult.Ok, true, a, b, money,monster, currentSeason);
            }
            else if(currentSeason==Seasons.summer) {
                int a= this.getSeasonalScoreCards(Seasons.summer).get(0).score(this.getCurrentSheet());
                int b= this.getSeasonalScoreCards(Seasons.summer).get(1).score(this.getCurrentSheet());
                int money=this.getCurrentSheet().getAccumulatedGold();
                int monster=this.getCurrentSheet().getMonsterPoints();
                return new ExecutionSeasonResult(ValidationResult.Ok, true, a, b, money, monster, currentSeason);
            }
            else if(currentSeason==Seasons.autumn) {
                int a= this.getSeasonalScoreCards(Seasons.autumn).get(0).score(this.getCurrentSheet());
                int b= this.getSeasonalScoreCards(Seasons.autumn).get(1).score(this.getCurrentSheet());
                int money=this.getCurrentSheet().getAccumulatedGold();
                int monster=this.getCurrentSheet().getMonsterPoints();
                return new ExecutionSeasonResult(ValidationResult.Ok, true, a, b, money, monster, currentSeason);
            }
        }
        return new ExecutionSeasonResult(ValidationResult.Ok, false, -1, -1, -1, -1, currentSeason);
    }

    public List<String> getDrawnDiscoveryCards() {
        List<String> names=new ArrayList<>();
        for(int i=0; i<discoveryCardsInPlay.size(); i++) {
            names.add(discoveryCardsInPlay.get(i).getName());
        }
        return names;
    }

    public List<ScoreCardInfo> getDrawnScoreCards() {
        List<ScoreCardInfo> names = new ArrayList<>();
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.spring).get(0).getName(),this.scoreCards.get(Seasons.spring).get(0).getDescription()));
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.summer).get(0).getName(),this.scoreCards.get(Seasons.summer).get(0).getDescription()));
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.autumn).get(0).getName(),this.scoreCards.get(Seasons.autumn).get(0).getDescription()));
        names.add(new ScoreCardInfo(this.scoreCards.get(Seasons.winter).get(0).getName(),this.scoreCards.get(Seasons.winter).get(0).getDescription()));
        return names;
    }

    public List<TerrainType> getPossibleTerrainTypes() {

        var lastCard = this.discoveryCardsInPlay.get(this.discoveryCardsInPlay.size()-1);
        if (lastCard.getCardType() == DiscoveryCardType.Ambush) {
            return List.of(TerrainType.Monster);
        }
        DiscoveryCard dc = (DiscoveryCard)lastCard;
        List<TerrainType> ret = dc.getPossibleTerrainTypes();
        return ret;
    }

    public Seasons getCurrentSeason() {
        return this.currentSeason;
    }

    public int getCurrentTime() {
        return this.discoveryCardDecks.get(currentSeason).getTime();
    }
    public int getGolds() {
        return this.currentSheet.getAccumulatedGold();
    }

    @Override
    public int getSize() {
        return this.currentSheet.getBoard().getBoardSize();
    }

    @Override
    public TileInfo getTileInfo(int x, int y) {
        var terrainType = this.currentSheet.getBoard().getTerrainType(new Coordinate(x, y));
        var hasRuin = this.currentSheet.getBoard().hasRuin(new Coordinate(x, y));
        return new TileInfo(terrainType, hasRuin);
    }
}


