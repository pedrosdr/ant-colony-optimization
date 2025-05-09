package entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph implements IDrawable{
    //fields
    private final HashMap<Node, HashMap<Node, Edge>> adjList = new HashMap<>();
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    // constructors
    public Graph() {}

    // methods
    public void addEdge(Edge edge) {
        Node node1 = edge.getNodeA();
        Node node2 = edge.getNodeB();

        addNode(node1);
        addNode(node2);

        if(!adjList.get(node1).containsKey(node2))
            adjList.get(node1).put(node2, edge);

        if(!adjList.get(node2).containsKey(node1))
            adjList.get(node2).put(node1, edge);

        if(!edges.contains(edge))
            edges.add(edge);
    }

    public void addNode(Node node) {
        if(!nodes.contains(node))
            nodes.add(node);

        if(!adjList.containsKey(node))
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

    public Node getNode(int key) {
        return nodes.stream().filter(n -> n.getKey() == key).toList().getFirst();
    }

    // properties

    public HashMap<Node, HashMap<Node, Edge>> getAdjList() {
        return adjList;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public double getTotalDistance() {
        return edges.stream()
                .map(Edge::getDistance)
                .reduce(0.0, Double::sum);
    }

    public void clear() {
        adjList.clear();
        nodes.clear();
        edges.clear();
    }

    public void reset() {
        for(Edge edge : edges) {
            edge.setPheromones(0.1);
        }
    }

    // methods
    @Override
    public String toString() {
        return "Graph{" +
                "adjList=" + adjList +
                '}';
    }

    @Override
    public void draw(Graphics2D gd) {
        for(Edge edge : edges) {
            edge.draw(gd);
        }

        for(Node node : nodes) {
            node.draw(gd);
        }
    }
}
