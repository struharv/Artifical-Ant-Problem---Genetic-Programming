package aant.engine;

import java.util.Random;

public class Evolution {

    int POPULATION_SIZE = 7500;
    int max_steps = 500;

    int MAX_TREE_DEPTH = 12;
    int MAX_DEPTH_TO_CREATE = 4;
    int MIN_DEPTH_TO_CREATE = 2;
    int MIN_NODES = 2;
    int MAX_NODES = 35;
    double P_SELECTION = 0.95;
    double P_MUTATION = 0.045;
    int SELECTION_ATTEMPTS = 5;

    Score[] pool;
    Map data;
    Random random;
    Score best;
    GeneticProgramming geneticProgramming;
    TreeUtils treeUtils;

    public Evolution(Map data) {
        this.data = data;
        this.random = new Random();
        this.geneticProgramming = new GeneticProgramming();
        this.treeUtils = new TreeUtils();
    }

    public void createPopulation() {
        pool = new Score[POPULATION_SIZE];
        RandomTreeCreator creator = new RandomTreeCreator();
        double bestFit = -Double.MAX_VALUE;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Node individual = creator.create(random.nextInt(MAX_DEPTH_TO_CREATE - MIN_DEPTH_TO_CREATE)
                            + MIN_DEPTH_TO_CREATE,
                    random.nextInt(MAX_NODES - MIN_NODES) + MIN_NODES);
            double fit = fitness(individual);
            pool[i] = new Score(individual, fit);
            if (fit > bestFit) {
                best = pool[i];
                bestFit = fit;
            }
        }
    }

    public void evoluce(int steps) {
        for (int generation = 0; generation < steps; generation++) {
            nextGeneration();
        }
    }

    public void nextGeneration() {
        Score[] newPool = new Score[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE / 2; i++) {
            int sel1 = selectionTournament(SELECTION_ATTEMPTS);
            int sel2;
            while ((sel2 = selectionTournament(SELECTION_ATTEMPTS)) == sel1) ;

            geneticProgramming.crossover(new CrossoverRoot(pool[sel1].node), new CrossoverRoot(pool[sel2].node),
                    geneticProgramming.randomSelection(pool[sel1].node), geneticProgramming.randomSelection(pool[sel2].node));

            double fit;
            Node result = geneticProgramming.getResultingIndividual_1();
            if (random.nextDouble() < P_MUTATION) {
                result = geneticProgramming.mutuj(result);
            }
            fit = fitness(result);
            newPool[2 * i] = new Score(result, fit);
            if (fit > best.fitness) {
                best = newPool[2 * i];
            }

            result = geneticProgramming.getResultingIndividual_2();
            if (random.nextDouble() < P_MUTATION) {
                result = geneticProgramming.mutuj(result);
            }
            fit = fitness(result);
            newPool[2 * i + 1] = new Score(result, fit);
            if (fit > best.fitness) {
                best = newPool[2 * i + 1];
            }
        }
        pool = newPool;
    }

    public double fitness(Node node) {
        AntInterpreter antInterpreter = new AntInterpreter(new Map(data));
        antInterpreter.run(node, max_steps);

        return antInterpreter.eaten - (double) treeUtils.nodeCount(node) / 10 - (double) treeUtils.depth(node) / 20;
    }

    public Score getBest() {
        return best;
    }


    public int selectionTournament(int attempts) {
        int max_pos = random.nextInt(POPULATION_SIZE);
        double max_val = pool[max_pos].fitness;

        for (int i = 0; i < attempts; i++) {
            int rand_pos = random.nextInt(POPULATION_SIZE);
            double rand_val = pool[rand_pos].fitness;

            if (rand_val > max_val && random.nextDouble() < P_SELECTION) {
                max_val = rand_val;
                max_pos = rand_pos;
            }
        }

        return max_pos;
    }

    public void setSteps(int steps) {
        this.max_steps = steps;
    }

}
