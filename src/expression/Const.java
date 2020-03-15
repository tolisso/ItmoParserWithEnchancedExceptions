package expression;

import expression.generic.Operation;

import java.util.Map;

public class Const<T> extends Operator<T> {
    public T value;
    private boolean negate;
    public Const(T val, Operation<T> operation) {
        this.operation = operation;
        value = val;
    }
    public Const(T val, Operation<T> operation, boolean negate) {
        this(val, operation);
        this.negate = negate;
    }

    public boolean isNegated() {
        return negate;
    }
    public void deleteNegate() {
        negate = false;
    }

    @Override
    public T evaluate(Map<String, T> values) {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(value);
    }

    @Override
    public void toMiniString(StringBuilder sb) {
        toString(sb);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            return value.equals(((Const)obj).value);
        }
        return false;
    }

    public Const negated() {
        return new Const<T>(operation.negate(value), operation);
    }
}
