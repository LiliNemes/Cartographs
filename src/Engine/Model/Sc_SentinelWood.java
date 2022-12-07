package Engine.Model;

/**
 * A Sentinel Wood küldetéskártyát reprezentáló osztály.
 */
public class Sc_SentinelWood extends ScoreCardBase {

    public Sc_SentinelWood() {
        super("Sentinel Wood", "Earn one reputation star \n" +
                "for each forest space adjacent \n" +
                "to the edge of the map.");
    }

    /**
     * 1 pont jár minden, a tábla szélével szomszédos erdővel kitöltött mezőért.
     *
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getBoardSize(); i++) {
            Coordinate c = new Coordinate(0, i);
            if (b.getTerrainType(c) == TerrainType.Forest)
                points++;
        }
        for (int i = 1; i < b.getBoardSize() - 1; i++) {
            Coordinate c = new Coordinate(i, 0);
            if (b.getTerrainType(c) == TerrainType.Forest)
                points++;
        }
        for (int i = 0; i < b.getBoardSize(); i++) {
            Coordinate c = new Coordinate(b.getBoardSize() - 1, i);
            if (b.getTerrainType(c) == TerrainType.Forest)
                points++;
        }
        for (int i = 1; i < b.getBoardSize() - 1; i++) {
            Coordinate c = new Coordinate(i, b.getBoardSize() - 1);
            if (b.getTerrainType(c) == TerrainType.Forest)
                points++;
        }
        return points;
    }
}
