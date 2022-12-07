package Engine.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Koordináták. Ezek alapján különböztethetők meg a játéktábla mezői.
 */
public class Coordinate implements Serializable {
    private final int x;
    private final int y;

    /**
     * Konstruktor, a jelölés a derékszögű koordinátarendszer jelölésének megfelelő. Későbbiekben mindig csak az első
     * síknegyedbe eső koordinátákkal fog számolni.
     * @param x x paraméter
     * @param y y paraméter
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return x értéke.
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y értéke.
     */
    public int getY() {
        return y;
    }

    /**
     * Megnézi, hogy egy koordináta értéke megegyezik-e az adott koordináta értékével.
     *
     * @param o A másik koordináta.
     * @return True ha megegyeznek, false ha nem.
     */
    public boolean equals(Object o) {
        if(o == this)
            return true;

        if(!(o instanceof Coordinate c))
            return false;
        else{
            return (c.getX() == x && c.getY() == y);
        }
    }


    /**
     * A koordinátát eltolja a paraéterként megadott értékekkel.
     * @param x X koordináta eltolása.
     * @param y Y koordináta eltolása.
     * @return Az eltolt koordináta.
     */
    public Coordinate offset(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    /**
     * A paraméterként kapott stringet feldarabolja a "," karakterek mentén majd koordinátát készít belőle.
     * @param inp Feldarabolandó string.
     * @return Az elkészített koordináta, ahol x értéke a "," előtti, y értéke a "," utáni karakter.
     */
    public static Coordinate parse(String inp) {
        String[] pair = inp.split(",");
        return new Coordinate(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
    }

    /**
     * A paraméterként kapott, több koordinátát tartalmazó Stringet feldarabolja a ";" karakterek mentén, majd azokból
     * a parse függvény segítségével koordinátákat készít.
     * @param inp A feldarabolandó string.
     * @return A Stringből kapott koordináták listája.
     */
    public static List<Coordinate> parseList(String inp) {
        List<Coordinate> result = new ArrayList<>();
        String[] split = inp.split(";");
        for (String s : split) {
            result.add(Coordinate.parse(s));
        }
        return result;
    }

    /**
     * A paraméterként kapott, koordinátákat tartalmazó listából kiszedi a szintén paraméterként kapott koordinátákat
     * tartalmazó lista elemeit.
     * @param in A teljes lista.
     * @param coordinatesToRemove Az eltávolítandó elemek.
     * @return A "szűrt" lista.
     */
    public static List<Coordinate> removeCoordinates(List<Coordinate> in, List<Coordinate> coordinatesToRemove) {
        List<Coordinate> result = new ArrayList<>();

        for (Coordinate coordinate : in) {
            boolean hasMatch = false;
            for (Coordinate value : coordinatesToRemove) {
                if (value.equals(coordinate)) {
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch)
                result.add(coordinate);
        }

        return result;
    }

    /**
     * A paraméterként kapott, koordinátákat tartalmazó listából kiszedi a duplikált koordinátákat.
     * @param in Az eredeti lista.
     * @return A "szűrt" lista.
     */
    public static List<Coordinate> removeDuplicates(List<Coordinate> in) {
        List<Coordinate> result = new ArrayList<>();

        for (Coordinate coordinate : in) {
            boolean hasMatch = false;
            for (Coordinate value : result) {
                if (value.equals(coordinate)) {
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch)
                result.add(coordinate);
        }

        return result;
    }
}
