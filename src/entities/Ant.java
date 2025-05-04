package entities;

import java.util.HashSet;
import java.util.Set;

public class Ant {
    // fields
    private Node node;
    private final Graph path = new Graph();
    private Graph environment;
    private final Set<Node> notVisitedNodes = new HashSet<>();

    // constructors
    public Ant(Node node, Graph environment) {
        this.environment = environment;
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
    public void move(Node node) {
        if(!notVisitedNodes.contains(node)) return;
        path.addEdge(
                environment.getEdge(this.node, node)
        );
        this.node = node;
        notVisitedNodes.remove(node);
    }

    @Override
    public String toString() {
        return "Ant{" +
                "node=" + node +
                ", notVisitedNodes=" + notVisitedNodes +
                '}';
    }
}
