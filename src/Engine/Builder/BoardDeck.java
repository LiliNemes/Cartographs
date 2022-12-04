package Engine.Builder;

import Engine.Model.Board;
import Engine.Model.Coordinate;

/**
 * Pálya generálások osztálya
 */
public class BoardDeck {
    /**
     * Normál pályát generál
     * @return generált pálya
     */
    public static Board createBoard() {
        return new Board(11, Coordinate.parseList("3,1;8,2;5,5;2,8;7,9"), null, Coordinate.parseList("1,2;5,1;9,2;1,8;5,9;9,8"));
    }

    /**
     * Nehéz pályát generál
     * @return generált pálya
     */
    public static Board createHardBoard() {
        return new Board(11, Coordinate.parseList("3,2;8,1;5,7;2,9;9,8"), Coordinate.parseList("5,3;4,4;5,4;4,5;5,5;6,5;5,6"), Coordinate.parseList("2,2;6,1;6,4;1,6;8,7;3,9"));
    }
}
