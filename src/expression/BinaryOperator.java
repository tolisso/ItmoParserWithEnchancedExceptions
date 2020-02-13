package expression;

public abstract class BinaryOperator extends Operator {
    public final Operator firstOperand;
    public final Operator secondOperand;
    protected String operator = "<none>";
    protected int priority = -1;
    protected boolean isAss = true;
    public BinaryOperator(Operator firstOperand, Operator secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public void toMiniString(StringBuilder sb) {
        if (firstOperand.getPriority() < priority) {
            sb.append("(");
            firstOperand.toMiniString(sb);
            sb.append(")");
        } else {
            firstOperand.toMiniString(sb);
        }
        sb.append(operator);
        if (priority > secondOperand.getPriority() ||
                (priority == secondOperand.getPriority() &&
                        secondOperand instanceof BinaryOperator && (!((BinaryOperator)secondOperand).isAss || !isAss))) {
            sb.append("(");
            secondOperand.toMiniString(sb);
            sb.append(")");
        } else {
            secondOperand.toMiniString(sb);
        }
    }

    public void toString(StringBuilder sb) {
        if (firstOperand instanceof BinaryOperator) {
            sb.append("(");
            firstOperand.toString(sb);
            sb.append(")");
        } else {
            firstOperand.toString(sb);
        }

        sb.append(operator);
        if (secondOperand instanceof BinaryOperator) {
            sb.append("(");
            secondOperand.toString(sb);
            sb.append(")");
        } else {
            secondOperand.toString(sb);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return "(" + sb + ")";
    }

    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        toMiniString(sb);
        return sb.toString();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperator) {
            BinaryOperator binOp = (BinaryOperator)obj;
            if (getClass().equals(binOp.getClass())) {
                return firstOperand.equals(binOp.firstOperand) && secondOperand.equals(binOp.secondOperand);
            }
        }
        return false;
    }
}
