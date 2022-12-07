package Engine.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A játéktáblát megvalósító osztály.
 */
public class Board implements Serializable {

    private final TerrainType[][] tiles;
    private final List<Coordinate> ruins;
    private final int boardSize;

    public Board(int size, List<Coordinate> mountains, List<Coordinate> rifts) {
        this(size, mountains, rifts, null);
    }

    /**
     * Konstruktor.
     *
     * @param size      Mekkora legyen a térkép (négyzet oldala).
     * @param mountains Lista, hogy mely koordinátákon legyenek hegyek.
     * @param rifts     Lista, hogy mely koordinátákon legyen pusztaság.
     * @param ruins     Lista, hogy mely koordinátákon van rom.
     */
    public Board(int size, List<Coordinate> mountains, List<Coordinate> rifts, List<Coordinate> ruins) {
        this.boardSize = size;
        this.ruins = ruins;
        this.tiles = new TerrainType[boardSize][boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.tiles[i][j] = TerrainType.Empty;
            }
        }
        fillCoordinates(rifts, TerrainType.Rift);
        fillCoordinates(mountains, TerrainType.Mountain);
    }

    /**
     *
     * @return A játékpálya mérete.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * A paraméterként megadott listában lévő koordinátákra a szintén paraméterként megadott TerrainType kitöltéseket tesz.
     *
     * @param coordinates A koordinátákat tartalmazó lista.
     * @param terrainType Milyen kitöltésre töltse ki a mezőket.
     */
    private void fillCoordinates(List<Coordinate> coordinates, TerrainType terrainType) {
        if (coordinates == null) return;
        for (Coordinate coordinate : coordinates) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            tiles[x][y] = terrainType;
        }
    }

    /**
     * Ellenőrzi, hogy a játékos által lerakandó mezők rajta vannak-e a térképen illetve üresek-e ezek a mezők.
     *
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     * @return ValidationResult, Ok ha jó, ha nem akkor specifikus a hibára.
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection) {
        List<SelectedTile> selectedTiles = playerTilesSelection.getSelectedTiles();

        for (SelectedTile selectedTile : selectedTiles) {
            int x = selectedTile.getX();
            int y = selectedTile.getY();
            if (this.getTerrainType(new Coordinate(x, y)) != TerrainType.Empty)
                return ValidationResult.TileNotEmpty;
        }

        return ValidationResult.Ok;
    }

    /**
     * Megnézi a paraméterként kapott PlayerTilesSelection mezőire, hogy van-e köztük olyan ami ruin-on van.
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     * @return true, ha valamelyik mező romra esik, false ha egyik sem.
     */
    public boolean isOnRuin(PlayerTilesSelection playerTilesSelection) {
        List<SelectedTile> selectedTiles = playerTilesSelection.getSelectedTiles();
        List<Coordinate> selectedCoordinates = new ArrayList<>();
        for (SelectedTile selectedTile : selectedTiles) {
            int x = selectedTile.getX();
            int y = selectedTile.getY();
            selectedCoordinates.add(new Coordinate(x, y));
        }

        return CoordinateHelpers.hasIntersection(selectedCoordinates, this.ruins);
    }

    /**
     * Lehelyezi a paraméterkéntt kapott PlayerTilesSelection mezőit Monster kitöltéssel (ez a függvény rakja le a
     * játék által kiszámolt helyre a szörnykártyák alakzatait).
     * @param playerTilesSelection A kijelölt mezők összessége.
     */
    public void executeAmbush(PlayerTilesSelection playerTilesSelection) {
        for (int i = 0; i < playerTilesSelection.getSelectedTiles().size(); i++) {
            int x = playerTilesSelection.getSelectedTiles().get(i).getX();
            int y = playerTilesSelection.getSelectedTiles().get(i).getY();
            this.tiles[x][y] = TerrainType.Monster;
        }
    }

    /**
     * Végrehajtja a kiválasztott mezők módosítását, ha sikeres a lefolytatott ellenőrzés. Visszaadja a sikerességet
     * illetve a hegyek körbevevésével megszerzett aranyak számát. Algoritmus hegyeknél: Van-e olyan hegy, ami a
     * lehelyezés előtt nem volt körbevéve, de a lehelyezés után már igen.
     *
     * @param playerTilesSelection A játékos által a lépésben kiválasztott mezők.
     * @return Execution result az eredményről.
     */
    public ExecutionResult execute(PlayerTilesSelection playerTilesSelection) {
        int goldYield = 0;
        List<Coordinate> mountains = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == TerrainType.Mountain) {
                    if (tiles[i - 1][j] == TerrainType.Empty || tiles[i + 1][j] == TerrainType.Empty
                            || tiles[i][j - 1] == TerrainType.Empty || tiles[i][j + 1] == TerrainType.Empty) {
                        Coordinate e = new Coordinate(i, j);
                        mountains.add(e);
                    }

                }
            }
        }
        var result = check(playerTilesSelection);
        if (result == ValidationResult.Ok) {
            for (int i = 0; i < playerTilesSelection.getSelectedTiles().size(); i++) {
                int x = playerTilesSelection.getSelectedTiles().get(i).getX();
                int y = playerTilesSelection.getSelectedTiles().get(i).getY();
                TerrainType t = playerTilesSelection.getSelectedTiles().get(i).getTerrainType();
                this.tiles[x][y] = t;
            }

            for (Coordinate mountain : mountains) {
                int x = mountain.getX();
                int y = mountain.getY();
                if (tiles[x - 1][y] != TerrainType.Empty && tiles[x + 1][y] != TerrainType.Empty &&
                        tiles[x][y - 1] != TerrainType.Empty && tiles[x][y + 1] != TerrainType.Empty) {
                    goldYield++;
                }
            }
            return new ExecutionResult(ValidationResult.Ok, goldYield);
        } else {
            return new ExecutionResult(result, 0);
        }
    }

    /**
     * Visszaadja a paraméterként megadott mező típusát.
     *
     * @param coordinate a kiválasztott mező koordinátája.
     * @return a mező típusa.
     */
    public TerrainType getTerrainType(Coordinate coordinate) {
        return safeGetTerrainType(coordinate.getX(), coordinate.getY());
    }

    /**
     * Visszaadja egy adott mezővel szomszédos mezők kitöltését.
     *
     * @param coordinate Az adott mező koordinátái.
     * @return A szomszédos mezők típusainak tömbje.
     */
    public List<TerrainType> getNeighboursTerrainType(Coordinate coordinate) {
        List<TerrainType> neighbours = new ArrayList<>();
        if (coordinate.getX() != 0) {
            neighbours.add(tiles[coordinate.getX() - 1][coordinate.getY()]);
        }
        if (coordinate.getX() != boardSize - 1) {
            neighbours.add(tiles[coordinate.getX() + 1][coordinate.getY()]);
        }
        if (coordinate.getY() != 0) {
            neighbours.add(tiles[coordinate.getX()][coordinate.getY() - 1]);
        }
        if (coordinate.getY() != boardSize - 1) {
            neighbours.add(tiles[coordinate.getX()][coordinate.getY() + 1]);
        }
        return neighbours;
    }

    /**
     * Visszaadja az adott mezővel szomszédos, azonos típusú mezők koordinátáit.
     *
     * @param coordinate Az adott mező koordinátája.
     * @return Lista a követelményeknek megfelelő mezők koordinátáiról.
     */
    public List<Coordinate> sameTerrainTypeNeighbours(Coordinate coordinate) {
        List<Coordinate> neighbours = new ArrayList<>();
        if (coordinate.getX() != 0) {
            Coordinate c = new Coordinate(coordinate.getX() - 1, coordinate.getY());
            if (this.getTerrainType(c) == this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        if (coordinate.getX() != boardSize - 1) {
            Coordinate c = new Coordinate(coordinate.getX() + 1, coordinate.getY());
            if (this.getTerrainType(c) == this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        if (coordinate.getY() != 0) {
            Coordinate c = new Coordinate(coordinate.getX(), coordinate.getY() - 1);
            if (this.getTerrainType(c) == this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        if (coordinate.getY() != boardSize - 1) {
            Coordinate c = new Coordinate(coordinate.getX(), coordinate.getY() + 1);
            if (this.getTerrainType(c) == this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        return neighbours;
    }

    /**
     * Ellenőrzi, hogy a paraméterként megadott koordináta azonos típusú szomnszédai benne vannak-e a szintén
     * paraméterként megadott régióban.
     *
     * @param region     Lista a régióban lévő koordinátákról.
     * @param coordinate A paraméterként megadott koordináta.
     * @return Lista a régióban nem benne lévő, azonos típusú, szmszédos mezők koordinátáival.
     */
    public List<Coordinate> neighbourNotInIt(List<Coordinate> region, Coordinate coordinate) {
        List<Coordinate> ret = new ArrayList<>();
        for (int i = 0; i < this.sameTerrainTypeNeighbours(coordinate).size(); i++) {
            boolean notInIt = true;
            for (Coordinate value : region) {
                if (value.equals(this.sameTerrainTypeNeighbours(coordinate).get(i)))
                    notInIt = false;
            }
            if (notInIt)
                ret.add(this.sameTerrainTypeNeighbours(coordinate).get(i));
        }
        return ret;
    }

    /**
     * Visszaadja az egybefüggő régiók listáját (régiónként lista koordinátákról) amik a paraméterként kapott típusú
     * kitöltéssel rendelkeznek.
     *
     * @param t Ilyen típusú régiókat keresünk.
     * @return A régiók listája.
     */
    public List<List<Coordinate>> getRegions(TerrainType t) {
        List<List<Coordinate>> allRegions = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.tiles[i][j] == t) {
                    Coordinate c = new Coordinate(i, j);
                    boolean notInIt = true;
                    for (List<Coordinate> allRegion : allRegions) {
                        for (Coordinate coordinate : allRegion) {
                            if (coordinate.equals(c)) {
                                notInIt = false;
                                break;
                            }
                        }
                    }
                    if (notInIt) {
                        List<Coordinate> oneRegion = new ArrayList<>();
                        oneRegion.add(c);
                        List<Coordinate> okNeighbours = this.neighbourNotInIt(oneRegion, c);
                        int check = okNeighbours.size();
                        oneRegion.addAll(okNeighbours);
                        while (check > 0) {
                            int param = 0;
                            for (int h = 0; h < oneRegion.size(); h++) {
                                Coordinate e = oneRegion.get(h);
                                List<Coordinate> okNeighbours2 = this.neighbourNotInIt(oneRegion, e);
                                param += okNeighbours2.size();
                                oneRegion.addAll(okNeighbours2);
                            }
                            check = param;
                        }
                        allRegions.add(oneRegion);

                    }

                }
            }
        }
        return allRegions;
    }

    /**
     * Megnézi, hogy a paraméterként kapott koordináta ruin mezőn van-e.
     * @param coordinate Az ellenőrizendő koordináta.
     * @return true ha rajta van, false ha nem.
     */
    public boolean hasRuin(Coordinate coordinate) {
        if (ruins != null) {
            for (Coordinate ruin : this.ruins) {
                if (coordinate.equals(ruin))
                    return true;
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott Layout-ot l lehet-e úgy helyezni, hogy a lehelyezés szabályos legyen és
     * legalább egy rom mezőt magába foglaljon.
     * @param normalizedLayout Az ellenőrizendő Layout.
     * @return true ha le lehet helyezni így, false ha nem.
     */
    public boolean canBePlacedOnRuin(Layout normalizedLayout) {
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                if (canLayoutBePlacedAt(x, y, normalizedLayout) && coversAtLeastASingleRuin(x, y, normalizedLayout))
                    return true;
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott Layout-ot le lehet-e helyezni szabályosan a pályára.
     * @param normalizedLayout A lehelyezendő Layout.
     * @return true ha le lehet, false ha nem.
     */
    public boolean canBePlaced(Layout normalizedLayout) {
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                if (canLayoutBePlacedAt(x, y, normalizedLayout))
                    return true;
            }
        }
        return false;
    }

    /**
     * Lehelyezhető-e úgy egy adott forma egy adott helyre, hogy legyen olyan mezője melyen rom található.
     * @param x X koordináta eltolásának értéke.
     * @param y Y koordináta eltolásának értéke.
     * @param normalizedLayout A forma amit meg kell vizsgálni.
     * @return true ha lehelyezhető, false ha nem.
     */
    private boolean coversAtLeastASingleRuin(int x, int y, Layout normalizedLayout) {
        for (int i = 0; i < normalizedLayout.count(); i++) {
            var coordinate = normalizedLayout.getCoordinate(i).offset(x, y);
            if (hasRuin(coordinate)) return true;
        }
        return false;
    }

    /**
     * Megadja, hogy az adott helyre lehelyezett adott forma hány mezője esik kitöltetlen mezőre.
     * @param x X koordináta eltolásának értéke.
     * @param y Y koordináta eltolásának értéke.
     * @param normalizedLayout A forma amit meg kell vizsgálni.
     * @return Hány mezője kitöltetlen.
     */
    public int getNumberOfFreeTilesCoveredByLayoutAt(int x, int y, Layout normalizedLayout) {
        int result = 0;
        for (int i = 0; i < normalizedLayout.count(); i++) {
            var coordinate = normalizedLayout.getCoordinate(i).offset(x, y);
            var posX = coordinate.getX();
            var posY = coordinate.getY();
            if (posX >= 0 && posX < this.boardSize && posY >= 0 && posY < this.boardSize) {
                if (tiles[posX][posY] == TerrainType.Empty) result++;
            }
        }
        return result;
    }

    /**
     * Megadja, hogy adott forma adott helyre szabályosan lehelyezhető-e.
     * @param x X koordináta eltolásának értéke.
     * @param y Y koordináta eltolásának értéke.
     * @param normalizedLayout A forma amit meg kell vizsgálni.
     * @return true ha lehelyezhető, false ha nem.
     */
    public boolean canLayoutBePlacedAt(int x, int y, Layout normalizedLayout) {
        int freeTiles = getNumberOfFreeTilesCoveredByLayoutAt(x, y, normalizedLayout);
        int layoutTilesNumber = normalizedLayout.count();
        return (freeTiles == layoutTilesNumber);
    }

    /**
     * Megadja, hogy van-e olyan mező a táblán amin rom található és a kitöltése üres.
     * @return true ha van, false ha nincs.
     */
    public boolean areThereFreeRuinTiles() {
        if (this.ruins == null) return false;
        for (Coordinate ruin : this.ruins) {
            var tile = tiles[ruin.getX()][ruin.getY()];
            if (tile == TerrainType.Empty)
                return true;
        }
        return false;
    }

    /**
     * Megadja a Monster kitöltésű mezőkkel szomszédos üres mezők számát (hány mínusz pont).
     * @return Hány ilyen mező van.
     */
    public int getMonsterPoints() {
        List<Coordinate> freeTilesNextToMonster = new ArrayList<>();
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                var tile = tiles[x][y];
                if (tile == TerrainType.Monster) {
                    if (safeGetTerrainType(x + 1, y) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x + 1, y));
                    if (safeGetTerrainType(x - 1, y) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x - 1, y));
                    if (safeGetTerrainType(x, y + 1) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x, y + 1));
                    if (safeGetTerrainType(x, y - 1) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x, y - 1));
                }
            }
        }
        return Coordinate.removeDuplicates(freeTilesNextToMonster).size();
    }

    /**
     * Visszaadja a megadott koordinátán lévő mező TerrainType-át. Tábláról kilógó mező esetén szakadék.
     * @param x X koordináta értéke.
     * @param y Y koordináta értéke.
     * @return Megadott mezőn lévő kitöltés.
     */
    private TerrainType safeGetTerrainType(int x, int y) {
        if (x < 0 || x >= boardSize) return TerrainType.Rift;
        if (y < 0 || y >= boardSize) return TerrainType.Rift;
        return tiles[x][y];
    }
}
