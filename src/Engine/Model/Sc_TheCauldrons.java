package Engine.Model;

import java.util.List;

public class Sc_TheCauldrons extends ScoreCardBase{

    public Sc_TheCauldrons() {
    }

    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        for(int i=0; i<b.boardSize; i++) {
            for(int j=0; j<b.boardSize; j++) {
                Coordinate c = new Coordinate(i, j);
                if(b.getTerrainType(c)==TerrainType.Empty) {
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
