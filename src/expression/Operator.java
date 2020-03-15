package expression;

import expression.generic.Operation;

import java.util.Map;
import java.util.TreeMap;

public abstract class Operator<T> implements TripleExpression<T> {
    protected Operation<T> operation;

    public abstract T evaluate(Map<String, T> values);
    public T evaluate(T x) {
        return evaluate(x, null, null);
    }

    public T evaluate(T x, T y, T z) {
        Map<String, T> map = new TreeMap<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return evaluate(map);
    }
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
