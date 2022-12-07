package UI;

public interface IBoardInfo {
    /**
     *
     * @return A tábla mérete (hány sor/oszlop).
     */
    int getSize();

    /**
     * Az adott koordinátájú mezőről szolgáltat TileInfo formájában adatokat.
     * @param x X koordináta.
     * @param y Y koordináta.
     * @return TileInfo a mezőről.
     */
    TileInfo getTileInfo(int x, int y);
}
