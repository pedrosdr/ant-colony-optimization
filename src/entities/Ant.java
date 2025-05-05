package entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ant {
    // fields
    private Node node;
    private Node initialNode;
    private final Graph path = new Graph();
    private Graph environment;
    private final Set<Node> notVisitedNodes = new HashSet<>();

    // constructors
    public Ant(Node node, Graph environment) {
        this.environment = environment;
        this.initialNode = node;
        this.node = node;

        for(Node n : environment.getNodes()) {
            if(!n.equals(node))
                notVisitedNodes.add(n);
        }

        path.addNode(node);
    }

    // properties

    public Graph getEnvironment() {
        return environment;
    }

    public void setEnvironment(Graph environment) {
        this.environment = environment;
    }

    public Node getNode() {
        return node;
    }

    public Graph getPath() {
        return path;
    }

    public Set<Node> getNotVisitedNodes() {
        return notVisitedNodes;
    }

    // methods
    public List<Node> getAvailableNodes() {
        return environment.getAdjList().get(node).keySet().stream()
                .filter(notVisitedNodes::contains)
                .toList();
    }

    public void move(Node node) {
        if(!notVisitedNodes.contains(node)) return;
        path.addEdge(
                environment.getEdge(this.node, node)
        );
        this.node = node;
        notVisitedNodes.remove(node);

        if(notVisitedNodes.isEmpty()) {
            path.addEdge(environment.getEdge(initialNode, node));
        }
    }

    @Override
    public String toString() {
        return "Ant{" +
                "node=" + node +
                ", notVisitedNodes=" + notVisitedNodes +
                '}';
    }
}
