package Engine.Model;

public class Sc_TheBrokenRoad extends ScoreCardBase{
    public Sc_TheBrokenRoad() {
    }

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
