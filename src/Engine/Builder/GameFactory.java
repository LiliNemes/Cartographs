package Engine.Builder;

import Engine.Model.*;

import java.util.*;

public class GameFactory {

    public static GameFactory Instance = new GameFactory();

    public Game createSolitaireGame(String playerName) {

        Map<Seasons, DiscoveryCardDeck> discoveryCardDecks = new HashMap<>();

        discoveryCardDecks.put(Seasons.spring, DiscoveryCardDeck.createDeck(false));
        discoveryCardDecks.put(Seasons.summer, DiscoveryCardDeck.createDeck(false));
        discoveryCardDecks.put(Seasons.autumn, DiscoveryCardDeck.createDeck(false));
        discoveryCardDecks.put(Seasons.winter, DiscoveryCardDeck.createDeck(false));

        Map<Seasons, Integer> seasonTimes = new HashMap<>();

        seasonTimes.put(Seasons.spring, 8);
        seasonTimes.put(Seasons.summer, 8);
        seasonTimes.put(Seasons.autumn, 7);
        seasonTimes.put(Seasons.winter, 6);
        //4 x ScoreCardBase lista tipusonként egy
        Sc_SentinelWood sw=new Sc_SentinelWood();
        Sc_CanalLake cl=new Sc_CanalLake();
        Sc_Borderlands b=new Sc_Borderlands();
        Sc_Wildholds w=new Sc_Wildholds();
        Sc_Shieldgate s=new Sc_Shieldgate();
        Sc_GreengoldPlains gp= new Sc_GreengoldPlains();
        Sc_GreatCity gc=new Sc_GreatCity();
        Sc_Greenbough g=new Sc_Greenbough();
        Sc_Treetower t=new Sc_Treetower();
        Sc_TheCauldrons tc=new Sc_TheCauldrons();
        Sc_MagesValley mv=new Sc_MagesValley();
        Sc_TheBrokenRoad tbr=new Sc_TheBrokenRoad();
        List<ScoreCardBase> type1=new ArrayList<>(List.of(sw, g, t));
        List<ScoreCardBase> type2=new ArrayList<>(List.of(cl, mv));
        List<ScoreCardBase> type3=new ArrayList<>(List.of(b, tc, tbr));
        List<ScoreCardBase> type4=new ArrayList<>(List.of(w, s, gp, gc));

        //megkever
        Collections.shuffle(type1);
        Collections.shuffle(type2);
        Collections.shuffle(type3);
        Collections.shuffle(type4);
        //mindegyikből húz 1
        List<ScoreCardBase> chosenCards = new ArrayList<>();
        chosenCards.add(type1.get(0));
        chosenCards.add(type2.get(0));
        chosenCards.add(type3.get(0));
        chosenCards.add(type4.get(0));
        //ezeket megkever
        Collections.shuffle(chosenCards);
        //4 pakli
        List<ScoreCardBase> chosenCardsSpring = new ArrayList<>();
        List<ScoreCardBase> chosenCardsSummer = new ArrayList<>();
        List<ScoreCardBase> chosenCardsAutumn = new ArrayList<>();
        List<ScoreCardBase> chosenCardsWinter = new ArrayList<>();
        chosenCardsSpring.add(chosenCards.get(0));
        chosenCardsSpring.add(chosenCards.get(1));
        chosenCardsSummer.add(chosenCards.get(1));
        chosenCardsSummer.add(chosenCards.get(2));
        chosenCardsAutumn.add(chosenCards.get(2));
        chosenCardsAutumn.add(chosenCards.get(3));
        chosenCardsWinter.add(chosenCards.get(3));
        chosenCardsWinter.add(chosenCards.get(0));

        Map<Seasons, List<ScoreCardBase>> scoreCards = new HashMap<>();
        scoreCards.put(Seasons.spring, chosenCardsSpring);
        scoreCards.put(Seasons.summer, chosenCardsSummer);
        scoreCards.put(Seasons.autumn, chosenCardsAutumn);
        scoreCards.put(Seasons.winter, chosenCardsWinter);

        return new Game(new PlayerSheet(playerName, BoardDeck.StandardBoard), discoveryCardDecks ,scoreCards, seasonTimes);
    }

}