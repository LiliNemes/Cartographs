package Engine.Model;

/**
 * A Borderlands küldetéskártyát reprezentáló osztály.
 */
public class Sc_Borderlands extends ScoreCardBase{
    public Sc_Borderlands() {
        super("Borderlands",
                "Earn six reputation stars \n" +
                        "for each complete row or \n" +
                        "complete column of filled \n" +
                        "spaces.");
    }

    /**
     * Pontott ad (6-ot) minden teli sorra és teli oszlopra.
     * @param sheet A lepontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b = sheet.getBoard();
        for(int i=0; i<b.getBoardSize(); i++) {
            boolean isColumnFull = true;
            boolean isRowFull = true;
            for(int j=0; j<b.getBoardSize(); j++) {
                Coordinate c=new Coordinate(i, j);
                if(b.getTerrainType(c)==TerrainType.Empty)
                    isColumnFull = false;
                Coordinate c2=new Coordinate(j, i);
                if(b.getTerrainType(c2)==TerrainType.Empty)
                    isRowFull = false;
            }
            if(isColumnFull)
                points+=6;
            if(isRowFull)
                points+=6;
        }
        return points;
    }

}
