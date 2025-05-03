package entities;

import java.awt.*;
import java.util.Objects;

public class Node implements IDrawable {
    // fields
    private int key;
    private double x;
    private double y;

    // constructors
    public Node(int key) {
        this.key = key;
    }

    public Node(int key, double x, double y) {
        this.key = key;
        this.x = x;
        this.y = y;
    }

    // properties

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // methods
    @Override
    public void draw(Graphics2D gd, Conversor conversor) {
        Position position = conversor.convert((int)x, (int)y);
        gd.setStroke(new BasicStroke(2.0f));
        gd.setColor(Color.black);
        gd.drawOval(position.getX()-7, position.getY()-7, 14, 14);
        gd.setColor(Color.blue);
        gd.fillOval(position.getX()-7, position.getY()-7, 14, 14);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return key == node.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return Integer.toString(key);
    }
}
