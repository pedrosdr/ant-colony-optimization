package entities;

public class Conversor {
    // fields
    private int height;

    // constructors
    public Conversor(int height) {
        this.height = height;
    }

    // properties


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // methods
    public Position convert(int x, int y) {
        y = height - y;
        return new Position(x, y);
    }
}
