package expression;

import expression.generic.Operation;

public class Max<T> extends BinaryOperator<T> {

    public Max(Operator<T> a, Operator<T> b, Operation<T> operation) {
        super(a, b, operation);
        operator = " max ";
        priority = 1;
    }

    @Override
    protected T doOperation(T first, T second) {
        return operation.max(first, second);
    }
}
