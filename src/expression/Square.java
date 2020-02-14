package expression;

import java.util.Map;

public class Square extends UnaryOperator {

    public Square(Operator operator) {
        super(operator);
    }

    @Override
    protected int doOperation(int val) {
        return val * val;
    }

    @Override
    public double evaluate(double x) {
        double ans = operand.evaluate(x);
        return ans * ans;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("square(");
        operand.toString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("square(");
        operand.toMiniString(sb);
        sb.append(")");
    }
}
