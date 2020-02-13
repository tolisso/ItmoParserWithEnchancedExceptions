package expression;

import java.util.Map;
import java.util.TreeMap;

public abstract class Operator implements Expression, DoubleExpression, CommonExpression {
    public abstract int evaluate(Map<String, Integer> values);
    public int evaluate(int x) {
        return evaluate(x, -1, -1);
    }

    public int evaluate(int x, int y, int z) {
        Map<String, Integer> map = new TreeMap<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        // System.out.println(x + " " + y + " " + z);
        return evaluate(map);
    }
    public abstract double evaluate(double x);
    public abstract int getPriority();
    public boolean equals(Object obj) {
        if (obj instanceof Operator) {
            return toMiniString().equals(((Operator)obj).toMiniString());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    public abstract void toString(StringBuilder sb);
    public abstract void toMiniString(StringBuilder sb);
}
