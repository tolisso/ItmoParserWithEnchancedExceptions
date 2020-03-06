package expression.parser;

import expression.exceptions.ParsingException;

public class Main {

    public static void main(String[] args) throws ParsingException {
        ExpressionParser parser = new ExpressionParser();
        String str = parser.parse("abc // value+value*value/x+y").toString();
        System.out.println(str);
    }
}
