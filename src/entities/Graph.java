package entities;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph implements IDrawable{
    //fields
    private HashMap<Node, HashMap<Node, Edge>> adjList = new HashMap<>();
    private Set<Node> nodes = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    // constructors
    public Graph() {}

    // methods
    public void addEdge(Edge edge) {

        Node node1 = edge.getNodeA();
        Node node2 = edge.getNodeB();

        adjList.put(node1, new HashMap<>());
        adjList.put(node2, new HashMap<>());

        adjList.get(node1).put(node2, edge);
        adjList.get(node2).put(node1, edge);

        nodes.add(node1);
        nodes.add(node2);
        edges.add(edge);
    }

    public void addNode(Node node) {
        nodes.add(node);
        adjList.put(node, new HashMap<>());
    }

    public void connectAll(double pheromones) {
        for(Node node1 : adjList.keySet()) {
            for(Node node2 : adjList.keySet()) {
                if(node1.equals(node2)) continue;

                Edge edge = new Edge(node1, node2, pheromones);
                addEdge(edge);
            }
        }
    }

    public Edge getEdge(Node node1, Node node2) {
        return adjList.get(node1).get(node2);
    }

    // properties

    public HashMap<Node, HashMap<Node, Edge>> getAdjList() {
        return adjList;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    // methods
    @Override
    public String toString() {
        return "Graph{" +
                "adjList=" + adjList +
                '}';
    }

    @Override
    public void draw(Graphics2D gd, Conversor conversor) {
        for(Edge edge : edges) {
            edge.draw(gd, conversor);
        }

        for(Node node : nodes) {
            node.draw(gd, conversor);
        }
    }
}
