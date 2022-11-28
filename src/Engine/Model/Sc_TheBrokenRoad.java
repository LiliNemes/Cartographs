package Engine.Model;

/**
 * A The Broken Road küldetéskártyát reprezentáló osztály.
 */
public class Sc_TheBrokenRoad extends ScoreCardBase{

    public Sc_TheBrokenRoad() {
        super("TheBrokenRoad");
    }

    /**
     * Minden, a térkép bal szélét érintő, teljesen kitöltött átlóért 3 pontot ad.
     * @param sheet Lepontozandó lap.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b=sheet.getBoard();
        for(int i=0; i<b.boardSize; i++) {
            boolean isfull=true;
            int horizontal=0;
            for(int j=i; j< b.boardSize; j++) {
                Coordinate c=new Coordinate(horizontal,j);
                if(b.getTerrainType(c)==TerrainType.Empty) {
                    isfull=false;
                }
                horizontal++;
            }
            if(isfull)
                points+=3;
        }
        return points;
    }
}
