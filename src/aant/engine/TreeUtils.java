package aant.engine;

import java.util.ArrayList;
import aant.gui.SourceLine;

public class TreeUtils {

    Node found;
    String idToFind;
    int cnt;
    int maxDepth;
    int maxNodes;

    ArrayList<SourceLine> sourceLines;

    public void traverse_print(Node node) {
        System.out.format("\t%s [label=\"%s\"]%n", node.getId(), node.getAction());
        for (int i = 0; i < node.getOffspringCount(); i++) {
            System.out.format("\t%s -- %s%n", node.getId(), node.getOffsrings()[i].getId());
        }

        for (int i = 0; i < node.getOffspringCount(); i++) {
            traverse_print(node.getOffsrings()[i]);
        }
    }

    public void printResults(Node node) {
        System.out.println("graph a{");
        traverse_print(node);
        System.out.println("}");
    }

    public void reconnect(Node source, Node destination) {

        for (int i = 0; i < source.getOffsrings().length; i++) {
            Node offspring = source.getOffsrings()[i];
            Node newNode = new Node(offspring.getAction(), offspring.getArita(), offspring.getId());
            destination.addOffspring(newNode);
        }
        for (int i = 0; i < source.getOffsrings().length; i++) {
            reconnect(source.getOffsrings()[i], destination.getOffsrings()[i]);
        }
    }

    public Node copy(Node root) {
        Node newRoot = new Node(root.getAction(), root.getArita(), root.getId());
        newRoot.setParent(null);
        reconnect(root, newRoot);
        return newRoot;
    }

    private void findTraverse(Node root) {
        if (this.found != null) {
            return;
        }

        if (root.getId().equals(this.idToFind)) {
            this.found = root;
            return;
        }

        for (int i = 0; i < root.offspringCount; i++) {
            findTraverse(root.getOffsrings()[i]);
        }
    }

    public Node find(String id, Node root) {
        found = null;
        this.idToFind = id;
        findTraverse(root);

        return found;
    }

    public void adjustTraverse(Node root, int depth) {
        root.setId("" + cnt);
        root.setDepth(depth);
        cnt++;
        for (int i = 0; i < root.offspringCount; i++) {
            adjustTraverse(root.getOffsrings()[i], depth + 1);
        }
    }

    public void adjust(Node root) {
        cnt = 0;
        adjustTraverse(root, 0);
    }

    private void depthTraverse(Node node, int hloubka) {
        if (hloubka > maxDepth) {
            maxDepth = hloubka;
        }
        for (int i = 0; i < node.offspringCount; i++) {
            depthTraverse(node.getOffsrings()[i], hloubka + 1);
        }
    }

    public int depth(Node koren) {
        maxDepth = 0;
        depthTraverse(koren, 0);
        return maxDepth;
    }

    private void nodesTraverse(Node node) {
        maxNodes++;
        for (int i = 0; i < node.offspringCount; i++) {
            nodesTraverse(node.getOffsrings()[i]);
        }
    }

    private void getSourceTraverse(Node node, int uroven) {
        maxNodes++;
        int novauroven = uroven;
        if (node.action == EAction.IF_FOOD_AHEAD) {
            novauroven++;
            sourceLines.add(new SourceLine(node.id, uroven, ""+ node.action +" {"));
            //builder.append(odsazeni(uroven) + uzel.akce + "{\n");
            getSourceTraverse(node.getOffsrings()[0], novauroven);
            sourceLines.add(new SourceLine("", uroven, "} else {"));
            //sbuilder.append(odsazeni(uroven) + "} else {\n");
            getSourceTraverse(node.getOffsrings()[1], novauroven);
            //sbuilder.append(odsazeni(uroven) + "}\n");
            sourceLines.add(new SourceLine("", uroven, "} "));
        } else {
            if (node.action != EAction.PROG2 && node.action != EAction.PROG3) {
                sourceLines.add(new SourceLine(node.id, uroven, ""+ node.action));
                //sbuilder.append(odsazeni(uroven) + uzel.akce+"\n");
            }
            for (int i = 0; i < node.offspringCount; i++) {
                getSourceTraverse(node.getOffsrings()[i], novauroven);
            }
        }
    }

    public ArrayList<SourceLine> getSource(Node koren) {
        sourceLines = new ArrayList<SourceLine>();
        getSourceTraverse(koren, 0);
        return sourceLines;

    }

    public int nodeCount(Node koren) {
        maxNodes = 0;
        nodesTraverse(koren);
        return maxNodes;
    }

    
}
