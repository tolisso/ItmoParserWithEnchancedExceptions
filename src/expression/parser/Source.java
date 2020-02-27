package expression.parser;

import expression.exceptions.ParsingException;
import expression.exceptions.WrongSymbolException;
import expression.exceptions.Replacer;

public class Source {
    private String source;
    private int pos = 0;
    private String current;
    private boolean wordParsed = false;

    public Source (String source) {
        source = source.replace("#", "<wrong_symbol>");
        this.source = source + "#"; // '#' end symbol
        current = source.charAt(0) + "";
    }
    public char next() {
        if (pos != source.length() - 1) {
            pos++;
        }
        current = source.charAt(pos) + "";
        wordParsed = false;
        return source.charAt(pos);
    }
    public String nextWord() throws ParsingException {
        next();
        skipWhitespaces();
        return parseCurrentWord();
    }
    public int getPos() {
        return pos;
    }

    public String parseCurrentWord() throws ParsingException {
        if (wordParsed) {
            return current;
        }
        wordParsed = true;
        if (current() == '<') {
            check("<<");
            current = "<<";
            return current;
        } else if (current() == '>') {
            check(">>");
            current = ">>";
            return current;
        } else if (current() == 'l') {
            check("log2");
            if (Character.isDigit(source.charAt(pos + 1)) ||
                    source.charAt(pos + 1) == 'x' ||
                    source.charAt(pos + 1) == 'y' ||
                    source.charAt(pos + 1) == 'z') {
                throw new WrongSymbolException("log2", source.charAt(pos + 1) + "", getPos());
            }
            current = "log2";
            return current;
        } else if (current() == 'p') {
            check("pow2");
            if (Character.isDigit(source.charAt(pos + 1)) ||
                    source.charAt(pos + 1) == 'x' ||
                    source.charAt(pos + 1) == 'y' ||
                    source.charAt(pos + 1) == 'z') {
                throw new WrongSymbolException("pow2", source.charAt(pos + 1) + "", getPos());
            }
            current = "pow2";
            return current;
        }  else if (current() == 'a') {
            check("abs");
            current = "abs";
            return current;
        } else if (current() == 's') {
            check("square");
            current = "square";
            return current;
        } else if (current() == '+') {
            check("+");
            current = "+";
            return current;
        } else if (current() == '-') {
            check("-");
            current = "-";
            return current;
        } else if (current() == '*') {
            if (source.charAt(pos + 1) == '*') {
                check("**");
                current = "**";
            } else {
                check("*");
                current = "*";
            }
            return current;
        } else if (current() == '/') {
            if (source.charAt(pos + 1) == '/') {
                check("//");
                current = "//";
            } else {
                check("/");
                current = "/";
            }
            return current;
        } else {
            current = current() + "";
            return current;
        }
    }

    public String currentWord() {
        return current;
    }
    public char current() {
        return current.charAt(0);
    }

    public String fromPos() {
        return source.substring(pos, source.length() - 1);
    }
    void skipWhitespaces() {
        if (!Character.isWhitespace(current())) {
            return;
        }
        while (Character.isWhitespace(next()));
    }
        void check(String comp) throws ParsingException {
            for (int i = 0; i < comp.length(); i++) {
                StringBuilder ans = new StringBuilder();
                ans.append(current());
                if (comp.charAt(i) != current()) {
                    throw new ParsingException(comp + " expected, but " + Replacer.replace(ans + "") + " has found");
                }
                if (i != comp.length() - 1) {
                    next();
                }
            }
            wordParsed = true;
        }

}
