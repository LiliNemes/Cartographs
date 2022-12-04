package Engine.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A játéktáblát megvalósító osztály.
 */
public class Board implements Serializable {

    private TerrainType[][] tiles;
    private List<Coordinate> ruins;
    private int boardSize;

    public int getBoardSize() {
        return boardSize;
    }

    public Board(int size, List<Coordinate> mountains, List<Coordinate> rifts) {
        this(size, mountains, rifts, null);
    }

        /**
         * Konstruktor.
         * @param size Mekkora legyen a térkép (négyzet oldala).
         * @param mountains Lista, hogy mely koordinátákon legyenek hegyek.
         * @param rifts Lista, hogy mely koordinátákon legyen pusztaság.
         * @param ruins Lista, hogy mely koordinátákon van rom
         */
    public Board(int size, List<Coordinate> mountains, List<Coordinate> rifts, List<Coordinate> ruins) {
        this.boardSize=size;
        this.ruins=ruins;
        this.tiles = new TerrainType[boardSize][boardSize];
        for (int i=0; i<this.boardSize; i++) {
            for (int j=0; j<this.boardSize; j++) {
                this.tiles[i][j] = TerrainType.Empty;
            }
        }
        fillCoordinates(rifts, TerrainType.Rift);
        fillCoordinates(mountains, TerrainType.Mountain);
    }

    /**
     * A paraméterként megadott listában lévő koordinátákra a szintén paraméterként megadott TerrainType kitöltéseket tesz.
     * @param coordinates A koordinátákat tartalmazó lista.
     * @param terrainType Milyen kitöltésre töltse ki a mezőket.
     */
    private void fillCoordinates(List<Coordinate> coordinates, TerrainType terrainType) {
        if (coordinates == null) return;
        for (int idx=0; idx<coordinates.size(); idx++) {
            int x = coordinates.get(idx).getX();
            int y = coordinates.get(idx).getY();
            tiles[x][y] = terrainType;
        }
    }

