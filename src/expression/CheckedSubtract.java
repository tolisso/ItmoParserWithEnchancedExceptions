package expression;

import expression.generic.Operation;

public class CheckedSubtract<T> extends BinaryOperator<T> {
    public CheckedSubtract(Operator<T> a, Operator<T> b, Operation<T> operation) {
        super(a, b, operation);
        operator = " - ";
        priority = 2;
        isAss = false;
    }

    @Override
    protected T doOperation(T first, T second) {
        return operation.subtract(first, second);
    }

}
