package santa.tools;

import santa.objects.Obstacle;
import santa.objects.Point;
import santa.objects.Route;

import java.util.*;

import static java.lang.Double.min;
import static java.lang.Math.*;
import static java.lang.Math.pow;

public class AStartPathFinding {

    private static <T extends Point> double heuristic(T start, T end) {
        int d_1 = 1, d_2 = 1;
        double dx = abs(start.getY() - end.getY());
        double dy = abs(start.getX() - end.getX());
        return d_1 * (dx + dy) + (d_2 - 2 * d_1) * min(dx, dy);
    }

    private static <T extends Point> ArrayList<T> get_vertex_neighbours(T pos, T[][] graph) {
        ArrayList<T> n = new ArrayList<>();
        for (int[] dx_dy : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
            int y = pos.getY() + dx_dy[1], x = pos.getX() + dx_dy[0];
            if (x < 0 || x > graph[0].length - 1 || y < 0 || y > graph.length - 1) {
                continue;
            }
            n.add(graph[y][x]);
        }
        return n;
    }

    private static <T extends Point> double move_cost(T a, T b) {
        return sqrt(pow(b.getY() - a.getY(), 2) + pow(b.getX() - a.getX(), 2));
    }

    public static <T extends Point> Route AStartSearch(T start, T end, T[][] graph) {


        HashMap<T, Double> g_actual_cost = new HashMap<T, Double>();
        g_actual_cost.put(start, 0.0);
        HashMap<T, Double> f_estimated_cost = new HashMap<T, Double>();
        f_estimated_cost.put(start, heuristic(start, end));
        List<T> open_vertices = new ArrayList<>();
        open_vertices.add(start);
        List<T> closed_vertices = new ArrayList<>();
        HashMap<T, T> came_from = new HashMap<>();


        while (open_vertices.size() > 0) {

            T current = null;
            double current_f_score = 0;

            for (T open_vertex : open_vertices) {
                if (current == null || f_estimated_cost.get(open_vertex) < current_f_score) {
                    current_f_score = f_estimated_cost.get(open_vertex);
                    current = open_vertex;
                }
            }

            open_vertices.remove(current);
            closed_vertices.add(current);

            assert current != null;
            if (current.equals(end)) {
                ArrayList<T> path = new ArrayList<>();
                path.add(current);
                while (came_from.containsKey(current)) {
                    current = came_from.get(current);
                    path.add(current);
                }
                Collections.reverse(path);
                return new Route(path, f_estimated_cost.get(end));
            }

            for (T neighbour : get_vertex_neighbours(current, graph)) {
                if (closed_vertices.contains(neighbour)) {
                    continue;
                }
                if (neighbour.getClass() != Obstacle.class) {
                    double move_cost = move_cost(current, neighbour);
                    double candidate_g = g_actual_cost.get(current) + move_cost;
                    if (!open_vertices.contains(neighbour)) {
                        open_vertices.add(neighbour);
                    } else if (candidate_g >= g_actual_cost.get(neighbour)) {
                        continue;
                    }
                    came_from.put(neighbour, current);
                    g_actual_cost.put(neighbour, candidate_g);
                    f_estimated_cost.put(neighbour, g_actual_cost.get(neighbour) + heuristic(neighbour, end));

                }
            }
        }

        return null;
    }

}
