package Engine.Model;

/**
 * A Greenbough küldetéskártyát reprezentáló osztály.
 */
public class Sc_Greenbough extends ScoreCardBase {
    public Sc_Greenbough() {
        super("Greenbough", "Earn one reputation star \n" +
                "for each row and column with \n" +
                "at least one forest space. \n" +
                "The same forest space may \n" +
                "be scored in a row and a \n" +
                "column.");
    }

    /**
     * Egy pontot ad minden olyan sorért illetve oszlopért ahol van erdő kitöltésű mező.
     *
     * @param sheet A pontozandó lap.
     * @return A pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getBoardSize(); i++) {
            boolean isGreenRow = false;
            boolean isGreenColumn = false;
            for (int j = 0; j < b.getBoardSize(); j++) {
                Coordinate c = new Coordinate(i, j);
                Coordinate c2 = new Coordinate(j, i);
                if (b.getTerrainType(c) == TerrainType.Forest)
                    isGreenColumn = true;
                if (b.getTerrainType(c2) == TerrainType.Forest)
                    isGreenRow = true;
            }
            if (isGreenColumn)
                points++;
            if (isGreenRow)
                points++;
        }
        return points;
    }
}
