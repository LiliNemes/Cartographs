package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A játéktáblát megvalósító osztály.
 */
public class Board {

    private TerrainType[][] tiles;
    private List<Coordinate> ruins;
    int boardSize;

    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Konstruktor.
     * @param size Mekkora legyen a térkép (négyzet oldala).
     * @param mountains Lista, hogy mely koordinátákon legyenek hegyek.
     * @param rifts Lista, hogy mely koordinátákon legyen pusztaság.
     */
    public Board(int size, List<Coordinate> mountains, List<Coordinate> rifts) {
        boardSize=size;
        tiles = new TerrainType[boardSize][boardSize];
        for (int i=0; i<boardSize; i++) {
            for (int j=0; j<boardSize; j++) {
                tiles[i][j] = TerrainType.Empty;
            }
        }
        //TODO nem muszáj rift!
        //TODO hegy nem lehet a térkép szélén!
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
     * @param isThereRuin Van-e romkártya a játékban.
     * @return ValidationResult, Ok ha jó, ha nem akkor specifikus a hibára.
     */
    public ValidationResult check(PlayerTilesSelection playerTilesSelection, boolean isThereRuin) {
        List<SelectedTile> selectedTiles=playerTilesSelection.getSelectedTiles();
        for(int i=0; i<selectedTiles.size(); i++) {
            int x=selectedTiles.get(i).getX();
            int y=selectedTiles.get(i).getY();
            if(this.getTerrainType(new Coordinate(x,y))!=TerrainType.Empty)
                return ValidationResult.TileNotEmpty;
        }
        return ValidationResult.Ok;
    }

    /**
     * Végrehajtja a kiválasztott mezők módosítását, ha sikeres a lefolytatott ellenőrzés. Visszaadja a sikerességet
     * illetve a hegyek körbevevésével megszerzett aranyak számát.
     * @param playerTilesSelection A játékos által a lépésben kiválasztott mezők.
     * @param isThereRuin Van-e romkártya éppen a játékban.
     * @return Execution result az eredményről.
     */
    public ExecutionResult execute(PlayerTilesSelection playerTilesSelection, boolean isThereRuin) {
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
        var result = check(playerTilesSelection, isThereRuin);
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
        return tiles[coordinate.getX()][coordinate.getY()];
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
                if(region.get(j).Equals(this.sameTerrainTypeNeighbours(coordinate).get(i)))
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
                            if(allRegions.get(n).get(m).Equals(c))
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
                if (coordinate.Equals(ruins.get(i)))
                    return true;
            }
        }
        return false;
    }
}
