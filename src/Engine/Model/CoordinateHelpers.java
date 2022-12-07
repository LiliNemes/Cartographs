package Engine.Model;

import java.util.List;

public class CoordinateHelpers {

    /**
     * Megnézi, hogy a két, paraméterként kapott koordinátákból űlló lista elemei közt van-e egyezés.
     * @param coordinates1 Lista 1.
     * @param coordinates2 Lista 2.
     * @return true ha van egyezés, false ha nincs.
     */
    public static boolean hasIntersection(List<Coordinate> coordinates1, List<Coordinate> coordinates2) {
        if (coordinates1 == null || coordinates2 == null) return false;

        for (Coordinate coordinate1 : coordinates1) {
            for (Coordinate coordinate2 : coordinates2) {
                if (coordinate1.equals(coordinate2)) return true;
            }
        }

        return false;
    }
}
