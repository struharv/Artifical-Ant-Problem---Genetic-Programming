package aant.engine;


public class OperandNumber {

    EAction action;
    int operandNumber;

    public OperandNumber(EAction action, int arita) {
        this.action = action;
        this.operandNumber = arita;
    }

    public EAction getAction() {
        return action;
    }

    public int getOperandNumber() {
        return operandNumber;
    }

    
}
