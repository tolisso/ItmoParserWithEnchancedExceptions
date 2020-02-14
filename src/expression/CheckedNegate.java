package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedNegate extends UnaryOperator {

    public CheckedNegate(Operator operator) {
        super(operator);
    }

    protected int doOperation(int result) {
        if (result == Integer.MIN_VALUE) {
            throw new OverflowException("-", result + "");
        }
        return -result;
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
