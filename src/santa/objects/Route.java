package santa.objects;

import java.util.ArrayList;

public class Route <T extends Point>{
    private  ArrayList<T> path;
    private double cost;
    public Route( ArrayList<T> path, Double cost) {
        this.cost = cost;
        this.path = path;
    }

    public  ArrayList<T> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }
}
