package Engine.Model;

import java.util.List;

/**
 * A TheCauldrons küldetéskártyát reprezentáló osztály.
 */
public class Sc_TheCauldrons extends ScoreCardBase {

    public Sc_TheCauldrons() {
        super("TheCauldrons", "Earn one reputation star \n" +
                "for each empty space surrounded \n" +
                "on all four sides by filled \n" +
                "spaces or the edge of the \n" +
                "map.");
    }

    /**
     * Minden Olyan üres mezőért aminek nincs üres szomszédja 1 pont jár.
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
                if (b.getTerrainType(c) == TerrainType.Empty) {
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
