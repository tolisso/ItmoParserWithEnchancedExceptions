package expression.parser;

public class Source {
    private String source;
    private int pos = 0;
    private String current;

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
        return source.charAt(pos);
    }
    public String nextWord() {
        next();
        skipWhitespaces();
        return parseCurrentWord();
    }

    public String parseCurrentWord() {
        if (current() == '<') {
            check("<<");
            current = "<<";
            return current;
        } else if (current() == '>') {
            check(">>");
            current = ">>";
            return current;
        } else if (current() == 'a') {
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
            check("*");
            current = "*";
            return current;
        } else if (current() == '/') {
            check("/");
            current = "/";
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
        void check(String comp) {
            for (int i = 0; i < comp.length(); i++) {
                StringBuilder ans = new StringBuilder();
                ans.append(current());
                if (comp.charAt(i) != current()) {
                    throw new RuntimeException(comp + " expected, but " + ans + " has found");
                }
                if (i != comp.length() - 1) {
                    next();
                }
            }
        }

}
