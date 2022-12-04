package Engine.Model;

import java.util.List;

/**
 * A TheCauldrons küldetéskártyát reprezentáló osztály.
 */
public class Sc_TheCauldrons extends ScoreCardBase{

    public Sc_TheCauldrons() {
        super("TheCauldrons", "Earn one \n" +
                "reputation star for each empty \n" +
                "space surrounded on all four \n" +
                "sides by filled spaces or the \n" +
                "edge of the map");
    }

    /**
     * Minden Olyan üres mezőért aminek nincs üres szomszédja 1 pont jár.
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        for(int i=0; i<b.getBoardSize(); i++) {
            for(int j=0; j<b.getBoardSize(); j++) {
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
