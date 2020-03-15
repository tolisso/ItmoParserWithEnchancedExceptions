package expression.parser;

import expression.exceptions.ParsingException;
import expression.generic.IntegerOperation;

public class Main {

    public static void main(String[] args) throws ParsingException {
        ExpressionParser<Integer> parser = new ExpressionParser<>(new IntegerOperation());
        String str = parser.parse("abc // value+value*value/x+y").toString();
        System.out.println(str);
    }
}
