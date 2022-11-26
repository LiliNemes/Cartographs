package Engine.Model;

public class Sc_Shieldgate extends ScoreCardBase{
    public Sc_Shieldgate() {
    }

    @Override
    public int score(PlayerSheet sheet) {
        int points=0;
        Board b=sheet.getBoard();
        int biggest=b.getRegions(TerrainType.Village).get(0).size();
        int secondBiggest=0;
        for(int i=0; i<b.getRegions(TerrainType.Village).size(); i++) {
            if(b.getRegions(TerrainType.Village).get(i).size()>=biggest) {
                biggest = b.getRegions(TerrainType.Village).get(i).size();
            }
            if(b.getRegions(TerrainType.Village).get(i).size()<biggest && b.getRegions(TerrainType.Village).get(i).size()>secondBiggest) {
                secondBiggest=b.getRegions(TerrainType.Village).get(i).size();
            }
        }
        points=secondBiggest*2;
        return points;
    }
}
