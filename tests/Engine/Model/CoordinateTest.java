package Engine.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CoordinateTest  {
    Coordinate coordinate;

    @Before
    public void init() {
        coordinate = new Coordinate(3, 6);
    }

    /**
     * Equals nem egyenlő koordináták esetén.
     */
    @Test
    public void notEqualsTest() {
        Coordinate other= new Coordinate(6, 3);
        Assert.assertFalse(coordinate.Equals(other));
    }

    /**
     * Equals egyenlő koordináták esetén.
     */
    @Test
    public void equalsTest() {
        Coordinate otherone=new Coordinate(3, 6);
        Assert.assertTrue(coordinate.Equals(otherone));
    }

    /**
     * parse jól megadott adatra
     */
    @Test
    public void parseTestOK() {
        String input = "1,2";
        Coordinate expected = new Coordinate(1, 2);
        Coordinate actual = Coordinate.parse(input);
        Assert.assertTrue(actual.Equals(expected));
    }

    /**
     * parse rosszul megadott adatra -> szám nem lehet ;-vel elválasztva!
     */
    @Test (expected = NumberFormatException.class)
    public void parseTestNotOK() {
        String input = "1;2";
        Coordinate expected = new Coordinate(1, 2);
        Coordinate actual = Coordinate.parse(input);
        Assert.assertTrue(actual.Equals(expected));
    }

    /**
     * parseList jól megadott adatra
     */
    @Test
    public void parseListOK() {
        String input = "3,6;6,3";
        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(3, 6));
        expected.add(new Coordinate(6, 3));
        List<Coordinate> actual = Coordinate.parseList(input);
        for(int i=0; i<expected.size(); i++) {
            Assert.assertTrue(actual.get(i).Equals(expected.get(i)));
        }
    }

    /**
     * parseList rosszul megadott adatra -> egy koordinátának csak attribútuma van és így 4 lenne!
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void parseListNotOK() {
        String input = "3,6,6,3";
        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate(3, 6));
        expected.add(new Coordinate(6, 3));
        List<Coordinate> actual = Coordinate.parseList(input);
        for(int i=0; i<expected.size(); i++) {
            Assert.assertTrue(actual.get(i).Equals(expected.get(i)));
        }
    }

}