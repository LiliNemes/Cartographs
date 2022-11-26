package Engine.Builder;

import Engine.Model.ArrangementRule;
import Engine.Model.Layout;

import java.util.*;

/**
 * ArrangementRule-t állít elő.
 */
public class ArrangementRuleFactory {
    /**
     * ArrangementRule-t állít elő a paraméteként megadott Layout-mól illetve boolean értékből.
     * @param layout A 0-ára betített alakzat.
     * @param yieldsGold Adott alakzatra jár-e pont.
     * @return ArrangementRule, benne listával a forgatások, tükrözések eseteivel.
     */

    public static ArrangementRule buildBasedOn(Layout layout, boolean yieldsGold) {

        ArrayList<Layout> allLayouts = new ArrayList<>();
        allLayouts.add(layout);
        for(int i=1; i<3; i++) {
            Layout turned=layout.turn90degrees(i);
            allLayouts.add(turned);
        }
        Layout mirrored = layout.mirror();
        allLayouts.add(mirrored);
        for(int i=1; i<3; i++) {
            Layout turned=mirrored.turn90degrees(i);
            allLayouts.add(turned);
        }
        return new ArrangementRule(allLayouts, yieldsGold);
    }

}
