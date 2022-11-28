package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *  A Great City küldetéskártyát reprezentáló osztály.
 */
public class Sc_GreatCity extends ScoreCardBase{
    public Sc_GreatCity() {
        super("GreatCity");
    }

    /**
     * A legnagyobb heggyel nem szomszédos falurégió minden mezőjéért 1 pontot ad.
     * @param sheet A pontozandó lap.
     * @return A pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        List<List<Coordinate>> noMountains = new ArrayList<>();
        for(int i=0; i<b.getRegions(TerrainType.Village).size(); i++) {
            boolean inIt = false;
            for(int j=0; j<b.getRegions(TerrainType.Village).get(i).size(); j++) {
                List<TerrainType> neighbourTerrains = b.getNeighboursTerrainType(b.getRegions(TerrainType.Village).get(i).get(j));
                for(int n=0; n< neighbourTerrains.size(); n++) {
                    if(neighbourTerrains.get(n)==TerrainType.Mountain)
                        inIt = true;
                }
            }
            if(!inIt)
                noMountains.add(b.getRegions(TerrainType.Village).get(i));
        }
        int biggest=0;
        for(int i=0; i< noMountains.size(); i++) {
            if(noMountains.get(i).size()>biggest)
                biggest=noMountains.get(i).size();
        }
        points=biggest;
        return points;
    }
}
