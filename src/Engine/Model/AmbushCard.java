package Engine.Model;

import Engine.Builder.ArrangementRuleFactory;

/**
 * Olyan kártyákat megvalósító osztály, amik speciálisak (a játék helyez le nem a játékos, mínuszpont szerezhető általa).
 * A továbbiakban szörnykártya vagy ambush card.
 */
public class AmbushCard extends DiscoveryCardBase {

    private final ArrangementRule arrangementRule;

    /**
     *
     * @param name A kártya neve.
     * @param layout A kártyához tartozó Layout.
     */
    public AmbushCard(String name, Layout layout) {
        super(DiscoveryCardType.Ambush, name);
        this.arrangementRule = ArrangementRuleFactory.buildBasedOn(layout, false);
    }

    /**
     * Kiválasztott mezők kombinációját adja vissza, melyekre rakja majd a játék a szörnykártya formáját, hogy az az
     * (egyik) legelőnytelenebb legyen a játékos számára. Az algoritmus: A forma minden lehetséges elhelyezésére megnézi,
     * hogy mely lehetséges helyekre fér el a táblán. Amennyiben talál egy, a formával megegyező méretű és elrendezésű
     * üres helyet kiszámolja, hogy a lehelyezett ábrával szomszédos mezőkből álló Layout-nak hány üres mezője van.
     * Amennyiben ez a szám nagyobb, mint az eddigi legnagyobb ilyen érték, ez lesz az. Ha nem fér el akkor üres
     * PlayerTilesSelectiont hoz létre, ha elfér akkor a kikeresett értékeknek megfelelőt.
     * @param board A játékpálya amin el kell majd helyezni a formát.
     * @return A PlayerTilesSelection ahova a formát le kell raknia a játéknak.
     */
    public PlayerTilesSelection calculateAmbush(Board board) {

        int currentDamage = 0;
        Layout currentLayout = null;
        int currentX = -1;
        int currentY = -1;

        var layouts = arrangementRule.getLayouts();
        for (Layout layout : layouts) {
            var surrounding = layout.surroundings();
            for (int x = 0; x < board.getBoardSize(); x++) {
                for (int y = 0; y < board.getBoardSize(); y++) {
                    if (board.canLayoutBePlacedAt(x, y, layout)) {
                        int damageCaused = board.getNumberOfFreeTilesCoveredByLayoutAt(x, y, surrounding);
                        if (damageCaused > currentDamage) {
                            currentDamage = damageCaused;
                            currentLayout = layout;
                            currentX = x;
                            currentY = y;
                        }
                    }
                }
            }
        }

        var result = new PlayerTilesSelection();
        //special case, when monsters cannot be placed at all
        if (currentX < 0) return result;

        for (int i=0; i<currentLayout.count(); i++) {
            var coordinate = currentLayout.getCoordinate(i);
            result.addTile(coordinate.getX() + currentX, coordinate.getY() + currentY, TerrainType.Monster);
        }
        return result;
    }

}
