package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedSubtract extends BinaryOperator {
    public CheckedSubtract(Operator a, Operator b) {
        super(a, b);
        operator = " - ";
        priority = 2;
        isAss = false;
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        // System.out.println("Subtract");
        int first = firstOperand.evaluate(values);
        int second = secondOperand.evaluate(values);
        int result = first - second;
        boolean overflowFlag = false;
        if (second > 0) {
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
            trace.append(" - ");
            trace.append(second);
            trace.append(" = ");
            trace.append(result);
            throw new OverflowException(trace.toString());
        }
        return result;
        // return firstOperand.evaluate(values) - secondOperand.evaluate(values);
    }
    @Override
    public double evaluate(double value) {
        return firstOperand.evaluate(value) - secondOperand.evaluate(value);
    }
}
