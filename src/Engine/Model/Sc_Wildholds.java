package Engine.Model;

public class Sc_Wildholds extends ScoreCardBase{
    public Sc_Wildholds() {
    }

    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        for(int i=0; i<b.getRegions(TerrainType.Village).size(); i++) {
            if(b.getRegions(TerrainType.Village).get(i).size()>=6) {
                points+=8;
            }
        }
        return points;
    }
}
