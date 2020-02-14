package expression;

import expression.exceptions.LogException;
import expression.exceptions.OverflowException;

import java.util.Map;

public class Log extends BinaryOperator {
    public Log(Operator a, Operator b) {
        super(a, b);
        operator = " // ";
        priority = 7;
        isAss = false;
    }

    @Override
    protected int doOperation(int first, int second) {
        if (second < 2) {
            throw new LogException("Wrong base in log: " + second);
        }
        if (first < 1) {
            throw new LogException("Wrong value log" + second + " " + first);
        }
        if (first == 1) {
            return 0;
        }
        first /= second;
        int ans = 1;
        int ansPow = 0;
        while (ans <= first) {
            ans *= second;
            ansPow++;
        }
        return ansPow;
    }

    public double evaluate(double value) {
        throw new IllegalArgumentException("Function is not realized :(");
    }
}
