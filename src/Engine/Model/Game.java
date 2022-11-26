package Engine.Model;

import UI.IGameEngine;
import UI.TileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A játékot reprezentáló osztály.
 */
public class Game implements IGameEngine {

    private PlayerSheet currentSheet;
    private Map<Seasons, DiscoveryCardDeck> discoveryCardDecks;
    private Map<Seasons, List<ScoreCardBase>> scoreCards;

    private Map<Seasons, Integer> seasonTimes;
    private Seasons currentSeason;

    private Stack<DiscoveryCardBase> discoveryCardsInPlay;

    /**
     * Konstruktor.
     * @param currentSheet A játékos lapja.
     * @param discoveryCardDecks A felfedező kártyák paklija évszakonként.
     * @param scoreCards A pontozókártyák évszakonként.
     * @param seasonTimes Az évszakok és időtartamaik.
     */
    public Game(PlayerSheet currentSheet, Map<Seasons, DiscoveryCardDeck> discoveryCardDecks, Map<Seasons, List<ScoreCardBase>> scoreCards, Map<Seasons, Integer> seasonTimes) {
        this.currentSheet = currentSheet;
        this.discoveryCardDecks = discoveryCardDecks;
        this.scoreCards = scoreCards;
        this.seasonTimes=seasonTimes;
        this.currentSeason = Seasons.spring;
        this.discoveryCardsInPlay = new Stack<>();
    }

    /**
     * Addig húz, amíg nem ruin kártya jön.
     */
    //TODO could not be tested! :( Tapasztalati alapon okk.
    public void drawNextCard() {
        //ckeck if inplay cards is empty
        // draw till not ruin
        if(discoveryCardsInPlay.empty()) {
            DiscoveryCardDeck currentDeck =discoveryCardDecks.get(currentSeason);
            var newCard = currentDeck.draw();
            discoveryCardsInPlay.push(newCard);
            while(discoveryCardsInPlay.get(discoveryCardsInPlay.size()-1).getCardType()==DiscoveryCardType.Ruin) {
                var newCard2 = currentDeck.draw();
                discoveryCardsInPlay.push(newCard2);
            }

        }
    }

    /**
     * Végrehajtja a játékos egy lépését. Ha vége az adott évszaknak egy új kezdődik.
     * @param playerTilesSelection A játékos egy lépése.
     * @return ValidationResult, hogy sikerült-e.
     */

    //TODO could not be tested :(
    public ValidationResult executePlayerSelection(PlayerTilesSelection playerTilesSelection) {
        int i = this.discoveryCardsInPlay.size()-1;
        ValidationResult vr = this.currentSheet.execute(playerTilesSelection,(DiscoveryCard) this.discoveryCardsInPlay.get(i));
        //ha hiba akkor vissza hibával
        if(vr!=ValidationResult.Ok)
            return vr;
        //ha jó
        //clear cards in play
        //current seasonhöz tartozó deck time-ja nagyobb e mint a season time
        //ha igen end of season (next seasn + score)
        this.discoveryCardsInPlay.clear();
        if(this.discoveryCardDecks.get(currentSeason).getTime()>=this.seasonTimes.get(currentSeason)) {
            //score
            for (int j=0; j<scoreCards.get(currentSeason).size(); j++) {
                int points=scoreCards.get(currentSeason).get(i).score(currentSheet);
                currentSheet.setScore(points);
            }
            currentSheet.setScore(currentSheet.getAccumulatedGold());
            if(currentSeason==Seasons.winter) {
                //TODO Game over
            }
            else if(currentSeason==Seasons.spring) {
                    currentSeason=Seasons.summer;
            }
            else if(currentSeason==Seasons.summer) {
                    currentSeason=Seasons.autumn;
            }
            else if(currentSeason==Seasons.autumn) {
                currentSeason = Seasons.winter;
            }
        }
        return ValidationResult.Ok;

    }

    public List<String> getDrawnDiscoveryCards() {
        List<String> names=new ArrayList<>();
        for(int i=0; i<discoveryCardsInPlay.size(); i++) {
            names.add(discoveryCardsInPlay.get(i).getName());
        }
        return names;
    }

    public List<TerrainType> getPossibleTerrainTypes() {

        DiscoveryCard dc = (DiscoveryCard) this.discoveryCardsInPlay.get(this.discoveryCardsInPlay.size()-1);
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
        return this.currentSheet.getBoard().boardSize;
    }

    @Override
    public TileInfo getTileInfo(int x, int y) {
        var terrainType = this.currentSheet.getBoard().getTerrainType(new Coordinate(x, y));
        var hasRuin = this.currentSheet.getBoard().hasRuin(new Coordinate(x, y));
        return new TileInfo(terrainType, hasRuin);
    }
}


