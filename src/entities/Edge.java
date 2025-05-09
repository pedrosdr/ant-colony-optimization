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
    public void draw(Graphics2D gd) {
        float lineWidth = (float) ((pheromones-0.1)*(13.0-3.0)/(1.0-0.1)+3.0);
        gd.setStroke(new BasicStroke(lineWidth));

        int colorAlpha = (int) ((pheromones-0.1)*(255.0-50.0)/(1.0-0.1)+50.0);
        colorAlpha = Math.min(colorAlpha, 255);
        gd.setColor(new Color(58, 68, 242, colorAlpha));
        gd.drawLine((int)nodeA.getX(), (int)nodeA.getY(), (int)nodeB.getX(), (int)nodeB.getY());
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
