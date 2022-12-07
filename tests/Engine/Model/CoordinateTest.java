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
        Assert.assertFalse(coordinate.equals(other));
    }

    /**
     * Equals egyenlő koordináták esetén.
     */
    @Test
    public void equalsTest() {
        Coordinate otherone=new Coordinate(3, 6);
        Assert.assertTrue(coordinate.equals(otherone));
    }

    /**
     * parse jól megadott adatra
     */
    @Test
    public void parseTestOK() {
        String input = "1,2";
        Coordinate expected = new Coordinate(1, 2);
        Coordinate actual = Coordinate.parse(input);
        Assert.assertTrue(actual.equals(expected));
    }

    /**
     * parse rosszul megadott adatra -> szám nem lehet ;-vel elválasztva!
     */
    @Test (expected = NumberFormatException.class)
    public void parseTestNotOK() {
        String input = "1;2";
        Coordinate expected = new Coordinate(1, 2);
        Coordinate actual = Coordinate.parse(input);
        Assert.assertTrue(actual.equals(expected));
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
            Assert.assertTrue(actual.get(i).equals(expected.get(i)));
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
            Assert.assertTrue(actual.get(i).equals(expected.get(i)));
        }
    }

    /**
     * Van-e közös elemük teszt ha pozitív.
     */
    @Test
    public void intersectionPositiveTest() {
        var coordinates1 = Coordinate.parseList("1,1;2,2;3,3");
        var coordinates2 = Coordinate.parseList("1,5;2,2;6,3");
        var result = CoordinateHelpers.hasIntersection(coordinates1, coordinates2);
        Assert.assertTrue(result);
    }

    /**
     * Van-e közös elemük teszt ha negatív.
     */
    @Test
    public void intersectionNegativeTest() {
        var coordinates1 = Coordinate.parseList("1,1;2,2;3,3");
        var coordinates2 = Coordinate.parseList("1,5;3,5;6,3");
        var result = CoordinateHelpers.hasIntersection(coordinates1, coordinates2);
        Assert.assertFalse(result);
    }


    /**
     * Dupla elemek törlése listábol teszt, ha nincs.
     */
    @Test
    public void deduplicateNoDuplicates() {
        var list = Coordinate.parseList("0,0;1,1;2,2");
        var resultList = Coordinate.removeDuplicates(list);
        Assert.assertTrue(resultList.get(0).equals(new Coordinate(0,0)));
        Assert.assertTrue(resultList.get(1).equals(new Coordinate(1,1)));
        Assert.assertTrue(resultList.get(2).equals(new Coordinate(2,2)));
    }

    /**
     * Dupla elemek törlése listábol teszt, ha van.
     */
    @Test
    public void deduplicateDuplicates() {
        var list = Coordinate.parseList("0,0;1,1;2,2;2,2;1,1;0,0");
        var resultList = Coordinate.removeDuplicates(list);
        Assert.assertEquals(3, resultList.size());
        Assert.assertTrue(resultList.get(0).equals(new Coordinate(0,0)));
        Assert.assertTrue(resultList.get(1).equals(new Coordinate(1,1)));
        Assert.assertTrue(resultList.get(2).equals(new Coordinate(2,2)));
    }

    /**
     * Kijelölt elemek törlése listából teszt.
     */
    @Test
    public void removeItems() {
        var list = Coordinate.parseList("0,0;1,1;2,2;3,3;4,4");
        var resultList = Coordinate.removeCoordinates(list, Coordinate.parseList("0,0;2,2;4,4"));
        Assert.assertEquals(2, resultList.size());
        Assert.assertTrue(resultList.get(0).equals(new Coordinate(1,1)));
        Assert.assertTrue(resultList.get(1).equals(new Coordinate(3,3)));
    }
}