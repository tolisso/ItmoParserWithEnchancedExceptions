package expression;

import expression.generic.Operation;

public class CheckedMultiply<T> extends BinaryOperator<T> {
    public CheckedMultiply(Operator a, Operator b, Operation operation) {
        super(a, b, operation);
        operator = " * ";
        priority = 5;
    }

    protected T doOperation(T first, T second) {
        return operation.multiply(first, second);
    }


}
