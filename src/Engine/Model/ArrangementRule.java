package Engine.Model;

import java.util.ArrayList;

public class ArrangementRule {

    private ArrayList<Layout> possibleLayouts;

    private boolean yieldsGold;

    public ArrangementRule(ArrayList<Layout> possibleLayouts, boolean yieldsGold) {
        this.possibleLayouts = possibleLayouts;
        this.yieldsGold = yieldsGold;
    }

    public ValidationResult Check(PlayerTilesSelection playerTilesSelection) {

        if (this.IsAnyMatch(playerTilesSelection.GetLayout()))
            return ValidationResult.Ok;

        return ValidationResult.InvalidArrangement;
    }

    public boolean isYieldsGold() {
        return yieldsGold;
    }

    private boolean IsAnyMatch(Layout layout) {
        for (int i=0; i<possibleLayouts.size(); i++) {
            if (possibleLayouts.get(i).isMatch(layout)) return true;
        }
        return false;
    }
}
