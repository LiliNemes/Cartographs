package Engine.Builder;

import Engine.Model.*;

import java.io.*;
import java.util.*;

/**
 * Magát a játékot felépítő osztály.
 */
public class GameFactory {

    public static GameFactory Instance = new GameFactory();

    /**
     * Létrehoz egy játékot. Előre elkészíti és megkeveri évszakonként a DiscoveryCardDeck-eket, kihúzza az évszakokra
     * érvényes scoreCard-okat, elkészíti a játékpályét.
     * @param playerName A játékos által megadható név (ScoreBoard miatt fontos).
     * @param isHard A játékpálya létrehozása miatt szükséges, tőle függ melyik pályalétrehozó függvényt híja a függvény (sima vagy szakadékokkal nehezített).
     * @param random A játékpálya létrehozásához szükséges, előre elkészített fix pálya legyeb-e vagy random.
     * @return A létrehozott, felépített játék.
     */
    public Game createSolitaireGame(String playerName, boolean isHard, boolean random) {

        Map<Seasons, DiscoveryCardDeck> discoveryCardDecks = new HashMap<>();

        var ambushDeck = DiscoveryCardDeck.ambushDeck();
        discoveryCardDecks.put(Seasons.spring, DiscoveryCardDeck.createDeck().addCardToDeck(ambushDeck.draw()));
        discoveryCardDecks.put(Seasons.summer, DiscoveryCardDeck.createDeck().addCardToDeck(ambushDeck.draw()));
        discoveryCardDecks.put(Seasons.autumn, DiscoveryCardDeck.createDeck().addCardToDeck(ambushDeck.draw()));
        discoveryCardDecks.put(Seasons.winter, DiscoveryCardDeck.createDeck().addCardToDeck(ambushDeck.draw()));

        Map<Seasons, Integer> seasonTimes = new HashMap<>();

        seasonTimes.put(Seasons.spring, 8);
        seasonTimes.put(Seasons.summer, 8);
        seasonTimes.put(Seasons.autumn, 7);
        seasonTimes.put(Seasons.winter, 6);

        //4 x ScoreCardBase lista tipusonként egy
        Sc_SentinelWood sw = new Sc_SentinelWood();
        Sc_CanalLake cl = new Sc_CanalLake();
        Sc_Borderlands b = new Sc_Borderlands();
        Sc_Wildholds w = new Sc_Wildholds();
        Sc_Shieldgate s = new Sc_Shieldgate();
        Sc_GreengoldPlains gp = new Sc_GreengoldPlains();
        Sc_GreatCity gc = new Sc_GreatCity();
        Sc_Greenbough g = new Sc_Greenbough();
        Sc_Treetower t = new Sc_Treetower();
        Sc_TheCauldrons tc = new Sc_TheCauldrons();
        Sc_MagesValley mv = new Sc_MagesValley();
        Sc_TheBrokenRoad tbr = new Sc_TheBrokenRoad();
        List<ScoreCardBase> type1 = new ArrayList<>(List.of(sw, g, t));
        List<ScoreCardBase> type2 = new ArrayList<>(List.of(cl, mv));
        List<ScoreCardBase> type3 = new ArrayList<>(List.of(b, tc, tbr));
        List<ScoreCardBase> type4 = new ArrayList<>(List.of(w, s, gp, gc));

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

        Board board = isHard ? BoardDeck.createHardBoard(random) : BoardDeck.createBoard(random);
        return new Game(new PlayerSheet(playerName, board), discoveryCardDecks, scoreCards, seasonTimes);
    }

    /**
     * Függvény a játék mentéséhez, file-ba írásához.
     * @param fileName Mi legyen a file neve.
     * @param game A játék amit el kell menteni.
     */
    public void saveGame(String fileName, Game game) {
        try {
            FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(game);
            o.close();
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Függvény mentett játék betöltéséhez, fileból.
     * @param fileName A beolvasandó file neve.
     * @return A beolvasott játék.
     */
    public Game loadGame(String fileName) {
        try {
            FileInputStream f = new FileInputStream(fileName);
            ObjectInputStream o = new ObjectInputStream(f);
            var game = (Game) o.readObject();
            o.close();
            f.close();
            return game;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
