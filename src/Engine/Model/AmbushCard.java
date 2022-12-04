package Engine.Model;

import Engine.Builder.ArrangementRuleFactory;

public class AmbushCard extends DiscoveryCardBase {

    private ArrangementRule arrangementRule;

    public AmbushCard(String name, Layout layout) {
        super(DiscoveryCardType.Ambush, name);
        this.arrangementRule = ArrangementRuleFactory.buildBasedOn(layout, false);
    }

    public PlayerTilesSelection calculateAmbush(Board board) {

        int currentDamage = 0;
        Layout currentLayout = null;
        int currentX = -1;
        int currentY = -1;

        var layouts = arrangementRule.getLayouts();
        for (int i=0; i<layouts.size(); i++) {
            var layout = layouts.get(i);
            var surrounding = layout.surroundings();
            for (int x=0; x<board.getBoardSize(); x++) {
                for (int y=0; y<board.getBoardSize(); y++) {
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
