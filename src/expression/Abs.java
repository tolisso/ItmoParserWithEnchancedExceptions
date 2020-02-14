package expression;

import java.util.Map;

public class Abs extends UnaryOperator {
    public Abs(Operator operator) {
        super(operator);
    }

    @Override
    protected int doOperation(int val) {
        return Math.abs(val);
    }

    @Override
    public double evaluate(double x) {
        return Math.abs(operand.evaluate(x));
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("abs(");
        operand.toString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("abs(");
        operand.toMiniString(sb);
        sb.append(")");
    }
}
