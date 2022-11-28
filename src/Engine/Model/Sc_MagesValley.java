package Engine.Model;

import java.util.List;

/**
 *  A Mages Valley küldetéskártyát reprezentáló osztály.
 */
public class Sc_MagesValley extends ScoreCardBase{
    public Sc_MagesValley() {
        super("MagesValley");
    }

    /**
     * Minden hegy melletti víz mező 2, hegy melletti farm mező 1 pontot ér.
     * @param sheet A pontozandó lap.
     * @return A szerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b = sheet.getBoard();
        for(int i=0; i<b.boardSize; i++) {
            for(int j=0; j<b.boardSize; j++) {
                Coordinate c=new Coordinate(i, j);
                if(b.getTerrainType(c)==TerrainType.Mountain) {
                    List<TerrainType> neighbours = b.getNeighboursTerrainType(c);
                    for(int n=0; n< neighbours.size(); n++) {
                        if(neighbours.get(n)==TerrainType.Water)
                            points+=2;
                        if(neighbours.get(n)==TerrainType.Farm)
                            points++;
                    }
                }
            }
        }
        return points;
    }
}
