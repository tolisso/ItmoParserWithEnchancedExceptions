package expression;

import java.util.Map;
import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryOperator {

    public CheckedAdd(Operator a, Operator b) {
        super(a, b);
        operator = " + ";
        priority = 2;
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        // System.out.println("Add");
        int first = firstOperand.evaluate(values);
        int second = secondOperand.evaluate(values);
        int result = first + second;
        boolean overflowFlag = false;
        if (second < 0) {
            if (result > first) {
                overflowFlag = true;
            }
        } else {
            if (result < first) {
                overflowFlag = true;
            }
        }

        if (overflowFlag) {
            StringBuilder trace = new StringBuilder();
            trace.append("Overflow: ");
            trace.append(first);
            trace.append(" + ");
            trace.append(second);
            trace.append(" = ");
            trace.append(result);
            throw new OverflowException(trace.toString());
        }

        return result;
        //return firstOperand.evaluate(values) + secondOperand.evaluate(values);
    }
    @Override
    public double evaluate(double x) {
        return firstOperand.evaluate(x) + secondOperand.evaluate(x);
    }
}
