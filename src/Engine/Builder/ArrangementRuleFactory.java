package Engine.Builder;

import Engine.Model.ArrangementRule;
import Engine.Model.Layout;

import java.util.ArrayList;

/**
 * ArrangementRule-t állít elő.
 */
public class ArrangementRuleFactory {
    /**
     * ArrangementRule-t állít elő a paraméteként megadott Layout-ból illetve boolean értékből. Az ArrangementRule
     * fog egy listát a megadott Layoutból tükrözéssel, illetve forgatással előállított, legkisebb koordinátákra vetített
     * Layoutokról, illetve hogy az adott Layout formáira jár-e plusz pénz (ez nyilván a listában található összes
     * Layoutra ugyanaz az érték).
     * @param layout     A lehető legkisebb koordinátákra vetített alakzat.
     * @param yieldsGold Adott alakzatra jár-e pont.
     * @return ArrangementRule, benne listával a forgatások, tükrözések eseteivel.
     */

    public static ArrangementRule buildBasedOn(Layout layout, boolean yieldsGold) {

        ArrayList<Layout> allLayouts = new ArrayList<>();
        allLayouts.add(layout);
        for (int i = 1; i < 4; i++) {
            Layout turned = layout.turn90degrees(i);
            allLayouts.add(turned);
        }
        Layout mirrored = layout.mirror();
        allLayouts.add(mirrored);
        for (int i = 1; i < 4; i++) {
            Layout turned = mirrored.turn90degrees(i);
            allLayouts.add(turned);
        }
        return new ArrangementRule(allLayouts, yieldsGold);
    }

}
