package Engine.Model;

/**
 *  A Greenbough küldetéskártyát reprezentáló osztály.
 */
public class Sc_Greenbough extends ScoreCardBase{
    public Sc_Greenbough() {
    }

    /**
     * Egy pontot ad minden olyan sorért illetve oszlopért ahol van erdő kitöltésű mező.
     * @param sheet A pontozandó lap.
     * @return A pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        for(int i=0; i<b.boardSize; i++) {
            boolean isGreenRow=false;
            boolean isGreenColumn=false;
            for(int j=0; j<b.boardSize; j++) {
                Coordinate c=new Coordinate(i, j);
                Coordinate c2=new Coordinate(j, i);
                if(b.getTerrainType(c)==TerrainType.Forest)
                    isGreenColumn=true;
                if(b.getTerrainType(c2)==TerrainType.Forest)
                    isGreenRow=true;
            }
            if(isGreenColumn)
                points++;
            if(isGreenRow)
                points++;
        }
        return points;
    }
}
