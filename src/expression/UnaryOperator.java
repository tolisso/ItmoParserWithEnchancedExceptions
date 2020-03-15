package expression;

import expression.generic.Operation;

import java.util.Map;

public abstract class UnaryOperator<T> extends Operator<T> {
    protected Operator<T> operand;

    public UnaryOperator (Operator operator, Operation<T> operation) {
        this.operand = operator;
        this.operation = operation;
    }
    @Override
    public final T evaluate(Map<String, T> values) {
        T val = operand.evaluate(values);
        return doOperation(val);
    }
    protected abstract T doOperation(T val);

    @Override
    public int getPriority() {
        return 10;
    }
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        toString(ans);
        return ans.toString();
    }
    @Override
    public String toMiniString() {
        StringBuilder ans = new StringBuilder();
        toMiniString(ans);
        return ans.toString();
    }
}
