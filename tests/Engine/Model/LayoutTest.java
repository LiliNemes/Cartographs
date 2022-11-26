package Engine.Model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LayoutTest {

    /**
     * isMatch tesztelése, ha nem ugyanabban a sorrendben vannak megadva a koordináták.
     */
    @Test
    public void matchesDespiteOfOrder() {
        Layout layout1 = Layout.createLayout("0,0;1,0;2,0");
        Layout layout2 = Layout.createLayout("1,0;0,0;2,0");

        assertTrue(layout1.isMatch(layout2));
    }

    /**
     * Turn függvény, 90 fok, egyenes vonal.
     */
    @Test
    public void turn90degreeSingleLine() {
        Layout input = Layout.createLayout("0,0;1,0;2,0");
        Layout expected = Layout.createLayout("0,0;0,1;0,2");
        Layout result = input.turn90degrees(1);
        assertTrue(result.isMatch(expected));
    }

    /**
     * Turn függvény, 180 fok, egyenes vonal.
     */
    @Test
    public void turn180degreeSingleLine() {
        Layout input = Layout.createLayout("0,0;1,0;2,0");
        Layout result = input.turn90degrees(2);
        assertTrue(result.isMatch(input));
    }

    /**
     * Turn függvény, 450(360+90) fok, egyenes vonal.
     */
    @Test
    public void turn450degreeSingleLine() {
        Layout input = Layout.createLayout("0,0;1,0;2,0");
        Layout expected = Layout.createLayout("0,0;0,1;0,2");
        Layout result = input.turn90degrees(5);
        assertTrue(result.isMatch(expected));
    }

    /**
     * Turn függvény, 270 fok, lépcsőszerű alak.
     */
    @Test
    public void turn270degreeStairs() {
        Layout input = Layout.createLayout("2,0;2,1;1,2;1,1;0,2");
        Layout expected = Layout.createLayout("0,0;0,1;1,1;1,2;2,2");
        Layout result= input.turn90degrees(3);
        assertTrue(result.isMatch(expected));
    }

    /**
     * Turn függvény, 90 fok, kereszt alak.
     */
    @Test
    public void turn90degreeCross() {
        Layout input = Layout.createLayout("1,0;0,1;1,1;2,1;1,2");
        Layout result = input.turn90degrees(1);
        assertTrue(result.isMatch(input));
    }

    /**
     * Turn függvény, 180 fok, L alak.
     */
    @Test
    public void turn180degreesLShape() {
        Layout input = Layout.createLayout("0,0;1,0;2,0;2,1");
        Layout result = input.turn90degrees(2);
        Layout expected = Layout.createLayout("0,0;0,1;1,1;2,1");
        assertTrue(result.isMatch(expected));
    }

    /**
     * Mirror függvény, L alak.
     */
    @Test
    public void mirroredL() {
        Layout input = Layout.createLayout("0,0;1,0;2,0;2,1");
        Layout result = input.mirror();
        Layout expected = Layout.createLayout("0,0;1,0;2,0;0,1");
        assertTrue(result.isMatch(expected));
    }

    /**
     * Mirror függvény, kereszt alak.
     */
    @Test
    public void mirroredCross() {
        Layout input = Layout.createLayout("1,0;0,1;1,1;2,1;1,2");
        Layout result = input.mirror();
        assertTrue(result.isMatch(input));
    }

    /**
     * Mirror függvény, lépcső alak.
     */
    @Test
    public void mirroredStairs() {
        Layout input = Layout.createLayout("2,0;2,1;1,2;1,1;0,2");
        Layout result = input.mirror();
        Layout expected = Layout.createLayout("0,0;0,1;1,1;1,2;2,2");
        assertTrue(result.isMatch(expected));
    }

    /**
     * Turn függvény, egy darab négyzet.
     */
    @Test
    public void turnedOne() {
        Layout input = Layout.createLayout("1,1");
        Layout result = input.turn90degrees(1);
        Layout expected = Layout.createLayout("0,0");
        assertTrue(result.isMatch(expected));
    }

}
