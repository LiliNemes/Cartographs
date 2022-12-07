package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy adott felfedező kártyát reprezentáló osztály.
 */
public class DiscoveryCard extends DiscoveryCardBase {
    private final int timeCost;
    private final List<ArrangementRule> possibleArrangementRules;

    private final List<TerrainType> possibleTerrainTypes;

    /**
     * Konstruktor.
     *
     * @param name                     A kártya neve
     * @param timeCost                 A kártyán szereplő idő
     * @param possibleArrangementRules A kártyán szereplő elrendezés(ek)
     * @param possibleTerrainTypes     A kártyán szereplő talaj típus(ok)
     */
    public DiscoveryCard(String name, int timeCost, List<ArrangementRule> possibleArrangementRules, List<TerrainType> possibleTerrainTypes) {
        super(DiscoveryCardType.Normal, name);
        this.timeCost = timeCost;
        this.possibleArrangementRules = possibleArrangementRules;
        this.possibleTerrainTypes = possibleTerrainTypes;
    }

    /**
     * A függvény ellenőrzi 3 követelmény mentén, hogy a játékos által kiválasztott mezők megfelelnek-e a kihúzott
     * DiscoveryCard-nak. A 3 követelmény: minden mező, ugyanolyan típusú-e, a típus a kártyán lévő-e, a forma a kártyán
     * lévő-e.
     *
     * @param playerTilesSelection a játékos által kiválasztott mezők
     * @return Helyes-e az elrendezés és ha nem, akkor miért
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection) {

        boolean firstCheck = playerTilesSelection.areAllTerrainsSame();
        if (!firstCheck)
            return ValidationResult.DifferentTerrains;
        TerrainType type = playerTilesSelection.getTerrain();
        boolean okTerrain = false;
        for (TerrainType possibleTerrainType : possibleTerrainTypes) {
            if (type == possibleTerrainType) {
                okTerrain = true;
                break;
            }
        }
        if (!okTerrain)
            return ValidationResult.InvalidTerrain;
        boolean okLayout = false;
        for (ArrangementRule possibleArrangementRule : possibleArrangementRules) {
            ValidationResult vr = possibleArrangementRule.Check(playerTilesSelection);
            if (vr == ValidationResult.Ok)
                okLayout = true;
        }
        if (!okLayout)
            return ValidationResult.InvalidArrangement;
        return ValidationResult.Ok;
    }

    /**
     * Annak az ellenőrzése, hogyha a játékos csak egy mezőt rakott le akkor annak jó-e a típusa.
     * @param playerTilesSelection A játékos által lerakott mező.
     * @return ValidationResult.Ok ha jó, ValidationResult.InvalidTerrain ha nem jó, ValidationResult.OnlySingleReplacement
     * ha több mezőt is lerakott.
     */
    public ValidationResult specialCheck(PlayerTilesSelection playerTilesSelection) {
        if (playerTilesSelection.getSelectedTiles().size() != 1)
            return ValidationResult.OnlySingleReplacement;

        TerrainType type = playerTilesSelection.getTerrain();
        boolean okTerrain = false;
        for (TerrainType possibleTerrainType : possibleTerrainTypes) {
            if (type == possibleTerrainType) {
                okTerrain = true;
                break;
            }
        }
        if (!okTerrain)
            return ValidationResult.InvalidTerrain;

        return ValidationResult.Ok;
    }

    /**
     * Megmondja, hogy a lerakott formáért jár-e arany.
     *
     * @param playerTilesSelection a játékos által kiválasztott mezők.
     * @return Ha a kártyához tartozó Arrangement rule-ok közül olyat valósít meg jól a paraméterként átadott playerTilesSelection
     * amely aranyat ad, akkor a függvény visszatérési értéke igaz, ha nem, akkor hamis.
     */
    public boolean yieldsGold(PlayerTilesSelection playerTilesSelection) {
        boolean one = false;
        for (ArrangementRule possibleArrangementRule : possibleArrangementRules) {
            if (possibleArrangementRule.isYieldsGold()) {
                ValidationResult vr = possibleArrangementRule.Check(playerTilesSelection);
                if (vr == ValidationResult.Ok)
                    one = true;
            }
        }
        boolean two = this.check(playerTilesSelection) == ValidationResult.Ok;
        return two && one;
    }

    /**
     * Visszaadja a kártyán szereplő időt.
     *
     * @return a kártya időértéke
     */
    public int getTimeCost() {
        return timeCost;
    }

    /**
     *
     * @return A kártyához tartozó minden lehetséges lehelyezhető forma (elforgatásokkal, tükrözéssel együtt).
     */
    public List<Layout> getAllLayouts() {
        var result = new ArrayList<Layout>();
        for (ArrangementRule possibleArrangementRule : possibleArrangementRules) {
            result.addAll(possibleArrangementRule.getLayouts());
        }
        return result;
    }

    /**
     * @return Kártyához tartozó lehetséges kitöltések.
     */
    public List<TerrainType> getPossibleTerrainTypes() {
        return possibleTerrainTypes;
    }
}
