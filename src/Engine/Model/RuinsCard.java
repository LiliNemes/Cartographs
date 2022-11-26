package Engine.Model;

public class RuinsCard extends DiscoveryCardBase {

    private RuinsCard(String name) {
        super(DiscoveryCardType.Ruin, name);
    }

    public static RuinsCard TempleRuins = new RuinsCard("Temple ruins");
    public static RuinsCard OutpostRuins = new RuinsCard("Outpost ruins");
}
