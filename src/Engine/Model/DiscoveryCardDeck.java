package Engine.Model;

import Engine.Builder.ArrangementRuleFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Felfedezőkártyák pakliját reprezentáló osztály.
 */
public class DiscoveryCardDeck implements Serializable {

    /**
     * Marshlands kártya.
     */
    public static DiscoveryCard Marshlands = new DiscoveryCard("Marshlands", 2, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,1;2,1"), false)), List.of(TerrainType.Forest, TerrainType.Water));
    /**
     * Hamlet kártya.
     */
    public static DiscoveryCard Hamlet = new DiscoveryCard("Hamlet", 1, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;1,1"), true), ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0;0,1;1,1"), false)), List.of(TerrainType.Village));
    /**
     * Orchard kártya.
     */
    public static DiscoveryCard Orchard = new DiscoveryCard("Orchard", 2, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0;2,1"), false)), List.of(TerrainType.Farm, TerrainType.Forest));
    /**
     * Fishing Village kártya.
     */
    public static DiscoveryCard FishingVillage = new DiscoveryCard("Fishing Village", 2, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0;3,0"), false)), List.of(TerrainType.Village, TerrainType.Water));
    /**
     * Great River kártya.
     */
    public static DiscoveryCard GreatRiver = new DiscoveryCard("Great River", 1, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2"), true), ArrangementRuleFactory.buildBasedOn(Layout.createLayout("2,0;1,1;2,1;0,2;1,2"), false)), List.of(TerrainType.Water));
    /**
     * Farmland kártya.
     */
    public static DiscoveryCard Farmland = new DiscoveryCard("Farmland", 1, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1"), true), ArrangementRuleFactory.buildBasedOn(Layout.createLayout("1,0;0,1;1,1;1,2;2,1"), false)), List.of(TerrainType.Farm));
    /**
     * Forgotten Forest kártya.
     */
    public static DiscoveryCard ForgottenForest = new DiscoveryCard("Forgotten Forest", 1, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,1"), true), ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;1,1;1,2"), false)), List.of(TerrainType.Forest));
    /**
     * Hinterland Stream kártya.
     */
    public static DiscoveryCard HinterlandStream = new DiscoveryCard("Hinterland Stream", 2, List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,0;2,0"), false)), List.of(TerrainType.Farm, TerrainType.Water));
    /**
     * Homestead kártya.
     */
    public static DiscoveryCard Homestead = new DiscoveryCard("Homestead",
            2,
            List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,1"), false)),
            List.of(TerrainType.Village, TerrainType.Farm));
    /**
     * Treetop Village kártya.
     */
    public static DiscoveryCard TreetopVillage = new DiscoveryCard("Treetop Village",
            2,
            List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("2,0;3,0;0,1;1,1;2,1"), false)),
            List.of(TerrainType.Village, TerrainType.Forest));
    /**
     * RiftLands kártya.
     */
    public static DiscoveryCard RiftLands = new DiscoveryCard("Rift Lands",
            0,
            List.of(ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0"), false)),
            List.of(TerrainType.Village, TerrainType.Water, TerrainType.Forest, TerrainType.Farm, TerrainType.Monster));
    /**
     * Gnoll Raid kártya.
     */
    public static AmbushCard GnollRaid = new AmbushCard("Gnoll Raid",
            Layout.createLayout("0,0;0,1;0,2;1,0;1,2"));
    /**
     * Kobold Onslaught kártya.
     */
    public static AmbushCard KoboldOnslaught = new AmbushCard("Kobold Onslaught",
            Layout.createLayout("0,0;0,1;0,2;1,1"));
    /**
     * Goblin Attack kártya.
     */
    public static AmbushCard GoblinAttack = new AmbushCard("Goblin Attack",
            Layout.createLayout("0,0;1,1;2,2"));
    /**
     * Bugbear Assault kártya.
     */
    public static AmbushCard BugbearAssault = new AmbushCard("Bugbear Assault",
            Layout.createLayout("0,0;0,1;2,0;2,1"));
    private final List<DiscoveryCardBase> cards;
    private int time;
    private int current;

    /**
     * Konstruktor.
     *
     * @param cards A pakliban lévő kártyák.
     */
    public DiscoveryCardDeck(List<DiscoveryCardBase> cards) {
        this.cards = cards;
        this.time = 0;
        this.current = -1;
    }

    /**
     * Létrehoz, megkever egy olyan kártyapaklit, amiben csak a szörnykártyák vannak.
     * @return A pakli.
     */
    public static DiscoveryCardDeck ambushDeck() {
        List<DiscoveryCardBase> deck = new ArrayList<>();
        deck.add(GnollRaid);
        deck.add(GoblinAttack);
        deck.add(KoboldOnslaught);
        deck.add(BugbearAssault);
        Collections.shuffle(deck);
        return new DiscoveryCardDeck(deck);
    }

    /**
     * Létrehozza, megkeveri a felfedezőkártyák pakliját.
     *
     * @return A kész, megkevert pakli.
     */
    public static DiscoveryCardDeck createDeck() {
        List<DiscoveryCardBase> deck = new ArrayList<>();

        deck.add(RuinsCard.TempleRuins);
        deck.add(RiftLands);
        deck.add(Marshlands);
        deck.add(RuinsCard.OutpostRuins);
        deck.add(Orchard);
        deck.add(Hamlet);
        deck.add(FishingVillage);
        deck.add(GreatRiver);
        deck.add(Farmland);
        deck.add(ForgottenForest);
        deck.add(HinterlandStream);
        deck.add(Homestead);
        deck.add(TreetopVillage);
        Collections.shuffle(deck);
        return new DiscoveryCardDeck(deck);
    }

    /**
     * Hozzáadja a paklihoz a paraméterként kapott kártyát, újra megkeveri.
     * @param card Az új kártya.
     * @return A módosított pakli.
     */
    public DiscoveryCardDeck addCardToDeck(DiscoveryCardBase card) {
        this.cards.add(card);
        Collections.shuffle(this.cards);
        return this;
    }

    /**
     * Felhúzza a pakli legfelső kártyáját.
     *
     * @return A pakli legfelső lapja.
     */
    public DiscoveryCardBase draw() {
        var newCard = cards.get(++current);
        if (newCard.getCardType() == DiscoveryCardType.Normal) {
            var discoveryCard = (DiscoveryCard) newCard;
            time += discoveryCard.getTimeCost();
        }
        return newCard;
    }

    /**
     * @return A pakliban eltelt idő.
     */
    public int getTime() {
        return time;
    }
}
