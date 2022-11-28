package Engine.Model;

import java.util.List;

/**
 * A Treetower küldetéskártyát reprezentáló osztály.
 */
public class Sc_Treetower extends ScoreCardBase{
    public Sc_Treetower() {
        super("Treetower");
    }

    /**
     * 1 pontot ad minden olyan erdő mezőért melynek szomszédságában nincs üres mező.
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        for(int i=0; i<b.boardSize; i++) {
            for(int j=0; j<b.boardSize; j++) {
                Coordinate c = new Coordinate(i, j);
                if(b.getTerrainType(c)==TerrainType.Forest) {
                    List<TerrainType> neighbours=b.getNeighboursTerrainType(c);
                    boolean noEmpty = true;
                    for(int n=0; n< neighbours.size(); n++) {
                        if(neighbours.get(n)==TerrainType.Empty) {
                            noEmpty=false;
                        }
                    }
                    if(noEmpty)
                        points++;
                }
            }
        }
        return points;
    }
}
