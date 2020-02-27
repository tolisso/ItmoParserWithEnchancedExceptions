package expression.parser;

import expression.exceptions.ParsingException;

public class Main {

    public static void main(String[] args) throws ParsingException {
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse("16//2"));
        System.out.println(parser.parse("16//2").evaluate(0));
        System.out.println(parser.parse("17//2").evaluate(0));
        System.out.println(parser.parse("31//2").evaluate(0));
        System.out.println(parser.parse("32//2").evaluate(0));
        System.out.println(parser.parse("3**4").evaluate(1));
    }
}
