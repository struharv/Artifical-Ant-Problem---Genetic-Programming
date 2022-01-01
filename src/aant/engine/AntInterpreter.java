package aant.engine;


public class AntInterpreter {


    Map data;

    int steps;
    int maxSteps;

    int eaten;
    int counter;

    boolean exit;
    String last_id;

    public AntInterpreter(Map data) {
        this.data = data;
        this.eaten = 0;
    }


    public int see() {
        int newX = data.antX;
        int newY = data.antY;
        if (data.direction == 0) {
            newY = (newY + data.height - 1) % data.height;
        }
        if (data.direction == 2) {
            newY = (newY + 1) % data.height;
        }
        if (data.direction == 3) {
            newX = (newX + data.width - 1) % data.width;
        }
        if (data.direction == 1) {
            newX = (newX + 1) % data.width;
        }
        return data.map[newY][newX];
    }


    public void move() {
        int newX = data.antX;
        int newY = data.antY;
        if (data.direction == 0) {
            newY = (newY + data.height - 1) % data.height;
        }
        if (data.direction == 2) {
            newY = (newY + 1) % data.height;
        }
        if (data.direction == 3) {
            newX = (newX + data.width - 1) % data.width;
        }
        if (data.direction == 1) {
            newX = (newX + 1) % data.width;
        }

        data.antX = newX;
        data.antY = newY;

        if (data.map[data.antY][data.antX] == 1) {
            eaten++;
        }
        data.map[data.antY][data.antX] = -1;
    }


    public void left() {
        data.direction = (data.direction + 3) % 4;
    }


    public void right() {
        data.direction = (data.direction + 1) % 4;
    }


    public void traverse(Node node) {
        if (exit) {
            return;
        }
        if (node.getAction() == EAction.IF_FOOD_AHEAD) {
            if (see() == 1) {
                traverse(node.getOffsrings()[0]);
            } else {
                traverse(node.getOffsrings()[1]);
            }
        }

        if (node.getAction() == EAction.PROG2 || node.getAction() == EAction.PROG3) {
            for (int i = 0; i < node.getOffspringCount(); i++) {
                traverse(node.getOffsrings()[i]);
            }
        }

        if (node.getAction() == EAction.MOVE) {
            move();
            steps++;
            if (steps > maxSteps) {
                last_id = node.getId();
                exit = true;
            }
        }

        if (node.getAction() == EAction.LEFT) {
            left();
            steps++;
            if (steps > maxSteps) {
                last_id = node.getId();
                exit = true;
            }
        }

        if (node.getAction() == EAction.RIGHT) {
            right();
            steps++;
            if (steps > maxSteps) {
                last_id = node.getId();
                exit = true;
            }
        }

    }

    public String run(Node node, int maxKroku) {
        this.eaten = 0;
        this.steps = 0;
        this.maxSteps = maxKroku;
        exit = false;
        counter = 0;
        while (!exit) {
            traverse(node);
            counter++;
        }
        return last_id;
    }

    public Map getData() {
        return data;
    }
}
