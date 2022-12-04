package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *  A Greengold Plains küldetéskártyát reprezentáló osztály.
 */
public class Sc_GreengoldPlains extends ScoreCardBase{

    public Sc_GreengoldPlains() {
        super("GreengoldPlains", "Earn three \n" +
                "reputation stars for each \n" +
                "cluster of villages spaces that \n" +
                "is adjacent to three or more \n" +
                "different terrain types.");
    }

    /**
     * 3 pont jár olyan falu régiónkként, amik 3 vagy annál több különböző kitöltésű területtel szomszédosak.
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for(int i=0; i<b.getRegions(TerrainType.Village).size();i++) {
            List<TerrainType> neighbouringTerrains=new ArrayList<>();
            for(int j=0; j<b.getRegions(TerrainType.Village).get(i).size(); j++) {
                List<TerrainType> param = b.getNeighboursTerrainType(b.getRegions(TerrainType.Village).get(i).get(j));
                for(int n=0; n<param.size(); n++) {
                    boolean inIt = false;
                    for(int m=0; m<neighbouringTerrains.size(); m++) {
                        if(neighbouringTerrains.get(m)==param.get(n))
                            inIt=true;
                    }
                    if(!inIt && param.get(n)!=TerrainType.Empty && param.get(n)!=TerrainType.Rift && param.get(n)!=TerrainType.Village)
                        neighbouringTerrains.add(param.get(n));
                }
            }
            if(neighbouringTerrains.size()>=3)
                points+=3;
        }
        return points;
    }
}
