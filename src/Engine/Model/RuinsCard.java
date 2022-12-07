package Engine.Model;

/**
 * A romkártyákat megvalósító osztály.
 */
public class RuinsCard extends DiscoveryCardBase {

    /**
     * Konstruktor.
     * @param name A kártya neve.
     */
    private RuinsCard(String name) {
        super(DiscoveryCardType.Ruin, name);
    }

    /**
     * Temple Ruins kártya.
     */
    public static RuinsCard TempleRuins = new RuinsCard("Temple ruins");
    /**
     * Outpost Ruins kártya.
     */
    public static RuinsCard OutpostRuins = new RuinsCard("Outpost ruins");
}
