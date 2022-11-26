package Engine.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Koordináták. Ezek alapján különböztethetők meg a játéktábla mezői.
 */
public class Coordinate {
    private int x;
    private int y;

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
     * @param coordinate A másik koordináta.
     * @return True ha megegyeznek, false ha nem.
     */
    public boolean Equals(Coordinate coordinate) {
        return (coordinate.getX() == x && coordinate.getY() == y);
    }

    /**
     * A paraméterként kapott stringet feldarabolja a "," karakterek mentén majd koordinátát készít belőle.
     * @param inp Feldarabolandó string.
     * @return Az elkészített koordináta, ahol x értéke a "," előtti, y értéke a "," utáni karakter.
     */
    public static Coordinate parse(String inp) {
        String[] pair = inp.split(",");
        Coordinate coordinate = new Coordinate(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
        return coordinate;
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
        for(int i=0; i< split.length; i++) {
            result.add(Coordinate.parse(split[i]));
        }
        return result;
    }
}