    /**
     * Ellenőrzi, hogy a játékos által lerakandó mezők rajta vannak-e a térképen illetve üresek-e ezek a mezők.
     * @param playerTilesSelection A játékos által kiválasztott mezők.
     * @return ValidationResult, Ok ha jó, ha nem akkor specifikus a hibára.
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection) {
        List<SelectedTile> selectedTiles=playerTilesSelection.getSelectedTiles();

        for(int i=0; i<selectedTiles.size(); i++) {
            int x=selectedTiles.get(i).getX();
            int y=selectedTiles.get(i).getY();
            if(this.getTerrainType(new Coordinate(x,y))!=TerrainType.Empty)
                return ValidationResult.TileNotEmpty;
        }

        return ValidationResult.Ok;
    }

    public boolean isOnRuin(PlayerTilesSelection playerTilesSelection) {
        List<SelectedTile> selectedTiles=playerTilesSelection.getSelectedTiles();
        List<Coordinate> selectedCoordinates = new ArrayList<Coordinate>();
        for(int i=0; i<selectedTiles.size(); i++) {
            int x=selectedTiles.get(i).getX();
            int y=selectedTiles.get(i).getY();
            selectedCoordinates.add(new Coordinate(x, y));
        }

        return CoordinateHelpers.hasIntersection(selectedCoordinates, this.ruins);
    }

    public void executeAmbush(PlayerTilesSelection playerTilesSelection) {
        for (int i = 0; i < playerTilesSelection.getSelectedTiles().size(); i++) {
            int x = playerTilesSelection.getSelectedTiles().get(i).getX();
            int y = playerTilesSelection.getSelectedTiles().get(i).getY();
            this.tiles[x][y] = TerrainType.Monster;
        }
    }

    /**
     * Végrehajtja a kiválasztott mezők módosítását, ha sikeres a lefolytatott ellenőrzés. Visszaadja a sikerességet
     * illetve a hegyek körbevevésével megszerzett aranyak számát.
     * @param playerTilesSelection A játékos által a lépésben kiválasztott mezők.
     * @return Execution result az eredményről.
     */
    public ExecutionResult execute(PlayerTilesSelection playerTilesSelection) {
        int goldYield=0;
        List<Coordinate> mountains = new ArrayList<>();
        for (int i=0; i<boardSize; i++) {
            for (int j=0; j<boardSize; j++) {
                if(tiles[i][j]==TerrainType.Mountain) {
                    if(tiles[i-1][j]==TerrainType.Empty || tiles[i+1][j]==TerrainType.Empty
                    || tiles[i][j-1]==TerrainType.Empty || tiles[i][j+1]==TerrainType.Empty) {
                        Coordinate e=new Coordinate(i, j);
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

            for (int i = 0; i < mountains.size(); i++) {
                int x = mountains.get(i).getX();
                int y = mountains.get(i).getY();
                if (tiles[x - 1][y] != TerrainType.Empty && tiles[x + 1][y] != TerrainType.Empty &&
                        tiles[x][y - 1] != TerrainType.Empty && tiles[x][y + 1] != TerrainType.Empty) {
                    goldYield++;
                }
            }
            return new ExecutionResult(ValidationResult.Ok, goldYield);
        }
        else {
            return new ExecutionResult(result, 0);
        }
    }

    /**
     * Visszaadja a paraméterként megadott mező típusát.
     * @param coordinate
     * @return
     */
    public TerrainType getTerrainType(Coordinate coordinate) {
        return safeGetTerrainType(coordinate.getX(), coordinate.getY());
    }

    /**
     * Visszaadja egy adott mezővel szomszédos mezők kitöltését.
     * @param coordinate Az adott mező koordinátái.
     * @return A szomszédos mezők típusainak tömbje.
     */
    public List<TerrainType> getNeighboursTerrainType (Coordinate coordinate) {
        List<TerrainType> neighbours=new ArrayList<>();
        if(coordinate.getX()!=0) {
            neighbours.add(tiles[coordinate.getX() - 1][coordinate.getY()]);
        }
        if(coordinate.getX()!=boardSize-1) {
            neighbours.add(tiles[coordinate.getX() + 1][coordinate.getY()]);
        }
        if(coordinate.getY()!=0) {
            neighbours.add(tiles[coordinate.getX()][coordinate.getY() - 1]);
        }
        if(coordinate.getY()!=boardSize-1) {
            neighbours.add(tiles[coordinate.getX()][coordinate.getY() + 1]);
        }
        return neighbours;
    }

    /**
     * Visszaadja az adott mezővel szomszédos, azonos típusú mezők koordinátáit.
     * @param coordinate Az adott mező koordinátája.
     * @return Lista a követelményeknek megfelelő mezők koordinátáiról.
     */
    public List<Coordinate> sameTerrainTypeNeighbours (Coordinate coordinate) {
        List<Coordinate> neighbours = new ArrayList<>();
        if(coordinate.getX()!=0) {
            Coordinate c= new Coordinate(coordinate.getX()-1, coordinate.getY());
            if(this.getTerrainType(c)==this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        if(coordinate.getX()!=boardSize-1) {
            Coordinate c= new Coordinate(coordinate.getX()+1, coordinate.getY());
            if(this.getTerrainType(c)==this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        if(coordinate.getY()!=0) {
            Coordinate c= new Coordinate(coordinate.getX(), coordinate.getY()-1);
            if(this.getTerrainType(c)==this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        if(coordinate.getY()!=boardSize-1) {
            Coordinate c= new Coordinate(coordinate.getX(), coordinate.getY()+1);
            if(this.getTerrainType(c)==this.getTerrainType(coordinate))
                neighbours.add(c);
        }
        return neighbours;
    }

    /**
     * Ellenőrzi, hogy a paraméterként megadott koordináta azonos típusú szomnszédai benne vannak-e a szintén
     * paraméterként megadott régióban.
     * @param region Lista a régióban lévő koordinátákról.
     * @param coordinate A paraméterként megadott koordináta.
     * @return Lista a régióban nem benne lévő, azonos típusú, szmszédos mezők koordinátáival.
     */
    public List<Coordinate> neighbourNotInIt(List<Coordinate> region, Coordinate coordinate) {
        List<Coordinate> ret = new ArrayList<>();
        for(int i=0; i<this.sameTerrainTypeNeighbours(coordinate).size(); i++) {
            boolean notInIt=true;
            for(int j=0; j< region.size(); j++) {
                if(region.get(j).isEqualTo(this.sameTerrainTypeNeighbours(coordinate).get(i)))
                    notInIt=false;
            }
            if(notInIt)
                ret.add(this.sameTerrainTypeNeighbours(coordinate).get(i));
        }
        return ret;
    }

    /**
     * Visszaadja az egybefüggő régiók listáját (régiónként lista koordinátákról) amik a paraméterként kapott típusú
     * kitöltéssel rendelkeznek.
     * @param t Ilyen típusú régiókat keresünk.
     * @return A régiók listája.
     */
    public List<List<Coordinate>> getRegions(TerrainType t) {
        List<List<Coordinate>> allRegions = new ArrayList<>();
        for(int i=0; i<boardSize; i++) {
            for(int j=0; j<boardSize; j++) {
                if(this.tiles[i][j]==t) {
                    Coordinate c = new Coordinate(i, j);
                    boolean notInIt = true;
                    for(int n=0; n<allRegions.size(); n++) {
                        for(int m=0; m<allRegions.get(n).size(); m++) {
                            if(allRegions.get(n).get(m).isEqualTo(c))
                                notInIt=false;
                        }
                    }
                    if(notInIt) {
                        List<Coordinate> oneRegion = new ArrayList<>();
                        oneRegion.add(c);
                        List<Coordinate> okNeighbours= this.neighbourNotInIt(oneRegion, c);
                        int check=okNeighbours.size();
                        for(int p=0; p<okNeighbours.size(); p++) {
                            oneRegion.add(okNeighbours.get(p));
                        }
                        while(check>0) {
                            int param=0;
                            for(int h=0; h<oneRegion.size(); h++) {
                                Coordinate e=oneRegion.get(h);
                                List<Coordinate> okNeighbours2= this.neighbourNotInIt(oneRegion, e);
                                param+= okNeighbours2.size();
                                for(int p=0; p<okNeighbours2.size(); p++) {
                                    oneRegion.add(okNeighbours2.get(p));
                                }
                            }
                            check=param;
                        }
                        allRegions.add(oneRegion);

                    }

                }
            }
        }
        return allRegions;
    }

    public boolean hasRuin(Coordinate coordinate) {
        if (ruins != null) {
            for (int i = 0; i < this.ruins.size(); i++) {
                if (coordinate.isEqualTo(ruins.get(i)))
                    return true;
            }
        }
        return false;
    }

    public boolean canBePlacedOnRuin(Layout normalizedLayout) {
        for (int x=0; x< this.boardSize; x++) {
            for (int y=0; y< this.boardSize; y++) {
                if (canLayoutBePlacedAt(x, y, normalizedLayout) && coversAtLeastASingleRuin(x, y, normalizedLayout))
                    return true;
            }
        }
        return false;
    }

    public boolean canBePlaced(Layout normalizedLayout) {
        for (int x=0; x< this.boardSize; x++) {
            for (int y=0; y< this.boardSize; y++) {
                if (canLayoutBePlacedAt(x, y, normalizedLayout))
                    return true;
            }
        }
        return false;
    }

    private boolean coversAtLeastASingleRuin(int x, int y, Layout normalizedLayout) {
        for (int i=0; i < normalizedLayout.count(); i++) {
            var coordinate = normalizedLayout.getCoordinate(i).offset(x, y);
            if (hasRuin(coordinate)) return true;
        }
        return false;
    }

    // returns the number of free tiles covered by the given layout at the given coordinates
    public int getNumberOfFreeTilesCoveredByLayoutAt(int x, int y, Layout normalizedLayout) {
        int result = 0;
        for (int i=0; i < normalizedLayout.count(); i++) {
            var coordinate = normalizedLayout.getCoordinate(i).offset(x ,y);
            var posX = coordinate.getX();
            var posY = coordinate.getY();
            if (posX >= 0 && posX < this.boardSize && posY >= 0 && posY < this.boardSize) {
                if (tiles[posX][posY] == TerrainType.Empty) result++;
            }
        }
        return result;
    }

    public boolean canLayoutBePlacedAt(int x, int y, Layout normalizedLayout) {
        int freeTiles = getNumberOfFreeTilesCoveredByLayoutAt(x, y, normalizedLayout);
        int layoutTilesNumber = normalizedLayout.count();
        return (freeTiles == layoutTilesNumber);
    }

    public boolean areThereFreeRuinTiles() {
        if (this.ruins == null) return false;
        for (int i=0; i<this.ruins.size(); i++) {
            var ruin = this.ruins.get(i);
            var tile = tiles[ruin.getX()][ruin.getY()];
            if (tile == TerrainType.Empty)
                return true;
        }
        return false;
    }

    public int getMonsterPoints() {
        List<Coordinate> freeTilesNextToMonster = new ArrayList<>();
        for (int x=0; x<boardSize; x++) {
            for (int y=0; y<boardSize; y++) {
                var tile = tiles[x][y];
                if (tile == TerrainType.Monster) {
                    if (safeGetTerrainType(x+1, y) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x+1, y));
                    if (safeGetTerrainType(x-1, y) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x-1, y));
                    if (safeGetTerrainType(x, y+1) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x, y+1));
                    if (safeGetTerrainType(x, y-1) == TerrainType.Empty)
                        freeTilesNextToMonster.add(new Coordinate(x, y-1));
                }
            }
        }
        return Coordinate.removeDuplicates(freeTilesNextToMonster).size();
    }

    private TerrainType safeGetTerrainType(int x, int y) {
        if (x < 0 || x >= boardSize) return TerrainType.Rift;
        if (y < 0 || y >= boardSize) return TerrainType.Rift;
        return tiles[x][y];
    }
}
