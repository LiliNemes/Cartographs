package Engine.Builder;

import Engine.Model.Board;
import Engine.Model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Játékpálya generálások osztálya.
 */
public class BoardDeck {
    /**
     * Alapértelmezett, vagyis rift nélküli játékpálya létrehozása. random false értéke esetén fix helyekre helyezi le
     * a romokat illetve a hegyeket, true érték esetén random koordinátákat generál (hegyeknél úgy, hogy hegy mező ne
     * lehessen a pálya szélén), megnézi, hogy ezekre a koordinátákra korábban helyezette-e le romot vagy hegyet,
     * hegyek esetén ellenőrzi, hogy a most lerakandó hegy nem egy korábban már lerakott hegy szomszédja lenne-e, majd ha
     * minden ellenőrzésen átment 'lehelyezi' a hegy vagy rom mezőt. Ezt addig csinálja, míg 6db rom és 5db hegy nincs
     * lerakva.
     * @param random Be van-e kapcsolva a játéknak az a funkciója, hogy a rom illetve hegy tile-okat random helyezi el.
     * @return A generált játékpálya.
     */
    public static Board createBoard(boolean random) {
        int n = 11;


        // generate mountains and ruins
        List<Coordinate> mounts = new ArrayList<>();
        List<Coordinate> ruins = new ArrayList<>();

        if(random){
            Random rnd = new Random();

            //num of mountains
            int m = 5;
            //num of ruins
            int r = 6;
            boolean done = false;

            while(!done) {
                int x = rnd.nextInt(1, n-1);
                int y = rnd.nextInt(1, n-1);
                Coordinate c1 = new Coordinate(x, y);

                if (!mounts.contains(c1) && !ruins.contains(c1) && m > 0) {
                    boolean notgood = false;

                    Coordinate c = new Coordinate(c1.getX() - 1, c1.getY() - 1);
                    if (mounts.contains(c))
                        notgood = true;

                    c = new Coordinate(c1.getX() + 1, c1.getY() + 1);
                    if (mounts.contains(c))
                        notgood = true;

                    c = new Coordinate(c1.getX() + 1, c1.getY() - 1);
                    if (mounts.contains(c))
                        notgood = true;

                    c = new Coordinate(c1.getX() - 1, c1.getY() + 1);
                    if (mounts.contains(c))
                        notgood = true;

                    if(!notgood){
                        mounts.add(c1);
                        m--;
                    }
                }

                int z = rnd.nextInt(0, n );
                int w = rnd.nextInt(0, n);
                Coordinate c2 = new Coordinate(z, w);

                if (!mounts.contains(c2) && !ruins.contains(c2) && r > 0) {
                    ruins.add(c2);
                    r--;
                }

                if (r == 0 && m == 0){
                    done = true;
                }
            }
        }
        else{
            mounts = Coordinate.parseList("3,1;8,2;5,5;2,8;7,9");
            ruins = Coordinate.parseList("1,2;5,1;9,2;1,8;5,9;9,8");
        }
        return new Board(n, mounts, null, ruins);
    }

    /**
     * Nehéz pályát (amiben vannak szakadékok) generál.
     * @param random Be van-e kapcsolva a random lehelyezés funkció, ennek működését az osztály másik függvényénél lásd részletesen.
     * @return generált pálya
     */
    public static Board createHardBoard(boolean random) {
        int n = 11;


        // generate mountains and ruins
        List<Coordinate> mounts = new ArrayList<>();
        List<Coordinate> rifts = new ArrayList<>();
        List<Coordinate> ruins = new ArrayList<>();

        if(random){
            Random rnd = new Random();

            //num of mountains
            int m = 5;
            //num of rifts
            int ri = 7;
            //num of ruins
            int ru = 6;
            boolean done = false;

            while(!done) {
                int x = rnd.nextInt(1, n - 1);
                int y = rnd.nextInt(1, n - 1);
                Coordinate c1 = new Coordinate(x, y);

                if (!mounts.contains(c1) && !ruins.contains(c1) && !rifts.contains(c1) && m > 0) {
                    boolean notgood = false;

                    Coordinate c = new Coordinate(c1.getX() - 1, c1.getY() - 1);
                    if (mounts.contains(c))
                        notgood = true;

                    c = new Coordinate(c1.getX() + 1, c1.getY()+1);
                    if (mounts.contains(c))
                        notgood = true;

                    c = new Coordinate(c1.getX()+1, c1.getY() - 1);
                    if (mounts.contains(c))
                        notgood = true;

                    c = new Coordinate(c1.getX()-1, c1.getY() + 1);
                    if (mounts.contains(c))
                        notgood = true;

                    if(!notgood){
                        mounts.add(c1);
                        m--;
                    }
                }

                int z = rnd.nextInt(0, n);
                int w = rnd.nextInt(0, n);
                Coordinate c2 = new Coordinate(z, w);

                if (!mounts.contains(c2) && !ruins.contains(c2) && !rifts.contains(c2) && ri > 0) {
                    rifts.add(c2);
                    ri--;
                }

                int a = rnd.nextInt(0, n );
                int b = rnd.nextInt(0, n);
                Coordinate c3 = new Coordinate(a, b);

                if (!mounts.contains(c3) && !ruins.contains(c3) && !rifts.contains(c3) && ru > 0) {
                    ruins.add(c3);
                    ru--;
                }

                if (ri == 0 && ru == 0 && m == 0){
                    done = true;
                }
            }
        }
        else {

            mounts = Coordinate.parseList("3,2;8,1;5,7;2,9;9,8");
            rifts = Coordinate.parseList("5,3;4,4;5,4;4,5;5,5;6,5;5,6");
            ruins = Coordinate.parseList("2,2;6,1;6,4;1,6;8,7;3,9");
        }
        return new Board(n, mounts, rifts, ruins);
    }
}
