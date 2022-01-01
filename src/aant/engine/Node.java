package aant.engine;


public class Node {

    EAction action;
    Node[] offsrings;
    int arita;
    int offspringCount;
    int depth;
    String id;
    Node parent;

    public Node(EAction action, int arita, String id) {
        this.action = action;
        this.arita = arita;
        this.parent = null;
        this.offspringCount = 0;
        this.offsrings = new Node[arita];
        for (int i = 0; i < arita; i++) {
            this.offsrings[i] = null;
        }

        this.depth = 0;
        this.id = id;
    }

    public void addOffspring(Node node) {
        offsrings[offspringCount] = node;
        offsrings[offspringCount].setDepth(depth + 1);
        offsrings[offspringCount].parent = this;
        this.offspringCount++;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getOffspringCount() {
        return offspringCount;
    }

    public int getArita() {
        return arita;
    }

    public Node[] getOffsrings() {
        return offsrings;
    }

    public EAction getAction() {
        return action;
    }

    public int getDepth() {
        return depth;
    }

    public String getId() {
        return id;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void exchange(Node node, Node exhcangeWith) {
        Node parent = node.getParent();
        for (int i = 0; i < parent.offsrings.length; i++) {
            if (parent.offsrings[i].id.equals(node.getId())) {
                parent.offsrings[i] = exhcangeWith;
                parent.offsrings[i].setParent(parent);
                break;
            }
        }
    }

    public void setId(String id) {
        this.id = id;
    }
}
