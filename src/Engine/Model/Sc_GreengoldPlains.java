package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Greengold Plains küldetéskártyát reprezentáló osztály.
 */
public class Sc_GreengoldPlains extends ScoreCardBase {

    public Sc_GreengoldPlains() {
        super("GreengoldPlains", "Earn three reputation stars \n" +
                "for each cluster of villages \n" +
                "spaces that is adjacent to \n" +
                "three or more different terrain \n" +
                "types.");
    }

    /**
     * 3 pont jár olyan falu régiónkként, amik 3 vagy annál több különböző kitöltésű területtel szomszédosak.
     *
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getRegions(TerrainType.Village).size(); i++) {
            List<TerrainType> neighbouringTerrains = new ArrayList<>();
            for (int j = 0; j < b.getRegions(TerrainType.Village).get(i).size(); j++) {
                List<TerrainType> param = b.getNeighboursTerrainType(b.getRegions(TerrainType.Village).get(i).get(j));
                for (TerrainType terrainType : param) {
                    boolean inIt = false;
                    for (TerrainType neighbouringTerrain : neighbouringTerrains) {
                        if (neighbouringTerrain == terrainType) {
                            inIt = true;
                            break;
                        }
                    }
                    if (!inIt && terrainType != TerrainType.Empty && terrainType != TerrainType.Rift && terrainType != TerrainType.Village)
                        neighbouringTerrains.add(terrainType);
                }
            }
            if (neighbouringTerrains.size() >= 3)
                points += 3;
        }
        return points;
    }
}
