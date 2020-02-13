package expression;

import java.util.Map;

public class Square extends UnaryOperator {

    public Square(Operator operator) {
        super(operator);
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        int ans = operand.evaluate(values);
        return ans * ans;
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
