package aant.engine;

import java.util.Random;


public class Operands {

    OperandNumber[] operands;
    OperandNumber[] operands0;
    private final Random random;

    public Operands() {
        operands = new OperandNumber[]{
                    new OperandNumber(EAction.IF_FOOD_AHEAD, 2),
                    new OperandNumber(EAction.PROG2, 2),
                    new OperandNumber(EAction.PROG3, 3),
                    new OperandNumber(EAction.LEFT, 0),
                    new OperandNumber(EAction.RIGHT, 0),
                    new OperandNumber(EAction.MOVE, 0)
                };
        buildOperands0();
        this.random = new Random();
    }

    public OperandNumber getRandomArita() {
        return operands[random.nextInt(operands.length)];
    }

    public OperandNumber getRandomArita0() {
        return operands0[random.nextInt(operands0.length)];
    }

    private void buildOperands0() {
        int cnt = 0;
        for (OperandNumber i : operands) {
            if (i.getOperandNumber() == 0) {
                cnt++;
            }
        }
        operands0 = new OperandNumber[cnt];
        for (int i = 0; i < operands.length; i++) {
            if (operands[i].getOperandNumber() == 0) {
                operands0[--cnt] = operands[i];
            }
        }
    }
}
