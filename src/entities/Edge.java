package entities;

import java.awt.*;
import java.util.Objects;

public class Edge implements IDrawable {
    // fields
    private int nodeA;
    private int nodeB;
    private double pheromones;
    private double distance;

    // constructors
    public Edge() {}

    public Edge(int node1, int node2) {
        this.nodeA = node1;
        this.nodeB = node2;
    }

    public Edge(int node1, int node2, double pheromones, double distance) {
        this.nodeA = node1;
        this.nodeB = node2;
        this.pheromones = pheromones;
        this.distance = distance;
    }

    // properties


    public int getNodeA() {
        return nodeA;
    }

    public void setNodeA(int nodeA) {
        this.nodeA = nodeA;
    }

    public int getNodeB() {
        return nodeB;
    }

    public void setNodeB(int nodeB) {
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
        Position pos_start = conversor.convert(0, 0);
        Position pos_end = conversor.convert((int)distance, (int)distance);
        gd.drawLine(pos_start.getX(), pos_start.getY(), pos_end.getX(), pos_end.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return nodeA == edge.nodeA && nodeB == edge.nodeB;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB);
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
