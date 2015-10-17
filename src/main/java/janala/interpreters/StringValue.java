package janala.interpreters;

import janala.config.Config;
import janala.solvers.History;

public final class StringValue extends ObjectValue {
  private final String string;
  private SymbolicStringExpression symbolicExp;

  public StringValue(String string, int address) {
    super(100, address);
    this.string = string;
  }

  public StringValue(String string, SymbolicStringExpression symbolicExp) {
    super(100, -1);
    this.string = string;
    this.symbolicExp = symbolicExp;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o == this) {
      return true;
    } else if (o instanceof StringValue) {
      StringValue other = (StringValue)o;
      return (this.string == other.string ||
        this.string.equals(other.string)) && 
        (this.symbolicExp == other.symbolicExp || 
          this.symbolicExp.equals(other.symbolicExp));
    } else {
      return false;
    }
  }

  @Override
  public String getConcrete() {
    return string;
  }

  public SymbolicStringExpression getSymbolicExp() {
    return symbolicExp;
  }

  private String escapeRE(String str) {
    return str.replaceAll("([^a-zA-z0-9])", "\\\\$1");
  }

  private Value invokeEquals(Value arg) {
    if (arg instanceof StringValue) {
      StringValue other = (StringValue) arg;
      boolean result = string.equals(other.string);
      if (symbolicExp != null && other.symbolicExp != null) {
        return new IntValue(
          result ? 1 : 0,
          new SymbolicStringPredicate(
            SymbolicStringPredicate.STRING_COMPARISON_OPS.EQ, symbolicExp, other.symbolicExp));
      } else if (symbolicExp != null) {
        return new IntValue(
          result ? 1 : 0,
          new SymbolicStringPredicate(
            SymbolicStringPredicate.STRING_COMPARISON_OPS.EQ, symbolicExp, other.string));
      } else if (other.symbolicExp != null) {
        return new IntValue(
          result ? 1 : 0,
          new SymbolicStringPredicate(
            SymbolicStringPredicate.STRING_COMPARISON_OPS.EQ, string, other.symbolicExp));
      } else {
        return new IntValue(result ? 1 : 0);
      }
    } else {
      // arg is not StringValue type.
      return new IntValue(0);
    }
  }

  private Value invokeStartsWith(Value[] args) {
    if (args.length == 1) {
      if (args[0] instanceof StringValue) {
        StringValue other = (StringValue) args[0];
        boolean result = string.startsWith(other.string);
        if (symbolicExp != null) {
          return new IntValue(
            result ? 1 : 0,
            new SymbolicStringPredicate(
              SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
              symbolicExp,
              escapeRE(other.string) + ".*"));
        } else {
          return new IntValue(result ? 1 : 0);
        }
      }
    } else if (args.length == 2) {
      if (args[0] instanceof StringValue) {
        StringValue other = (StringValue) args[0];
        IntValue offset = (IntValue) args[1];
        boolean result = string.startsWith(other.string, offset.concrete);
        if (symbolicExp != null) {
          return new IntValue(
            result ? 1 : 0,
            new SymbolicStringPredicate(
              SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
              symbolicExp,
              ".{" + offset.concrete + "}" + escapeRE(other.string) + ".*"));
        } else {
          return new IntValue(result ? 1 : 0);
        }
      }
    }
    return new IntValue(0);
  }

  private Value invokeEndsWith(Value arg) {
    StringValue other = (StringValue) arg;
    boolean result = string.endsWith(other.string);
    if (symbolicExp != null) {
      return new IntValue(
        result ? 1 : 0,
        new SymbolicStringPredicate(
          SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
          symbolicExp,
          ".*" + escapeRE(other.string)));
    } else {
      return new IntValue(result ? 1 : 0);
    }
  }

  private Value invokeContains(Value arg) {
    StringValue other = (StringValue) arg;
    boolean result = string.contains(other.string);
    if (symbolicExp != null) {
      return new IntValue(
        result ? 1 : 0,
        new SymbolicStringPredicate(
          SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
          symbolicExp,
          ".*" + escapeRE(other.string) + ".*"));
    } else {
      return new IntValue(result ? 1 : 0);
    }
  }

  private Value invokeConcat(Value arg) {
    StringValue other = (StringValue) arg;
    String result = string.concat(other.string);
    if (symbolicExp != null && other.symbolicExp  != null) {
      return new StringValue(result, symbolicExp.concat(other.symbolicExp));
    } else if (symbolicExp != null) {
      return new StringValue(result, symbolicExp.concatStr(other.string));
    } else if (other.symbolicExp  != null) {
      return new StringValue(result, other.symbolicExp.concatToStr(string));
    } else {
      return new StringValue(result, null);
    }
  }

  private Value invokeMatches(Value arg) {
    StringValue other = (StringValue) arg;
    boolean result = string.matches(other.string);
    if (symbolicExp != null) {
      return new IntValue(
        result ? 1 : 0,
        new SymbolicStringPredicate(
          SymbolicStringPredicate.STRING_COMPARISON_OPS.IN, symbolicExp, other.string));
    } else {
      return new IntValue(result ? 1 : 0);
    }
  }

  private Value invokeReplace(Value from, Value to) {
    if ((from instanceof IntValue) &&
        (to instanceof IntValue)) {
      // e.g., replace('a', 'b')
      char fromChar = (char) ((IntValue) from).concrete;
      char toChar = (char) ((IntValue) to).concrete;
      // TODO(zhihan): Consider how to handle expressions.
      // As of now just use the concrete value.
      return new StringValue(string.replace(fromChar, toChar), null);
    }
    return null;
  }

  @Override
  public Value invokeMethod(String name, Value[] args, History history) {
    if (name.equals("equals") && args.length == 1) {
      return invokeEquals(args[0]);
    } else if (name.equals("startsWith")) {
      return invokeStartsWith(args);
    } else if (name.equals("endsWith") && args.length == 1) {
      return invokeEndsWith(args[0]);
    } else if (name.equals("contains") && args.length == 1) {
      return invokeContains(args[0]);
    } else if (name.equals("concat") && args.length == 1) {
      return invokeConcat(args[0]);
    } else if (name.equals("length") && (args == null || args.length == 0)) {
      int result = string.length();
      if (symbolicExp != null) {
        return symbolicExp.getField("length");
      } else {
        return new IntValue(result);
      }
    } else if (name.equals("matches") && args.length == 1) {
      return invokeMatches(args[0]);
    } else if (name.equals("replace") && args.length == 2) {
      return invokeReplace(args[0], args[1]);
    }
    return super.invokeMethod(name, args, history);
  }

  public int MAKE_SYMBOLIC(History history) {
    IntValue length = new IntValue(string.length());
    int ret = symbol;
    symbol = symbol + inc;
    symbolicExp = new SymbolicStringExpression(ret, length);
    length.MAKE_SYMBOLIC(history);

    Constraint results = length.symbolic.setop(COMPARISON_OPS.GE);
    boolean resultc = length.concrete >= 0;
    history.checkAndSetBranch(resultc, results, 0);
    if (resultc) {
      history.setLastBranchDone();
    }

    results =
        length
            .ISUB(new IntValue(Config.instance.maxStringLength))
            .symbolic
            .setop(COMPARISON_OPS.LE);
    resultc = length.concrete <= Config.instance.maxStringLength;
    history.checkAndSetBranch(resultc, results, 0);
    if (resultc) {
      history.setLastBranchDone();
    }

    return ret;
  }
}
