package entities;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    //fields
    private HashMap<Integer, HashMap<Integer, Edge>> adjList = new HashMap<>();

    // constructors
    public Graph() {}

    // methods
    public void addEdge(int node1, int node2) {
        adjList.put(node1, new HashMap<>());
        adjList.put(node2, new HashMap<>());

        Edge edge = new Edge(node1, node2);

        adjList.get(node1).put(node2, edge);
        adjList.get(node2).put(node1, edge);
    }

    public Edge getEdge(int node1, int node2) {
        return adjList.get(node1).get(node2);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjList=" + adjList +
                '}';
    }
}
