package Engine.Model;

import java.util.List;

/**
 * Egy adott felfedező kártyát reprezentáló osztály.
 */
public class DiscoveryCard extends DiscoveryCardBase {
    private int timeCost;
    private List<ArrangementRule> possibleArrangementRules;

    /**
     *
     * @return Kártyához tartozó lehetséges kitöltések.
     */
    public List<TerrainType> getPossibleTerrainTypes() {
        return possibleTerrainTypes;
    }

    private List<TerrainType> possibleTerrainTypes;

    /**
     * Konstruktor.
     * @param name A kártya neve
     * @param timeCost A kártyán szereplő idő
     * @param possibleArrangementRules A kártyán szereplő elrendezés(ek)
     * @param possibleTerrainTypes A kártyán szereplő talaj típus(ok)
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
     * @param playerTilesSelection a játékos által kiválasztott mezők
     * @return Helyes-e az elrendezés és ha nem, akkor miért
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection) {

        boolean firstCheck = playerTilesSelection.areAllTerrainsSame();
        if(!firstCheck)
            return ValidationResult.DifferentTerrains;
        TerrainType type=playerTilesSelection.getTerrain();
        boolean okTerrain=false;
        for(int i=0; i<possibleTerrainTypes.size(); i++) {
            if(type==possibleTerrainTypes.get(i))
                okTerrain=true;
        }
        if(!okTerrain)
            return ValidationResult.InvalidTerrain;
        Layout a=playerTilesSelection.GetLayout();
        boolean okLayout=false;
        for(int i=0; i<possibleArrangementRules.size(); i++) {
            ValidationResult vr = possibleArrangementRules.get(i).Check(playerTilesSelection);
            if(vr==ValidationResult.Ok)
                okLayout=true;
        }
        if(!okLayout)
            return ValidationResult.InvalidArrangement;
        return ValidationResult.Ok;
    }

    /**
     * Megmondja, hogy a lerakott formáért jár-e arany.
     * @param playerTilesSelection a játékos által kiválasztott mezők.
     * @return Ha a kártyához tartozó Arrangement rule-ok közül olyat valósít meg jól a paraméterként átadott playerTilesSelection
     * amely aranyat ad, akkor a függvény visszatérési értéke igaz, ha nem, akkor hamis.
     */
    public boolean yieldsGold(PlayerTilesSelection playerTilesSelection) {
        boolean one=false;
        for(int i=0; i<possibleArrangementRules.size(); i++) {
            if(possibleArrangementRules.get(i).isYieldsGold()) {
                ValidationResult vr = possibleArrangementRules.get(i).Check(playerTilesSelection);
                if(vr==ValidationResult.Ok)
                    one = true;
            }
        }
        boolean two=false;
        if(this.check(playerTilesSelection)==ValidationResult.Ok)
            two=true;
        return two&&one;
    }

    /**
     * Visszaadja a kártyán szereplő időt.
     * @return a kártya időértéke
     */
    public int getTimeCost() {
        return timeCost;
    }
}
