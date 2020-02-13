package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedNegate extends UnaryOperator {

    public CheckedNegate(Operator operator) {
        super(operator);
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        int result = operand.evaluate(values);
        if (result != 0 && result == -result) {
            throw new OverflowException("Negating " + result);
        }
        return -result;
        // return -operand.evaluate(values);
    }

    @Override
    public double evaluate(double x) {
        return -operand.evaluate(x);
    }


    @Override
    public void toString(StringBuilder sb) {
        sb.append("-");
        sb.append("(");
        operand.toMiniString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("-");
        if (operand instanceof UnaryOperator || operand instanceof Const
                || operand instanceof Variable) {
            operand.toMiniString(sb);
        } else {
            sb.append("(");
            operand.toMiniString(sb);
            sb.append(")");
        }
    }

    @Override
    public String toMiniString() {
        StringBuilder ans = new StringBuilder();
        toMiniString(ans);
        return ans.toString();
    }
}
