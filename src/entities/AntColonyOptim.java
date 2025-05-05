package entities;

import java.util.List;

public class AntColonyOptim {
    // fields
    private double alpha = 1.0;
    private double beta = 1.0;
    private double proximityConstant = 3.0;
    private double pheromoneRate = 3.0;
    private double evaporationRate = 0.1;
    private Graph environment;
    private Runnable runnable;

    // constructors
    public AntColonyOptim(Graph environment) {
        this.environment = environment;
    }

    public AntColonyOptim(
            Graph environment,
            double alpha,
            double beta,
            double proximityConstant,
            double pheromoneRate,
            double evaporationRate
    ) {
        this.environment = environment;
        this.alpha = alpha;
        this.beta = beta;
        this.proximityConstant = proximityConstant;
        this.pheromoneRate = pheromoneRate;
        this.evaporationRate = evaporationRate;
    }

    // properties
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

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
    public void evaporate() {
        environment.getEdges()
                .forEach(e -> e.setPheromones(e.getPheromones() * (1.0 - evaporationRate)));
    }

    public void incrementPheromones(Ant ant) {
        double totalDistance = ant.getPath().getTotalDistance();
        ant.getPath().getEdges()
                .forEach(e -> e.setPheromones(e.getPheromones() + pheromoneRate / totalDistance));
    }

    public double getProximity(Edge edge) {
        return proximityConstant / edge.getDistance();
    }

    public double getDesire(Edge edge) {
        return Math.pow(edge.getPheromones(), alpha) * Math.pow(getProximity(edge), beta);
    }

    public List<Double> getProbabilities(Ant ant) {
        List<Node> availableNodes = ant.getAvailableNodes();

        List<Double> desires = availableNodes.stream()
                .map(n -> environment.getAdjList().get(ant.getNode()).get(n))
                .map(this::getDesire)
                .toList();

        Double totalDesires = desires.stream().reduce(0.0, Double::sum);

        return desires.stream()
                .map(d -> d / totalDesires)
                .toList();
    }

    public Node getNextNode(Ant ant) {
        double randomNumber = Math.random();

        List<Node> nodes = ant.getAvailableNodes();
        List<Double> probs = getProbabilities(ant);

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

    public void cycle(Ant ant) {
        while(!ant.getNotVisitedNodes().isEmpty()) {
            step(ant);
        }

        evaporate();
        incrementPheromones(ant);

        if(runnable != null)
            runnable.run();
    }
}
