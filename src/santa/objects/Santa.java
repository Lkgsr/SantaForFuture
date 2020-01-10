package santa.objects;

import santa.tools.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

import static santa.tools.AStartPathFinding.AStartSearch;
import static santa.tools.Tools.deepCopy;

public class Santa extends Point {
    private MatrixOperations mtxO = new MatrixOperations();
    private Object[] path;
    private EarthMap map;

    public Santa(int x, int y, EarthMap map) {
        super(x, y);
        this.map = map;
    }

    public void search() {
        this.map.setMapValue(this.getY(), this.getX(), this);
        System.out.println("Cost Matrix: ");
        for (double[] c : costMatrix()) {
            for (double cc : c) {
                System.out.printf("%.2f ", cc);
            }
            System.out.println("\n");
        }
        System.out.println("\nNumber of Houses (and Santa): " + this.map.getHouses().size());
        Route<Point> res = nearestNeighbor(costMatrix());
        System.out.println("Path Cost Nearest Neighbour: " + res.getCost() + "\nPath: " + res.getPath());
        System.out.println(this.map.toString(drawRoute(getCompleteRoute(res.getPath()).getPath(), this.map.getMap())));
        bruteForce(this.map.getHouses(), this);
    }

    private void bruteForce(ArrayList<Point> houses, Point start) {
        houses.remove(0);
        List<List<Point>> permutations = listPermutations(houses);
        double lowestCost = Double.MAX_VALUE;
        Route<Point> best_route = null;
        ArrayList<Point> best_permutation = null;
        for (List<Point> permutation : permutations) {
            permutation.add(0, start);
            Route<Point> r = getCompleteRoute(new ArrayList(permutation));
            if (lowestCost > r.getCost()) {
                lowestCost = r.getCost();
                best_route = r;
                best_permutation = (ArrayList<Point>) permutation;
            }
        }
        assert best_route != null;
        Route<Point> b_route = getCompleteRoute(best_route.getPath());
        Point[][] n_map = drawRoute(b_route.getPath(), this.map.getMap());
        System.out.println("Path Cost Brute force Method: " + b_route.getCost() + "\nPath: " + best_permutation);
        System.out.println(this.map.toString(n_map));
    }

    private List<List<Point>> listPermutations(ArrayList<Point> list) {
        if (list.size() > 10) {
            throw new OutOfMemoryError();
        }
        if (list.size() == 0) {
            List<List<Point>> result = new ArrayList<List<Point>>();
            result.add(new ArrayList<Point>());
            return result;
        }
        List<List<Point>> returnMe = new ArrayList<List<Point>>();
        Point firstElement = list.remove(0);
        List<List<Point>> recursiveReturn = listPermutations(list);
        for (List<Point> li : recursiveReturn) {
            for (int index = 0; index <= li.size(); index++) {
                List<Point> temp = new ArrayList<Point>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }
        }
        return returnMe;
    }

    private Route<Point> getCompleteRoute(ArrayList<Point> wayPoints) {
        ArrayList<Point> complete_route = new ArrayList<>();
        double cost = 0;
        Point start = wayPoints.remove(0);
        for (Point p : wayPoints) {
            Route<Point> pointToPoint = AStartSearch(start, p, this.map.getMap());
            if (pointToPoint != null) {
                complete_route.addAll(pointToPoint.getPath());
                cost += pointToPoint.getCost();
                start = p;
            }
        }
        return new Route<>(complete_route, cost + 1);
    }

    private Point[][] drawRoute(ArrayList<Point> route, Point[][] map) {
        Point[][] mapClone = deepCopy(map);
        for (Point p : route) {
            if (mapClone[p.getY()][p.getX()].getClass() != House.class && mapClone[p.getY()][p.getX()].getClass() != Santa.class && mapClone[p.getY()][p.getX()].getClass() != Path.class) {
                mapClone[p.getY()][p.getX()] = new Path(p);
            } else if (mapClone[p.getY()][p.getX()].getClass() == Path.class) {
                mapClone[p.getY()][p.getX()].step();
            }

        }
        return mapClone;
    }

    private <T extends Point> Route<Point> nearestNeighbor(double[][] costMatrix) {
        ArrayList<Point> houses = this.map.getHouses();
        int house_size = houses.size();
        ArrayList<Point> path = new ArrayList<>();
        path.add(houses.remove(0));
        int index = 0;
        double cost = 0;
        while (path.size() < house_size) {
            costMatrix = mtxO.remove_row(costMatrix, index);
            double[] row = mtxO.T(costMatrix)[index];
            index = indexLowestElement(row);
            assert index != -1;
            if (row[index] == Double.MAX_VALUE) {
                houses.remove(index);
                house_size--;
            } else {
                cost += row[index];
                path.add(houses.remove(index));
            }
            costMatrix = mtxO.T(mtxO.remove_row(mtxO.T(costMatrix), index));
        }
        return new Route<>(path, cost + 1);

    }

    private int indexLowestElement(double[] list) {
        double min_element = Double.MAX_VALUE;
        int min_index = -1;
        for (int index = 0; index < list.length; index++) {
            if (list[index] <= min_element) {
                min_element = list[index];
                min_index = index;
            }
        }

        return min_index;
    }

    private double[][] costMatrix() {
        ArrayList<Point> houses = this.map.getHouses();
        double[][] m = new double[houses.size()][houses.size()];
        for (int start_int = 0; start_int < houses.size(); start_int++) {
            for (int end_int = 0; end_int < houses.size(); end_int++) {
                Point start = houses.get(start_int);
                Point end = houses.get(end_int);
                if (start != end) {
                    Route cost = AStartSearch(start, end, this.map.getMap());
                    if (cost != null) {
                        m[end_int][start_int] = cost.getCost();
                    } else {
                        System.out.println("No Way From: " + start + "X:" + start.getX() + "Y:" + start.getY() + " To: " + end + "X:" + end.getX() + "Y:" + end.getY());
                        m[end_int][start_int] = Double.MAX_VALUE;
                    }
                }
            }
        }
        return m;
    }

    @Override
    public String toString() {
        return " S ";//"S(X" + this.getX()+"Y"+this.getY()+")";
    }
}
