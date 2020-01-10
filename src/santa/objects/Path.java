package santa.objects;

public class Path extends Point {


    public Path(int x, int y) {
        super(x, y);
    }

    public Path(Point p) {
        super(p);
    }

    @Override
    public String toString() {
        if (getStep() == 0) {
            return " * ";
        } else if (getStep() == 1) {
            return " + ";
        } else if (getStep() == 2) {
            return " ~ ";
        }
        return " # ";//"P(X" + this.getX()+"Y"+this.getY()+")";
    }

}
