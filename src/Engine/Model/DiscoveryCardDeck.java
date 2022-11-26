package Engine.Model;

import Engine.Builder.ArrangementRuleFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Felfedezőkártyák pakliját reprezentáló osztály.
 */
public class DiscoveryCardDeck {

    private final List<DiscoveryCardBase> cards;
    private int time;
    private int current;

    /**
     * Konstruktor.
     * @param cards A pakliban lévő kártyák.
     */
    public DiscoveryCardDeck(List<DiscoveryCardBase> cards) {
        this.cards = cards;
        this.time = 0;
        this.current = -1;
    }

    /**
     * Létrehozza, megkeveri a felfedezőkártyák pakliját.
     * @param useAmbushCards legyenek-e benne AmbushCard-ok.
     * @return A kész, megkevert pakli.
     */
    public static DiscoveryCardDeck createDeck(boolean useAmbushCards) {
        List<DiscoveryCardBase> deck=new ArrayList<>();
        deck.add(Marshlands);
        deck.add(Orchard);
        deck.add(Hamlet);
        deck.add(FishingVillage);
        deck.add(GreatRiver);
        deck.add(Farmland);
        deck.add(ForgottenForest);
        deck.add(HinterlandStream);
        deck.add(Homestead);
        deck.add(TreetopVillage);
        deck.add(RiftLands);
        deck.add(RuinsCard.TempleRuins);
        deck.add(RuinsCard.OutpostRuins);
        DiscoveryCardDeck dc = new DiscoveryCardDeck(deck);
        Collections.shuffle(dc.cards);
        return dc;
    }

    /**
     * Felhúzza a pakli legfelső kártyáját.
     * @return A pakli legfelső lapja.
     */
    public DiscoveryCardBase draw() {
        var newCard = cards.get(++current);
        if (newCard.getCardType() == DiscoveryCardType.Normal)
        {
            var discoveryCard = (DiscoveryCard)newCard;
            time += discoveryCard.getTimeCost();
        }
        return newCard;
    }

    //TODO miért
    public DiscoveryCardBase getCurrent() {
        if (current < 0 || current >= cards.size())
            return null;
        return cards.get(current);
    }

    /**
     *
     * @return A pakliban eltelt idő.
     */
    public int getTime() {
        return time;
    }

    /**
     * Marshlands kártya.
     */
    public static DiscoveryCard Marshlands = new DiscoveryCard("Marshlands", 2,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,1;2,1"), false)
            ),
            List.of(TerrainType.Forest, TerrainType.Water));

    /**
     * Hamlet kártya.
     */
    public static DiscoveryCard Hamlet = new DiscoveryCard("Hamlet", 1,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;1,1"), true),
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0;0,1;1,1"), false)
            ),
            List.of(TerrainType.Village));

    /**
     * Orchard kártya.
     */
    public static DiscoveryCard Orchard = new DiscoveryCard("Orchard", 2,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0;2,1"), false)
            ),
            List.of(TerrainType.Farm, TerrainType.Forest));

    /**
     * Fishing Village kártya.
     */
    public static DiscoveryCard FishingVillage = new DiscoveryCard("Fishing Village", 2,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,0;2,0;3,0"), false)
            ),
            List.of(TerrainType.Village, TerrainType.Water));

    /**
     * Great River kártya.
     */
    public static DiscoveryCard GreatRiver = new DiscoveryCard("Great River", 1,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2"), true),
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("2,0;1,1;2,1;0,2;1,2"), false)
            ),
            List.of(TerrainType.Water));

    /**
     * Farmland kártya.
     */
    public static DiscoveryCard Farmland = new DiscoveryCard("Farmland", 1,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1"), true),
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("1,0;0,1;1,1;1,2;2,1"), false)
            ),
            List.of(TerrainType.Farm));

    /**
     * Forgotten Forest kártya.
     */
    public static DiscoveryCard ForgottenForest = new DiscoveryCard("Forgotten Forest", 1,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;1,1"), true),
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;1,1;1,2"), false)
            ),
            List.of(TerrainType.Forest));

    /**
     * Hinterland Stream kártya.
     */
    public static DiscoveryCard HinterlandStream = new DiscoveryCard("Hinterland Stream", 2,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,0;2,0"), false)
            ),
            List.of(TerrainType.Farm, TerrainType.Water));

    /**
     * Homestead kártya.
     */
    public static DiscoveryCard Homestead = new DiscoveryCard("Homestead", 2,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0;0,1;0,2;1,1"), false)
            ),
            List.of(TerrainType.Village, TerrainType.Farm));

    /**
     * Treetop Village kártya.
     */
    public static DiscoveryCard TreetopVillage = new DiscoveryCard("Treetop Village", 2,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("2,0;3,0;0,1;1,1;2,1"), false)
            ),
            List.of(TerrainType.Village, TerrainType.Forest));

    /**
     * RiftLands kártya.
     */
    public static DiscoveryCard RiftLands = new DiscoveryCard("Rift Lands", 0,
            List.of(
                    ArrangementRuleFactory.buildBasedOn(Layout.createLayout("0,0"), false)
            ),
            List.of(TerrainType.Village, TerrainType.Water, TerrainType.Forest, TerrainType.Farm, TerrainType.Monster));
}
