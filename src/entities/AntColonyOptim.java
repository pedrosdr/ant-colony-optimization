package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AntColonyOptim {
    // fields
    private double alpha = 1.0;
    private double beta = 1.0;
    private double proximityConstant = 3.0;
    private Graph environment;

    // constructors
    public AntColonyOptim(Graph environment) {
        this.environment = environment;
    }

    public AntColonyOptim(Graph environment, double alpha, double beta, double proximityConstant) {
        this.environment = environment;
        this.alpha = alpha;
        this.beta = beta;
        this.proximityConstant = proximityConstant;
    }

    // properties

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getProximityConstant() {
        return proximityConstant;
    }

    public Graph getEnvironment() {
        return environment;
    }

    public void setEnvironment(Graph environment) {
        this.environment = environment;
    }

    public void setProximityConstant(double proximityConstant) {
        this.proximityConstant = proximityConstant;
    }

    // methods
    public double getProximity(Edge edge) {
        return proximityConstant / edge.getDistance();
    }

    public double getDesire(Edge edge) {
        return Math.pow(edge.getPheromones(), alpha) * Math.pow(getProximity(edge), beta);
    }

    public HashMap<Node, Double> getProbabilities(Ant ant) {
        List<Node> availableNodes = environment.getAdjList().get(ant.getNode()).keySet().stream()
                .filter(n -> ant.getNotVisitedNodes().contains(n))
                .toList();

        List<Double> desires = availableNodes.stream()
                .map(n -> environment.getAdjList().get(ant.getNode()).get(n))
                .map(this::getDesire)
                .toList();

        Double totalDesires = desires.stream().reduce(0.0, Double::sum);

        List<Double> probabilities = desires.stream()
                .map(d -> d / totalDesires)
                .toList();

        HashMap<Node, Double> result = new HashMap<>();
        for(int i = 0; i < availableNodes.size(); i++) {
            result.put(availableNodes.get(i), probabilities.get(i));
        }

        return result;
    }

    public Node getNextNode(Ant ant) {
        HashMap<Node, Double> probHashMap = getProbabilities(ant);

        double randomNumber = Math.random();

        List<Node> nodes = probHashMap.keySet().stream().toList();
        List<Double> probs = probHashMap.values().stream().toList();

        Node nextNode = nodes.getFirst();
        double previousVal = 0.0;
        for(int i = 0; i < nodes.size(); i++) {
            if(previousVal < randomNumber && randomNumber <= probs.get(i) + previousVal) {
                nextNode = nodes.get(i);
                break;
            }
            previousVal += probs.get(i);
        }
        return nextNode;
    }

    public void step(Ant ant) {
        ant.move(getNextNode(ant));
    }

    public void cicle(Ant ant) {
        while(!ant.getNotVisitedNodes().isEmpty()) {
            step(ant);
        }
    }
}
