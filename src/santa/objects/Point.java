package santa.objects;

public class Point {
    private int x, y;
    private int step = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
        this.step = p.step;
    }

    public int getStep() {
        return step;
    }

    public void step() {
        step++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
