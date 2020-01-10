package santa.objects;

public class House extends Point{
    public House(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return " H ";//"H(X" + this.getX()+"Y"+this.getY()+")";
    }
}
