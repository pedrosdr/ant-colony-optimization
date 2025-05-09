package entities;

import java.util.List;
import java.util.Random;

public class MaxMinACO extends ACO{
    // fields
    private double maxPheromones = 1.0;
    private double minPheromones = 0.1;

    // constructors
    public MaxMinACO() {}

    public MaxMinACO(Graph environment) {
        super(environment);
    }

    public MaxMinACO(
            Graph environment,
            int nAnts,
            double alpha,
            double beta,
            double proximityConstant,
            double evaporationRate,
            double minPheromones,
            double maxPheromones
    ) {
        super(environment, nAnts, alpha, beta, proximityConstant, evaporationRate);
        this.minPheromones = minPheromones;
        this.maxPheromones = maxPheromones;
    }

    // properties
    public double getMaxPheromones() {
        return maxPheromones;
    }

    public void setMaxPheromones(double maxPheromones) {
        this.maxPheromones = maxPheromones;
    }

    public double getMinPheromones() {
        return minPheromones;
    }

    public void setMinPheromones(double minPheromones) {
        this.minPheromones = minPheromones;
    }

    // methods
    @Override
    public void evaporate() {
        environment.getEdges()
                .forEach(e -> {
                    double newPheromones = e.getPheromones() * (1-evaporationRate);
                    if(newPheromones > maxPheromones)
                        e.setPheromones(maxPheromones);
                    else if(newPheromones < minPheromones)
                        e.setPheromones(minPheromones);
                    else
                        e.setPheromones(newPheromones);
                });
    }

    @Override
    public void incrementPheromones(Ant ant) {
        double totalDistance = ant.getPath().getTotalDistance();
        ant.getPath().getEdges()
                .forEach(e -> {
                    double newPheromones = e.getPheromones() + (deltaConstant / totalDistance);
                    if(newPheromones > maxPheromones)
                        e.setPheromones(maxPheromones);
                    else if(newPheromones < minPheromones)
                        e.setPheromones(minPheromones);
                    else
                        e.setPheromones(newPheromones);
                });
    }

    @Override
    public void cycle() {
        Ant[] ants = new Ant[nAnts];
        for(int i = 0; i < nAnts; i++) {
            Random rand = new Random();
            int randNum = rand.nextInt(environment.getNodes().size());
            ants[i] = new Ant(environment.getNodes().get(randNum), environment);
        }

        Ant bestAnt = ants[0];
        for(int i = 0; i < nAnts; i++) {
            Ant ant = ants[i];
            while(!ant.getNotVisitedNodes().isEmpty()) {
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
