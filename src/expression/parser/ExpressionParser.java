package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.generic.Operation;

public class ExpressionParser<T> implements Parser<T> {
    private Source source;
    private Operation<T> operation;
    //Expression exc;
    private int depth = 0;
    private boolean isLastBinaryOperator = false;
    private String lastParsed = "";
    private String prevParsed = "begin";
    private boolean currentOperationParsed = false;

    public ExpressionParser(Operation<T> operation) {
        this.operation = operation;
    }

    public Operator parse(String str) throws ParsingException {
        defaultCondition();
//        System.out.println(str);
        source = new Source(str);
        Operator ans = null;
        Operator prev = null;
        while (source.current() != '#') {
            ans = parseValue(0, ans, false);
            if (ans == prev) {
                throw new CloseParenthesisException(lastParsed, source.currentWord(), source.getPos());
            }
            prev = ans;
        }
//        if (source.current() != '#') {
//            throw new RuntimeException("Unexpected symbol: " + source.fromPos());
//        }
//        System.out.println("(" + str + ")");
        return ans;
    }

    public Operator parseValue(final int priority, Operator ans, boolean negate) throws ParsingException {
        char current;
        int counter = 0;
        do {
            // System.out.println(source.currentWord());
            Operator prev = ans;
            source.skipWhitespaces();
            source.parseCurrentWord();
            current = source.current();
            // System.out.println(source.currentWord());
            // System.out.println(current + " " + priority);
            // System.out.println(lastParsed + " " + current);
            if (source.currentWord().equals("x") || source.currentWord().equals("y") ||
                    source.currentWord().equals("z")) {
                isLastBinaryOperator = false;
                setLastParsed(source.currentWord());
                String variable = source.currentWord();
                source.nextWord();
                if (priority == 10) {
                    return new Variable<T>(variable, operation);
                } else {
                    ans = new Variable<T>(variable, operation);
                }
                continue;
            }

            if (current == '(') {
                setLastParsed(current);
                isLastBinaryOperator = false;
                source.nextWord();
                Operator oldAns = ans;
                do {
                    ans = parseValue(0, ans, false);
                    // isLastBinaryOperator = false;
                } while (source.current() != ')' && source.current() != '#');
                if (source.current() == '#') {
                    throw new NoCloseParenthesisException(source.getPos());
                }
                if (oldAns == ans) {
                    throw new NothingBetweenParenthesisException(source.getPos());
                }
                setLastParsed(source.current());
                source.nextWord();
                isLastBinaryOperator = false;
                source.skipWhitespaces();
                current = source.current();
                if (priority >= getWordPriority(source.currentWord())) {
                    return ans;
                } else {
                    return parseValue(priority, ans, false);
                }
            }

            if (source.current() == ')') {
                if (isLastBinaryOperator || negate) {
                    isLastBinaryOperator = false;
                    return null;
//                    throw new RuntimeException("wrong symbol after \'" + lastParsed + "\': " + source.current());
                }
                isLastBinaryOperator = false;
                return ans;
            }

            current = source.current();

            if ('0' <= current && current <= '9') {
                isLastBinaryOperator = false;
                Integer curInt;
                if (current != '0') {
                    curInt = parseInt(negate);
                } else {
                    curInt = 0;
                    source.next();
                }
                source.skipWhitespaces();
                source.parseCurrentWord();
                current = source.current();
                if (ans != null) {
                    throw new WrongSymbolException(lastParsed, curInt + "", source.getPos());
                }
                setLastParsed(curInt + "");
                if (getWordPriority(source.currentWord()) < priority) {
                    if (negate) {
                        return new Const<T>(operation.wrap(curInt), operation, true);
                    }
                    return new Const<T>(operation.wrap(curInt), operation);
                } else {
                    if (negate) {
                        return parseValue(priority, new Const<T>(operation.wrap(curInt), operation, true), false);
                    }
                    return parseValue(priority, new Const<T>(operation.wrap(curInt), operation), false);
                }
            }

            source.parseCurrentWord();
            // System.out.println(source.currentWord());
            if (source.currentWord().equals("+") || source.currentWord().equals("-") ||
                    source.currentWord().equals("*") || source.currentWord().equals("/") ||
                    source.currentWord().equals("min") || source.currentWord().equals("max")  ||
                    source.currentWord().equals("count") /*||
                    source.currentWord().equals("abs") || source.currentWord().equals("square") ||
                    source.currentWord().equals("<<") || source.currentWord().equals(">>") ||
                    source.currentWord().equals("log2") || source.currentWord().equals("pow2") ||
                    source.currentWord().equals("**") || source.currentWord().equals("//") */) {
                if (priority >= getWordPriority(source.currentWord()) && ans != null) {
                    return ans;
                }
                if (!source.currentWord().equals("-") && !source.currentWord().equals("count")/* &&
                        !source.currentWord().equals("abs") &&
                        !source.currentWord().equals("pow2") &&
                        !source.currentWord().equals("log2")*/) {
                    if (ans == null) {
                        if (isOperator(lastParsed)) {
                            throw new MiddleOperandException(lastParsed, source.currentWord(), source.getPos());
                        } else {
                            throw new FirstOperandException(lastParsed, source.currentWord(), source.getPos());
                        }
                    }
                    isLastBinaryOperator = true;
                }
                setLastParsed(source.currentWord());
                source.nextWord();
                current = source.current();

//                if (lastParsed.equals("**")) {
//                    Operator secondOperand = parseValue(getWordPriority("**"), null, false);
//                    checkSecondOperand(secondOperand);
//                    Operator result = new Pow(ans, secondOperand);
//                    if (priority < getWordPriority("**")) {
//                        ans = result;
//                    } else {
//                        return result;
//                    }
//                }
//                if (lastParsed.equals("//")) {
//                    Operator secondOperand = parseValue(getWordPriority("//"), null, false);
//                    checkSecondOperand(secondOperand);
//                    Operator result = new Log(ans, secondOperand);
//                    if (priority < getWordPriority("//")) {
//                        ans = result;
//                    } else {
//                        return result;
//                    }
//                }
//
//                if (lastParsed.equals("<<")) {
//                    Operator secondOperand = parseValue(getWordPriority("<<"), null, false);
//                    checkSecondOperand(secondOperand);
//                    Operator result = new ShiftLeft(ans, secondOperand);
//                    if (priority < getWordPriority("<<")) {
//                        ans = result;
//                    } else {
//                        return result;
//                    }
//                }
//
//                if (lastParsed.equals(">>")) {
//                    Operator secondOperand = parseValue(getWordPriority(">>"), null, false);
//                    checkSecondOperand(secondOperand);
//                    Operator result = new ShiftRight(ans, secondOperand);
//                    if (priority < getWordPriority(">>")) {
//                        ans = result;
//                    } else {
//                        return result;
//                    }
//                }
//                if (lastParsed.equals("abs")) {
//                    Operator operand = parseValue(10, null, false);
//                    checkOperand(operand);
//                    if (priority < 10) {
//                        ans = new Abs(operand); // 10 is UnaryOperator priority
//
//                    } else if (priority >= 10) {
//                        return new Abs(operand);
//                    }
//                }
//                if (lastParsed.equals("log2")) {
//                    /*if (source.current() != '(' && source.current() != ' ') {
//                        throw new RuntimeException("No space between log2 and operand");
//                    }*/
//                    Operator operand = parseValue(10, null, false);
//                    checkOperand(operand);
//                    if (priority < 10) {
//                        ans = new Log2(operand); // 10 is UnaryOperator priority
//
//                    } else if (priority >= 10) {
//                        return new Log2(operand);
//                    }
//                }
//                if (lastParsed.equals("pow2")) {
//                    /*if (source.current() != '(' && source.current() != ' ') {
//                        throw new RuntimeException("No space between log2 and operand");
//                    }*/
//                    Operator operand = parseValue(10, null, false);
//                    checkOperand(operand);
//                    if (priority < 10) {
//                        ans = new Pow2(operand); // 10 is UnaryOperator priority
//
//                    } else if (priority >= 10) {
//                        return new Pow2(operand);
//                    }
//                }
//                if (lastParsed.equals("square")) {
//                    Operator operand = parseValue(10, null, false);
//                    checkOperand(operand);
//                    if (priority < 10) {
//                        ans = new Square(operand);
//                    } else if (priority >= 10) {
//                        return new Square(operand);
//                    }
//                }
                if (lastParsed.equals("count")) {
                    Operator operand = parseValue(10, null, false);
                    checkOperand(operand);
                    if (priority < 10) {
                        ans = new Count<>(operand, operation);
                    } else if (priority >= 10) {
                        return new Count<>(operand, operation);
                    }
                }
                if (lastParsed.equals("min")) {
                    Operator secondOperand = parseValue(getWordPriority("min"), null, false);
                    if (secondOperand == null) {
                        throw new SecondOperandException(prevParsed, lastParsed,
                                source.currentWord(), source.getPos());
                    }
                    Operator result = new Min<T>(ans, secondOperand, operation);

                    if (priority < getWordPriority("min")) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
                if (lastParsed.equals("max")) {
                    Operator secondOperand = parseValue(getWordPriority("max"), null, false);
                    if (secondOperand == null) {
                        throw new SecondOperandException(prevParsed, lastParsed,
                                source.currentWord(), source.getPos());
                    }
                    Operator result = new Max<T>(ans, secondOperand, operation);

                    if (priority < getWordPriority("max")) {
                        ans = result;
                    } else {
                        return result;
                    }
                }

                if (lastParsed.equals("+")) {
                    Operator secondOperand = parseValue(getWordPriority("+"), null, false);
                    if (secondOperand == null) {
                        throw new SecondOperandException(prevParsed, lastParsed,
                                source.currentWord(), source.getPos());
                    }
                    Operator result = new CheckedAdd<T>(ans, secondOperand, operation);

                    if (priority < getWordPriority("+")) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
                if (lastParsed.equals("-")) {
                    if (ans == null || isLastBinaryOperator) {
                        Operator res = parseValue(10, null, true);
                        if (res == null) {
                            throw new OperandException(lastParsed, source.currentWord(), source.getPos());
                        }
                        if (priority < 10) {
                            ans = new CheckedNegate<T>(res, operation); // 10 is UnaryOperator priority
                            if (res instanceof Const) {
                                if (((Const) res).isNegated()) {
                                    ((Const) res).deleteNegate();
                                    ans = res;
                                } else {
                                    ans = ((Const)res).negated();
                                }
                            }
                        } else if (priority >= 10) {
                            if (res instanceof Const) {
                                if (((Const) res).isNegated()) {
                                    ((Const) res).deleteNegate();
                                    return res;
                                } else {
                                    return ((Const)res).negated();
                                }
                            }
                            return new CheckedNegate<T>(res, operation);
                        }
                    } else {
                        isLastBinaryOperator = true;
                        Operator secondOperand = parseValue(getWordPriority("-"), null, false);
                        if (secondOperand == null) {
                            throw new SecondOperandException(prevParsed, lastParsed,
                                    source.currentWord(), source.getPos());
                        }
                        Operator result = new CheckedSubtract<T>(ans, secondOperand, operation);
                        if (priority < getWordPriority("-")) {
                            ans = result;
                        } else {
                            return result;
                        }
                    }
                }
                if (lastParsed.equals("*")) {
                    Operator secondOperand = parseValue(getWordPriority("*"), null, false);
                    checkSecondOperand(secondOperand);
                    Operator result = new CheckedMultiply<T>(ans, secondOperand, operation);

                    if (priority < getWordPriority("*")) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
                if (lastParsed.equals("/")) {
                    Operator secondOperand = parseValue(getWordPriority("/"), null, false);
                    checkSecondOperand(secondOperand);
                    Operator result = new CheckedDivide(ans, secondOperand, operation);
                    if (priority < getWordPriority("/")) {
                        ans = result;
                    } else {
                        return result;
                    }
                }
            }
            if (ans == prev) {
                if (source.current() == '#') {
                    return ans;
                }
                throw new WrongSymbolException(prevParsed, source.currentWord(), source.getPos());
            }
        } while (true);
    }

//    private int getPriority(char ch) {
//        if (ch == '+') {
//            return new CheckedAdd(null, null).getPriority();
//        }
//        if (ch == '-') {
//            return new CheckedSubtract(null, null).getPriority();
//        }
//        if (ch == '*') {
//            return new CheckedMultiply(null, null).getPriority();
//        }
//        if (ch == '/') {
//            return new CheckedDivide(null, null).getPriority();
//        }
//        if (ch == '>') {
//            return new ShiftRight(null, null).getPriority();
//        }
//        if (ch == '<') {
//            return new ShiftLeft(null, null).getPriority();
//        }
//        if (ch == 'a') {
//            return 10;
//        }
//        if (ch == 's') {
//            return 10;
//        }
//        if (ch == ')') {
//            return 10;
//        }
//        return 0;
//    }
    private int getWordPriority(String word) {
        if (word.equals("+")) {
            return new CheckedAdd(null, null, operation).getPriority();
        }
        if (word.equals("-")) {
            return new CheckedSubtract(null, null, operation).getPriority();
        }
        if (word.equals("*")) {
            return new CheckedMultiply(null, null, operation).getPriority();
        }
        if (word.equals("/")) {
            return new CheckedDivide(null, null, operation).getPriority();
        }
        if (word.equals("min")) {
            return new Min(null, null, operation).getPriority();
        }
        if (word.equals("max")) {
            return new Max(null, null, operation).getPriority();
        }
        if (word.equals("count")) {
            return new Count(null, operation).getPriority();
        }
        /*if (word.equals(">>")) {
            return new ShiftRight(null, null).getPriority();
        }
        if (word.equals("<<")) {
            return new ShiftLeft(null, null).getPriority();
        }
        if (word.equals("abs")) {
            return 10;
        }
        if (word.equals("square")) {
            return 10;
        }
        if (word.equals("//")) {
            return 7;
        }
        if (word.equals("**")) {
            return 7;
        } */
        if (word.equals(")")) {
            return 10;
        }
        return 0;
    }

    private int parseInt(boolean negate) throws ParsingException {
        StringBuilder sb = new StringBuilder(source.current());
        while ('0' <= source.current() && source.current() <= '9') {
            sb.append(source.current());
            source.next();
        }
        setLastParsed(sb + "");
        source.parseCurrentWord();
        try {
            if (negate) {
                return Integer.parseInt("-" + sb);
            } else {
                return Integer.parseInt(sb.toString());
            }
        } catch (NumberFormatException exc) {
            throw new BigDecimalException((negate ? "-" : "") + sb);
        }
    }

    void setLastParsed(String parsed) {
        prevParsed = lastParsed;
        lastParsed = parsed;
    }
    void setLastParsed(char parsed) {
        setLastParsed(parsed + "");
    }
    void checkSecondOperand(Operator secondOperand) throws ParsingException {
        if (secondOperand == null) {
            throw new SecondOperandException(prevParsed, lastParsed, source.currentWord(), source.getPos());
        }
    }
    void checkOperand(Operator operand) throws ParsingException{
        if (operand == null) {
            throw new OperandException(lastParsed, source.currentWord(), source.getPos());
        }
    }
    boolean isOperator(String str) {
        switch(str) {
            case "+":
            case "-":
            case "*":
            case "/":
            /*case ">>":
            case "<<":
            case "pow2":
            case "log2":
            case "//":
            case "**":*/
                return true;
        }
        return false;
    }

    private void defaultCondition() {
        depth = 0;
        boolean isLastBinaryOperator = false;
        String lastParsed = "";
        String prevParsed = "begin";
        boolean currentOperationParsed = false;
    }
}
