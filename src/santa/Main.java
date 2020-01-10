package santa;

import santa.exceptions.TheMapIsToSmallForAllTheHousesAndObstaclesException;
import santa.objects.EarthMap;
import santa.objects.Santa;

public class Main {
    public static void main(String[] args) throws TheMapIsToSmallForAllTheHousesAndObstaclesException {
        EarthMap map = new EarthMap(6, 6, 8, 10);
        Santa santa = new Santa(0, 0, map);
        santa.search();
    }
}
