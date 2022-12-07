package Engine.Model;

import java.util.List;

/**
 * A Mages Valley küldetéskártyát reprezentáló osztály.
 */
public class Sc_MagesValley extends ScoreCardBase {
    public Sc_MagesValley() {
        super("MagesValley", "Earn two reputation stars \n" +
                "for each water space adjacent \n" +
                "to a mountain space. Earn \n" +
                "one reputation star for each \n" +
                "farm space adjacent to a \n" +
                "mountain space.");
    }

    /**
     * Minden hegy melletti víz mező 2, hegy melletti farm mező 1 pontot ér.
     *
     * @param sheet A pontozandó lap.
     * @return A szerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getBoardSize(); i++) {
            for (int j = 0; j < b.getBoardSize(); j++) {
                Coordinate c = new Coordinate(i, j);
                if (b.getTerrainType(c) == TerrainType.Mountain) {
                    List<TerrainType> neighbours = b.getNeighboursTerrainType(c);
                    for (TerrainType neighbour : neighbours) {
                        if (neighbour == TerrainType.Water)
                            points += 2;
                        if (neighbour == TerrainType.Farm)
                            points++;
                    }
                }
            }
        }
        return points;
    }
}
