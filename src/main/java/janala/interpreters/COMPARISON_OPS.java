package janala.interpreters;

import java.io.PrintStream;

public enum COMPARISON_OPS {
    EQ("="),
    NE("/="),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    UN("?");

    private final String str;

    @Override
    public String toString() {
      return str;
    }


    private COMPARISON_OPS(String s) {
      str = s;
    }

};
