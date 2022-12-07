package Engine.Model;

/**
 * A Canal Lake küldetéskártyát reprezentáló osztály.
 */
public class Sc_CanalLake extends ScoreCardBase {
    public Sc_CanalLake() {
        super("CanalLake", "Earn six reputation stars \n" +
                "for each complete row or \n" +
                "complete column of filled \n" +
                "spaces.");
    }

    /**
     * Minden Farmmező mellet lévő Vízmezőért egy pont jár és fordítva.
     *
     * @param sheet A pontozandó lsp.
     * @return A megszerzett pontok száma.
     */
    @Override
    public int score(PlayerSheet sheet) {
        int points = 0;
        Board b = sheet.getBoard();
        for (int i = 0; i < b.getBoardSize(); i++) {
            for (int j = 0; j < b.getBoardSize(); j++) {
                Coordinate c = new Coordinate(i, j);
                boolean isThere = false;
                if (b.getTerrainType(c) == TerrainType.Water) {
                    for (int f = 0; f < b.getNeighboursTerrainType(c).size(); f++) {
                        if (b.getNeighboursTerrainType(c).get(f) == TerrainType.Farm)
                            isThere = true;
                    }
                }
                if (b.getTerrainType(c) == TerrainType.Farm) {
                    for (int f = 0; f < b.getNeighboursTerrainType(c).size(); f++) {
                        if (b.getNeighboursTerrainType(c).get(f) == TerrainType.Water)
                            isThere = true;
                    }
                }
                if (isThere)
                    points++;
            }
        }
        return points;
    }
}
