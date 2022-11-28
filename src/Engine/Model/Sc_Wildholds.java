package Engine.Model;
/**
 * A Wildholds küldetéskártyát reprezentáló osztály.
 */
public class Sc_Wildholds extends ScoreCardBase{
    public Sc_Wildholds() {
        super("Wildholds");
    }

    /**
     * 8 pontot ad minden 6 vagy annál több mezőből álló egybefüggő falurégióért.
     * @param sheet A pontozandó lap.
     * @return A megszerzett pontok száma.
     */
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
