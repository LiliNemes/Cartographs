package Engine.Model;

import java.util.List;

/**
 * A Treetower küldetéskártyát reprezentáló osztály.
 */
public class Sc_Treetower extends ScoreCardBase {
    public Sc_Treetower() {
        super("Treetower", "Earn one reputation star \n" +
                "for each forest space surrounded \n" +
                "on all four sides by filled \n" +
                "spaces or the edge of the \n" +
                "map.");
    }

    /**
     * 1 pontot ad minden olyan erdő mezőért melynek szomszédságában nincs üres mező.
     *
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getBoardSize(); i++) {
            for (int j = 0; j < b.getBoardSize(); j++) {
                Coordinate c = new Coordinate(i, j);
                if (b.getTerrainType(c) == TerrainType.Forest) {
                    List<TerrainType> neighbours = b.getNeighboursTerrainType(c);
                    boolean noEmpty = true;
                    for (TerrainType neighbour : neighbours) {
                        if (neighbour == TerrainType.Empty) {
                            noEmpty = false;
                            break;
                        }
                    }
                    if (noEmpty)
                        points++;
                }
            }
        }
        return points;
    }
}
