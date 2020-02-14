package expression;

import expression.exceptions.OverflowException;

import java.util.ArrayList;
import java.util.Map;

public class Pow2 extends UnaryOperator {

    public Pow2(Operator operator) {
        super(operator);
    }

    @Override
    protected int doOperation(int result) {
        if (result < 0) {
            throw new IllegalArgumentException("Negative value of power: " + result);
        }
        if (result > 31) {
            throw new OverflowException("pow2 ", result + "");
        }
        return 1 << result;
    }

    @Override
    public double evaluate(double x) {
        throw new IllegalArgumentException("Function is not realized :(");
    }


    @Override
    public void toString(StringBuilder sb) {
        sb.append("pow2");
        sb.append("(");
        operand.toMiniString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("pow2");
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
