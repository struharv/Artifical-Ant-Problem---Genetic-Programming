
package aant.engine;

import java.util.ArrayList;
import java.util.Random;


public class GeneticProgramming {

    static final int MAX_TREE_DEPTH = 10;

    Node resultingIndividual_1;
    Node resultingIndividual_2;

    TreeUtils treeUtils;

    ArrayList<Node> node_list;
    Random random;
    RandomTreeCreator treeCreator;

    public GeneticProgramming() {
        this.treeUtils = new TreeUtils();
        this.random = new Random();
        this.treeCreator = new RandomTreeCreator();
    }


    public void crossover(CrossoverRoot crossoverRoot1, CrossoverRoot crossoverRoot2, Node point1, Node point2) {
        Node croot1 = treeUtils.copy(crossoverRoot1.root);
        Node croot2 = treeUtils.copy(crossoverRoot2.root);

        Node cpoint1 = treeUtils.copy(point1);
        Node cpoint2 = treeUtils.copy(point2);

        Node found1 = treeUtils.find(point1.getId(), croot1);
        Node found2 = treeUtils.find(point2.getId(), croot2);

        if (found2.getParent() == null) {
            resultingIndividual_2 = cpoint1;
        } else {
            found2.exchange(found2, cpoint1);
            resultingIndividual_2 = croot2;
        }

        if (found1.getParent() == null) {
            resultingIndividual_1 = cpoint2;
        } else {
            found1.exchange(found1, cpoint2);
            resultingIndividual_1 = croot1;
        }

        treeUtils.adjust(resultingIndividual_2);
        treeUtils.adjust(resultingIndividual_1);

        if (treeUtils.depth(resultingIndividual_1) > MAX_TREE_DEPTH) {
            if (random.nextBoolean()) {
                resultingIndividual_1 = treeUtils.copy(crossoverRoot1.root);
            } else {
                resultingIndividual_1 = treeUtils.copy(crossoverRoot2.root);
            }
        }


        if (treeUtils.depth(resultingIndividual_2) > MAX_TREE_DEPTH) {
            if (random.nextBoolean()) {
                resultingIndividual_2 = treeUtils.copy(crossoverRoot1.root);
            } else {
                resultingIndividual_2 = treeUtils.copy(crossoverRoot2.root);
            }
        }
    }

    public void randomSelectionTraverse(Node node) {
        node_list.add(node);
        for (int i = 0; i < node.offsrings.length; i++) {
            randomSelectionTraverse(node.offsrings[i]);
        }
    }

    public Node randomSelection(Node root) {
        node_list = new ArrayList<Node>();
        randomSelectionTraverse(root);
        return node_list.get(random.nextInt(node_list.size()));
    }

    public Node mutuj(Node root) {
        Node result;

        Node selected = randomSelection(root);
        Node created;
        if (MAX_TREE_DEPTH - selected.depth - 2 > 0) {
            created = treeCreator.create(random.nextInt(MAX_TREE_DEPTH - selected.depth - 2), random.nextInt(100));
        } else {
            created = treeCreator.create(0, random.nextInt(100));
        }

        if (selected.getParent() == null) {
            result = created;
        } else {
            result = treeUtils.copy(root);
            Node cselected = treeUtils.find(selected.id, result);
            cselected.getParent().exchange(cselected, created);
        }

        treeUtils.adjust(result);
        return result;
    }

    public Node getResultingIndividual_1() {
        return resultingIndividual_1;
    }

    public Node getResultingIndividual_2() {
        return resultingIndividual_2;
    }
}
