package Engine.Model;

/**
 * A The Broken Road küldetéskártyát reprezentáló osztály.
 */
public class Sc_TheBrokenRoad extends ScoreCardBase {

    public Sc_TheBrokenRoad() {
        super("TheBrokenRoad", "Earn three reputation stars \n" +
                "for each complete diagonal \n" +
                "line of filled spaces that \n" +
                "touches the left and bottom \n" +
                "edges of the map.");
    }

    /**
     * Minden, a térkép bal szélét érintő, teljesen kitöltött átlóért 3 pontot ad.
     *
     * @param sheet Lepontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getBoardSize(); i++) {
            boolean isfull = true;
            int horizontal = 0;
            for (int j = i; j < b.getBoardSize(); j++) {
                Coordinate c = new Coordinate(horizontal, j);
                if (b.getTerrainType(c) == TerrainType.Empty) {
                    isfull = false;
                }
                horizontal++;
            }
            if (isfull)
                points += 3;
        }
        return points;
    }
}
