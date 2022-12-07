package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Great City küldetéskártyát reprezentáló osztály.
 */
public class Sc_GreatCity extends ScoreCardBase {
    public Sc_GreatCity() {
        super("GreatCity", "Earn one reputation star \n" +
                "for each village space in \n" +
                "the largest cluster of village \n" +
                "spaces that is not adjacent \n" +
                "to a mountain space.");
    }

    /**
     * A legnagyobb heggyel nem szomszédos falurégió minden mezőjéért 1 pontot ad.
     *
     * @param sheet A pontozandó lap.
     * @return A pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points;
        Board b = sheet.getBoard();
        List<List<Coordinate>> noMountains = new ArrayList<>();
        for (int i = 0; i < b.getRegions(TerrainType.Village).size(); i++) {
            boolean inIt = false;
            for (int j = 0; j < b.getRegions(TerrainType.Village).get(i).size(); j++) {
                List<TerrainType> neighbourTerrains = b.getNeighboursTerrainType(b.getRegions(TerrainType.Village).get(i).get(j));
                for (TerrainType neighbourTerrain : neighbourTerrains) {
                    if (neighbourTerrain == TerrainType.Mountain) {
                        inIt = true;
                        break;
                    }
                }
            }
            if (!inIt) noMountains.add(b.getRegions(TerrainType.Village).get(i));
        }
        int biggest = 0;
        for (List<Coordinate> noMountain : noMountains) {
            if (noMountain.size() > biggest) biggest = noMountain.size();
        }
        points = biggest;
        return points;
    }
}
