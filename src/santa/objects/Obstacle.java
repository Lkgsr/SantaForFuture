package santa.objects;

public class Obstacle extends Point {

    public Obstacle(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return " O ";//"O(X" + this.getX()+"Y"+this.getY()+")";
    }
}
