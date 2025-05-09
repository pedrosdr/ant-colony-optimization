package entities;

public class DoubleBridge extends MaxMinACO {
    // fields
    private Node startNode;
    private Node endNode;
    private BridgeType type;

    // constructors
    public DoubleBridge(BridgeType type) {
        this.type = type;
        initiateEnvironment();
    }

    // properties
    public BridgeType getType() {
        return type;
    }

    public void setType(BridgeType type) {
        this.type = type;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    // methods
    public void initiateEnvironment() {
        environment = new Graph();

        Node[] nodes = new Node[] {
                new Node(0, 40.0, 300.0),
                new Node(1, 373.0, 300.0),
                new Node(2, 540.0, 200.0),
                new Node(3, 540.0, 400.0),
                new Node(4, 707.0, 300.0),
                new Node(5, 1040.0, 300.0)
        };

        if(type == BridgeType.DIFFERENT_LENGTHS)
            nodes[3].setY(550.0);

        for(Node node : nodes) {
            environment.addNode(node);
        }

        environment.addEdge(new Edge(nodes[0], nodes[1], 0.2));
        environment.addEdge(new Edge(nodes[1], nodes[2], 0.2));
        environment.addEdge(new Edge(nodes[1], nodes[3], 0.2));
        environment.addEdge(new Edge(nodes[2], nodes[4], 0.2));
        environment.addEdge(new Edge(nodes[3], nodes[4], 0.2));
        environment.addEdge(new Edge(nodes[4], nodes[5], 0.2));

        startNode = nodes[0];
        endNode = nodes[5];
    }

    @Override
    public void cycle() {
        Ant[] ants = new Ant[nAnts];
        for(int i = 0; i < nAnts; i++) {
            ants[i] = new Ant(startNode, environment);
        }

        Ant bestAnt = ants[0];
        for(int i = 0; i < nAnts; i++) {
            Ant ant = ants[i];
            while(!ant.getNode().equals(endNode)) {
                if(ant.getAvailableNodes().isEmpty()) {
                    ant.setPath(environment);
                    break;
                }
                step(ant);
            }

            if(ant.getPath().getTotalDistance() < bestAnt.getPath().getTotalDistance()) {
                bestAnt = ant;
            }
        }

        evaporate();
        incrementPheromones(bestAnt);
    }
}
