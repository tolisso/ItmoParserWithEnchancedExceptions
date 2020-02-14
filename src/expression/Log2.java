package expression;

import expression.exceptions.LogException;
import expression.exceptions.OverflowException;

import java.util.ArrayList;
import java.util.Map;

public class Log2 extends UnaryOperator {

    public Log2(Operator operator) {
        super(operator);
    }

    @Override
    protected int doOperation(int result) {
        if (result <= 0) {
            throw new LogException("Wrong value log2 " + result);
        }
        /*
         */

        int l = 0, r = 32;
        while (r - l != 1) {
            int m = (r + l) / 2;
            if (result / (1 << m) != 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    @Override
    public double evaluate(double x) {
        throw new IllegalArgumentException("Function is not realized :(");
    }


    @Override
    public void toString(StringBuilder sb) {
        sb.append("log2");
        sb.append("(");
        operand.toMiniString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        sb.append("log2");
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
