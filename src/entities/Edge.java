package entities;

import java.awt.*;
import java.util.Objects;

public class Edge implements IDrawable {
    // fields
    private Node nodeA;
    private Node nodeB;
    private double pheromones;
    private double distance;

    // constructors
    public Edge() {}

    public Edge(Node node1, Node node2) {
        System.out.println(node1.getX());
        this.nodeA = node1;
        this.nodeB = node2;
        this.distance = Math.sqrt(
                Math.pow(nodeA.getX() - nodeB.getX(), 2.0) + Math.pow(nodeA.getY() - nodeB.getY(), 2.0)
        );
    }

    public Edge(Node node1, Node node2, double pheromones) {
        this.nodeA = node1;
        this.nodeB = node2;
        this.pheromones = pheromones;
        this.distance = Math.sqrt(
                Math.pow(nodeA.getX() - nodeB.getX(), 2.0) + Math.pow(nodeA.getY() - nodeB.getY(), 2.0)
        );
    }

    // properties


    public Node getNodeA() {
        return nodeA;
    }

    public void setNodeA(Node nodeA) {
        this.nodeA = nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public void setNodeB(Node nodeB) {
        this.nodeB = nodeB;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPheromones() {
        return pheromones;
    }

    public void setPheromones(double pheromones) {
        this.pheromones = pheromones;
    }

    // methods
    public void draw(Graphics2D gd, Conversor conversor) {
        Position pos_start = conversor.convert((int)nodeA.getX(), (int)nodeA.getY());
        Position pos_end = conversor.convert((int)nodeB.getX(), (int)nodeB.getY());

        float lineWidth = pheromones < 1.0? 3.0f : 1.0f + (float)Math.log(2.0 * pheromones);
        gd.setStroke(new BasicStroke(lineWidth));

        int colorAlpha = (int)(1 + 0.2 * pheromones);
        colorAlpha = Math.min(colorAlpha, 255);
        gd.setColor(new Color(255, 0, 0, colorAlpha));
        gd.drawLine(pos_start.getX(), pos_start.getY(), pos_end.getX(), pos_end.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (nodeA.equals(edge.nodeA) && nodeB.equals(edge.nodeB)) ||
                (nodeA.equals(edge.nodeB) && nodeB.equals(edge.nodeA));
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB) + Objects.hash(nodeB, nodeA);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node1=" + nodeA +
                ", node2=" + nodeB +
                ", pheromones=" + pheromones +
                ", distance=" + distance +
                '}';
    }
}
