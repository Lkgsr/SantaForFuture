package santa.objects;

import santa.exceptions.TheMapIsToSmallForAllTheHousesAndObstaclesException;

import java.util.ArrayList;
import java.util.Random;

import static santa.tools.Tools.deepCopy;

public class EarthMap {
    private int width, height;
    private Point[][] map;
    private ArrayList<Point> houses;
    private Random rand = new Random();


    public EarthMap(int width, int height, int houses, int obstacle) throws TheMapIsToSmallForAllTheHousesAndObstaclesException {
        this.houses = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.map = new Point[height][width];
        if (houses + obstacle >= width * height) {
            int houseObstacle = houses + obstacle;
            throw new TheMapIsToSmallForAllTheHousesAndObstaclesException("Houses + Obstacle " + houseObstacle + " >= " + "width*height " + width * height);
        }
        placeHousesOrObstacle(houses, true);
        placeHousesOrObstacle(obstacle, false);
        filNullWithSpace();
    }

    private void placeHousesOrObstacle(int count, boolean type) {
        for (int i = 0; i < count; i++) {
            int y = rand.nextInt(this.height);
            int x = rand.nextInt(this.width);
            while (getMapValue(x, y) != null) {
                y = rand.nextInt(this.height);
                x = rand.nextInt(this.width);
            }
            if (type) {
                House h = new House(x, y);
                setMapValue(x, y, h);
                this.houses.add(h);
            } else {
                setMapValue(x, y, new Obstacle(x, y));
            }
        }
    }

    private void filNullWithSpace() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (getMapValue(x, y) == null) {
                    setMapValue(x, y, new Space(x, y));
                }
            }
        }
    }

    public void setMapValue(int x, int y, Point value) {
        this.map[y][x] = value;
    }

    public Point getMapValue(int x, int y) {
        return this.map[y][x];
    }

    @Override
    public String toString() {
        StringBuilder object = new StringBuilder();
        StringBuilder top_bot = new StringBuilder();
        for (int i = 0; i < this.map[0].length * 3; i++) {
            top_bot.append("-");
        }
        object.append(top_bot);
        object.append("\n");
        for (Point[] row : this.map) {
            object.append("|");
            for (Point column : row) {
                object.append(column.toString());
            }
            object.append("|\n");
        }
        object.append(top_bot);
        return object.toString();
    }

    public String toString(Point[][] map) {
        StringBuilder object = new StringBuilder();
        StringBuilder top_bot = new StringBuilder();
        for (int i = 0; i < map[0].length * 3; i++) {
            top_bot.append("-");
        }
        object.append(top_bot);
        object.append("\n");
        for (Point[] row : map) {
            object.append("|");
            for (Point column : row) {
                object.append(column.toString());
            }
            object.append("|\n");
        }
        object.append(top_bot);
        return object.toString();
    }

    public ArrayList<Point> getHouses() {
        return (ArrayList<Point>) houses.clone();
    }

    public Point[][] getMap() {
        return deepCopy(this.map);
    }
}
