package Engine.Model;

/**
 * A Shieldgate küldetéskártyát reprezentáló osztály.
 */
public class Sc_Shieldgate extends ScoreCardBase{
    public Sc_Shieldgate() {
        super("Shieldgate");
    }

    /**
     * Két pontot kapsz a második legnagyobb összefóüggő falumeződ minden mezője után.
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
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
