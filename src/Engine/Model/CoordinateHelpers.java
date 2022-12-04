package Engine.Model;

import java.util.List;

public class CoordinateHelpers {

    public static boolean hasIntersection(List<Coordinate> coordinates1, List<Coordinate> coordinates2) {
        if (coordinates1 == null || coordinates2 == null) return false;

        for (int i=0; i<coordinates1.size(); i++) {
            var coordinate1 = coordinates1.get(i);
            for (int j=0; j<coordinates2.size(); j++) {
                var coordinate2 = coordinates2.get(j);
                if (coordinate1.isEqualTo(coordinate2)) return true;
            }
        }

        return false;
    }
}
