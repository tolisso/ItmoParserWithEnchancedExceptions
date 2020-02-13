package expression;

import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> map = new TreeMap<>();
        map.put("x", 3);
        map.put("y", 1);
        Expression first = new CheckedSubtract(
                new CheckedMultiply(
                        new Const(2), new Variable("x")
                ),
                new Const(3)
        );
        Expression second = new CheckedMultiply(new CheckedSubtract(
                new CheckedMultiply(
                        new Const(2), new Variable("x")
                ),
                new Const(3)
        ), new CheckedAdd(new Const(2), new Variable("y")));
        Expression third = new CheckedSubtract(new CheckedSubtract(new Const(1), new Const(1)), new CheckedSubtract(new Const(1), new Const(1)));

                System.out.println(first);
        System.out.println(first.evaluate(1));
        System.out.println(second);
        System.out.println(second.evaluate(2));
        System.out.println(third);
    }
}
