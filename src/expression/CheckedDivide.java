package expression;

import expression.exceptions.OverflowException;

import java.util.Map;

public class CheckedDivide extends BinaryOperator {
    public CheckedDivide(Operator a, Operator b) {
        super(a, b);
        operator = " / ";
        priority = 5;
        isAss = false;
    }

    @Override
    public int evaluate(Map<String, Integer> values) {
        // System.out.println("Divide");
        int first = firstOperand.evaluate(values);
        int second = secondOperand.evaluate(values);
        int result = first / second;
        boolean overflowFlag = false;
        if (second != 1 && first != 0 && first == result) {
            overflowFlag = true;
        }

        if (overflowFlag) {
            StringBuilder trace = new StringBuilder();
            trace.append("Overflow: ");
            trace.append(first);
            trace.append(" / ");
            trace.append(second);
            trace.append(" = ");
            trace.append(result);
            throw new OverflowException(trace.toString());
        }

        return result;
        // return firstOperand.evaluate(values) / secondOperand.evaluate(values);
    }
    public double evaluate(double value) {
        return firstOperand.evaluate(value) / secondOperand.evaluate(value);
    }
}
