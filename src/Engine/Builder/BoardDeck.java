package Engine.Builder;

import Engine.Model.Board;
import Engine.Model.Coordinate;

public class BoardDeck {

    //TODO hegyek egymástól min.2 távolságra
    public static Board StandardBoard = new Board(11, Coordinate.parseList("3,1;8,2;5,5;2,8;7,9"), null);
}
