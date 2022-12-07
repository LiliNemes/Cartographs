package Engine.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Olyan osztály, mely egy adott forma összes lehelyezési módját illetve azok aranyértékét (van vagy nincs) tartalmazza.
 */
public class ArrangementRule implements Serializable {

    private final ArrayList<Layout> possibleLayouts;

    private final boolean yieldsGold;

    /**
     *
     * @param possibleLayouts Lista a lehetséges lehelyezhető formákról legkisebb koordinátára vetítve.
     * @param yieldsGold Jár-e az adott Layout bármilyen formában törtémő lehelyezésért arany.
     */
    public ArrangementRule(ArrayList<Layout> possibleLayouts, boolean yieldsGold) {
        this.possibleLayouts = possibleLayouts;
        this.yieldsGold = yieldsGold;
    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott kijelölt mezők összessége megfelel-e valamelyik lehelyezhető formának.
     * @param playerTilesSelection A paraméterként kapott kijelölt mezők összessége.
     * @return ValidationResult.Ok ha jó a lehelyezés, ValidationResult.InvalidArrangement ha nem.
     */
    public ValidationResult Check(PlayerTilesSelection playerTilesSelection) {

        if (this.IsAnyMatch(playerTilesSelection.GetLayout()))
            return ValidationResult.Ok;

        return ValidationResult.InvalidArrangement;
    }

    /**
     *
     * @return true ha ad aranyat a forma, false ha nem.
     */
    public boolean isYieldsGold() {
        return yieldsGold;
    }

    /**
     * Megnézi, hogy a paraméterként kapott Layout megegyezik-e bármelyik lehelyezhető formával (possibleLayouts).
     * @param layout A paraméterként kapott forma.
     * @return true ha igen, false ha nem.
     */
    private boolean IsAnyMatch(Layout layout) {
        for (Layout possibleLayout : possibleLayouts) {
            if (possibleLayout.isMatch(layout)) return true;
        }
        return false;
    }

    /**
     *
     * @return A lehelyezhető formák listája.
     */
    public List<Layout> getLayouts() {
        return this.possibleLayouts;
    }
}
