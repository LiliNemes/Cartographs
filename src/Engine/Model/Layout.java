package Engine.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Koordinátákból álló forma,
 */
public class Layout implements Serializable {
    private final List<Coordinate> coordinates;

    /**
     * Létrehoz egy Layoutot.
     * @param inp String amiben a koordináták vannak.
     * @return A koordinátákból készített forma.
     */
    public static Layout createLayout(String inp) {
        return new Layout(Coordinate.parseList(inp));
    }

    /**
     * Konstruktor.
     * @param coordinates Koordináták.
     */
    public Layout(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Ellenőrzi, hogy a paraméterként megadott Layout megegyezik-e az adott Layouttal.
     * @param otherLayout a másik Layout.
     * @return True ha megegyeznek a koordinátáik, false ha nem.
     */
    public boolean isMatch(Layout otherLayout) {
        if (otherLayout.coordinates.size() != coordinates.size()) return false;

        for (int i = 0; i < otherLayout.coordinates.size(); i++) {
            var otherCord = otherLayout.coordinates.get(i);
            if (!this.hasCoordinate(otherCord)) return false;
        }
        return true;
    }

    /**
     * Elfordítja a Layoutot paraméterként megadott számszor 90 fokkal.
     * @param times Hányszor forgassuk el 90 fokkal.
     * @return Az elforgatott Layout.
     */
    public Layout turn90degrees(int times) {
        int biggestX=this.coordinates.get(0).getX();
        int biggestY=this.coordinates.get(0).getY();
        for (Coordinate coordinate : this.coordinates) {
            if (coordinate.getX() > biggestX)
                biggestX = coordinate.getX();
            if (coordinate.getY() > biggestY)
                biggestY = coordinate.getY();
        }
        int param = Math.max(biggestX, biggestY);
        List<Coordinate> turnedCoordinates = new ArrayList<>();
        for (Coordinate coordinate : this.coordinates) {
            int x = coordinate.getY();
            int y = (-1 * coordinate.getX()) + param;
            for (int j = 0; j < times - 1; j++) {
                int p = x;
                x = y;
                y = (p * -1) + param;
            }
            Coordinate turned = new Coordinate(x, y);
            turnedCoordinates.add(turned);
        }
        Layout turned=new Layout(turnedCoordinates);
        turned=turned.project();
        return turned;
    }

    /**
     * A Layoutot 'levetíti' a bal felső sarokba (0,0)-ás koordinátához.
     * @return A levetített Layout.
     */
    public Layout project() {
        int smallestX=this.coordinates.get(0).getX();
        int smallestY=this.coordinates.get(0).getY();
        for (Coordinate coordinate : this.coordinates) {
            if (coordinate.getX() < smallestX)
                smallestX = coordinate.getX();
            if (coordinate.getY() < smallestY)
                smallestY = coordinate.getY();
        }
        List<Coordinate> projectedCoordinates = new ArrayList<>();
        for (Coordinate coordinate : this.coordinates) {
            int x = coordinate.getX() - smallestX;
            int y = coordinate.getY() - smallestY;
            Coordinate c = new Coordinate(x, y);
            projectedCoordinates.add(c);
        }
        return new Layout(projectedCoordinates);
    }

    /**
     * A Layoutot x koordinátára tükrözi.
     * @return A tükrözött Layout.
     */
    public Layout mirror() {
        int biggestX=this.coordinates.get(0).getX();
        for (Coordinate coordinate : this.coordinates) {
            if (coordinate.getX() > biggestX)
                biggestX = coordinate.getX();
        }
        List<Coordinate> mirroredCoordinates = new ArrayList<>();
        for (Coordinate coordinate : this.coordinates) {
            int x = biggestX - coordinate.getX();
            Coordinate c = new Coordinate(x, coordinate.getY());
            mirroredCoordinates.add(c);
        }
        Layout mirrored = new Layout(mirroredCoordinates);
        mirrored=mirrored.project();
        return mirrored;
    }

    /**
     * Új Layoutot készít a Layoutot körülvevő mezőkből.
     * @return Az új Layout.
     */
    public Layout surroundings() {
        List<Coordinate> surroundingCoordinates = new ArrayList<>();
        for (Coordinate coordinate : this.coordinates) {
            surroundingCoordinates.add(coordinate.offset(1, 0));
            surroundingCoordinates.add(coordinate.offset(-1, 0));
            surroundingCoordinates.add(coordinate.offset(0, 1));
            surroundingCoordinates.add(coordinate.offset(0, -1));
        }
        List<Coordinate> noDuplicates = Coordinate.removeDuplicates(surroundingCoordinates);
        List<Coordinate> cleansed = Coordinate.removeCoordinates(noDuplicates, this.coordinates);
        return new Layout(cleansed);
    }

    /**
     *
     * @return Hány mezőből áll a Layout.
     */
    public int count() {
        return this.coordinates.size();
    }

    /**
     *
     * @param idx index.
     * @return A megadott indexű koordinátája a Layoutnak.
     */
    public Coordinate getCoordinate(int idx) {
        return this.coordinates.get(idx);
    }

    /**
     * A paraméterként megadott koordináta a Layout koordinátái között van-e.
     * @param coordinate A paraméterként megadott koordináta.
     * @return True ha igen, false ha nem.
     */
    private boolean hasCoordinate(Coordinate coordinate) {
        for (Coordinate value : coordinates) {
            if (value.equals(coordinate)) return true;
        }
        return false;
    }
}
