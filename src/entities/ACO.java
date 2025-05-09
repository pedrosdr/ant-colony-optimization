package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ACO {
    // fields
    protected double alpha = 1.0;
    protected double beta = 1.0;
    protected double deltaConstant = 10.0;
    protected double evaporationRate = 0.1;
    protected int nAnts = 10;
    protected Graph environment;

    // constructors
    public ACO() {}

    public ACO(Graph environment) {
        this.environment = environment;
    }

    public ACO(
            Graph environment,
            int nAnts,
            double alpha,
            double beta,
            double proximityConstant,
            double evaporationRate
    ) {
        this.nAnts = nAnts;
        this.environment = environment;
        this.alpha = alpha;
        this.beta = beta;
        this.deltaConstant = proximityConstant;
        this.evaporationRate = evaporationRate;
    }

    // properties
    public double getEvaporationRate() {
        return evaporationRate;
    }

    public void setEvaporationRate(double evaporationRate) {
        this.evaporationRate = evaporationRate;
    }

    public int getNAnts() {
        return nAnts;
    }

    public void setNAnts(int nAnts) {
        this.nAnts = nAnts;
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

    public double getDeltaConstant() {
        return deltaConstant;
    }

    public Graph getEnvironment() {
        return environment;
    }

    public void setEnvironment(Graph environment) {
        this.environment = environment;
    }

    public void setDeltaConstant(double deltaConstant) {
        this.deltaConstant = deltaConstant;
    }

    // methods
    public void evaporate() {
        environment.getEdges()
                .forEach(e -> {
                    double newPheromones = e.getPheromones() * (1-evaporationRate);
                    e.setPheromones(newPheromones);
                });
    }

    public void incrementPheromones(Ant ant) {
        double totalDistance = ant.getPath().getTotalDistance();
        ant.getPath().getEdges()
                .forEach(e -> {
                    double newPheromones = e.getPheromones() + (deltaConstant / totalDistance);
                    e.setPheromones(newPheromones);
                });
    }

    public double getProximity(Edge edge) {
        return 1.0 / edge.getDistance();
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

    public void cycle() {
        Ant[] ants = new Ant[nAnts];
        for(int i = 0; i < nAnts; i++) {
            Random rand = new Random();
            int randNum = rand.nextInt(environment.getNodes().size());
            ants[i] = new Ant(environment.getNodes().get(randNum), environment);
        }

        for(int i = 0; i < nAnts; i++) {
            Ant ant = ants[i];
            while(!ant.getNotVisitedNodes().isEmpty()) {
                step(ant);
            }
        }

        evaporate();

        for(int i = 0; i < nAnts; i++) {
            incrementPheromones(ants[i]);
        }
    }
}
