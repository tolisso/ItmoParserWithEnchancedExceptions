package expression;

import expression.generic.Operation;

public class Min<T> extends BinaryOperator<T> {

    public Min(Operator<T> a, Operator<T> b, Operation<T> operation) {
        super(a, b, operation);
        operator = " min ";
        priority = 1;
    }

    @Override
    protected T doOperation(T first, T second) {
        return operation.min(first, second);
    }
}
