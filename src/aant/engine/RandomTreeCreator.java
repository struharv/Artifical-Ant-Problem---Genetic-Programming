package aant.engine;

import java.util.ArrayList;
import java.util.Random;


public class RandomTreeCreator {

    ArrayList<Node> nodes; // seznam vsech uzlu ve stromu
    Operands operands;
    Random random;

    public RandomTreeCreator() {
        this.operands = new Operands();
        this.random = new Random();
    }

    public Node create(int maxHloubka, int maxPocet) {
        nodes = new ArrayList<Node>();

        //pridani korenu do stromu
        int cnt = 0;
        OperandNumber rand_operandNumber;
        if (maxHloubka > 0) {
            rand_operandNumber = operands.getRandomArita();
        } else {
            rand_operandNumber = operands.getRandomArita0();
        }
        Node koren = new Node(rand_operandNumber.getAction(), rand_operandNumber.getOperandNumber(), "" + cnt++); //korenovy uzel
        nodes.add(koren);

        while (true) {
            boolean finish = true;
            int selected = random.nextInt(nodes.size());
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get((i + selected) % nodes.size());
                if (node.getOffspringCount() < node.getArita()) {

                    if (node.getDepth() > maxHloubka || (maxPocet > -1 && cnt > maxPocet)) {
                        rand_operandNumber = operands.getRandomArita0();
                    } else {
                        rand_operandNumber = operands.getRandomArita();
                    }
                    Node newNode = new Node(rand_operandNumber.getAction(), rand_operandNumber.getOperandNumber(), "" + cnt++);

                    node.addOffspring(newNode);
                    nodes.add(newNode);

                    finish = false;
                    break;
                }
            }
            if (finish) {
                break;
            }
        }
        return koren;
    }
}
