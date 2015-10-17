package janala.interpreters;

import janala.solvers.CVC4Solver.CONSTRAINT_TYPE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;

public class SymbolicStringPredicate extends Constraint {

  public static enum STRING_COMPARISON_OPS {
    EQ,
    NE,
    IN,
    NOTIN
  };

  private final STRING_COMPARISON_OPS op;
  public STRING_COMPARISON_OPS getOp() { return op; }

  private final Object left;
  private final Object right;

  public SymbolicStringPredicate(STRING_COMPARISON_OPS op, Object left, Object right) {
    this.op = op;
    this.left = left;
    this.right = right;
  }

  public SymbolicStringPredicate(SymbolicStringPredicate other) {
    this.op = other.op;
    this.left = other.left;
    this.right = other.right;
  }

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicStringPredicate(this);
  }

  @Override
  public Constraint not() {
    SymbolicStringPredicate ret = new SymbolicStringPredicate(this);
    STRING_COMPARISON_OPS retOp = STRING_COMPARISON_OPS.NE;
    switch (this.op) {
      case EQ:
        retOp = STRING_COMPARISON_OPS.NE;
        break;
      case NE:
        retOp = STRING_COMPARISON_OPS.EQ;
        break;
      case IN:
        retOp = STRING_COMPARISON_OPS.NOTIN;
        break;
      case NOTIN:
        retOp = STRING_COMPARISON_OPS.IN;
        break;
    }
    return new SymbolicStringPredicate(retOp, left, right);
  }

  @Override
  public Constraint substitute(Map<String, Long> assignments) {
    return this;
  }

  public Constraint substitute(ArrayList<Value> assignments) {
    return this;
  }

  private String stringfy(Object s) {
    if (s instanceof String) {
      return "\"" + s + "\"";
    } else if (s == null) {
      return "null";
    } else {
      return s.toString();
    } 
  }

  public String toString() {
    switch (this.op) {
      case EQ:
        return stringfy(this.left) + " == " + stringfy(this.right);
      case NE:
        return stringfy(this.left) + " != " + stringfy(this.right);
      case IN:
        return stringfy(this.left) + " regexin " + stringfy(this.right);
      case NOTIN:
        return stringfy(this.left) + " regexnotin " + stringfy(this.right);
    }
    throw new RuntimeException("Not implemented");
  }

  private static class ExprAt {
    final boolean isSymbolic;
    final String prefix;
    final int symOrVal;

    ExprAt(boolean symbolic, String prefix, int symOrVal) {
      isSymbolic = symbolic;
      this.prefix = prefix;
      this.symOrVal = symOrVal;
    }
  }

  private SymOrInt exprAt(Object sExpr, int i, Set<String> freeVars, 
    Map<String, Long> assignments) {
    if (sExpr instanceof String) {
      return new SymOrInt(((String) sExpr).charAt(i));
    } else {
      SymbolicStringExpression tmp = (SymbolicStringExpression) sExpr;
      return tmp.getExprAt(i, freeVars, assignments);
    }
  }

  private Constraint getStringEqualityFormula(
      Object left,
      Object right,
      long length,
      Set<String> freeVars,
      Map<String, Long> assignments) {
    SymbolicAndConstraint and = null;

    if (length <= 0) {
      return SymbolicTrueConstraint.instance;
    }
    for (int i = 0; i < length; i++) {
      SymOrInt e1 = exprAt(left, i, freeVars, assignments);
      SymOrInt e2 = exprAt(right, i, freeVars, assignments);
      Constraint c;
      c = new SymbolicIntCompareConstraint(e1, e2, 
        COMPARISON_OPS.EQ);

      if (i != 0) {
        and = and.AND(c);
      } else {
        and = new SymbolicAndConstraint(c);
      }
    }
    return and;
  }

  private IntValue getLength(Object s) {
    return  (s instanceof String)
            ? new IntValue(((String) s).length())
            : ((SymbolicStringExpression) s).getField("length");
  }

  public Constraint getFormula(
      Set<String> freeVars,
      CONSTRAINT_TYPE mode,
      Map<String, Long> assignments) {
    StringBuilder sb = new StringBuilder();
    int j;
    
    IntValue s1 = getLength(left);
    IntValue s2 = getLength(right);
    long length1 = s1.substituteInLinear(assignments);
    long length2 = s2.substituteInLinear(assignments);

    if (mode == CONSTRAINT_TYPE.INT) {
      switch (this.op) {
        case EQ:
          IntValue val = s1.ISUB(s2);
          if (val.symbolic != null) {
            return val.symbolic.setop(COMPARISON_OPS.EQ);
          } else {
            if (val.getConcrete().equals(0)) {
              return SymbolicTrueConstraint.instance;
            } else {
              return SymbolicFalseConstraint.instance;
            }
          }
          
        case NE:
          // This quick check is a little confusing. 
          // Essentially it checks that if left and right size are nonempty strings.
          
          SymbolicInt int1 =
              s1.symbolic != null ? s1.symbolic.setop(COMPARISON_OPS.GT) : null;
          SymbolicInt int2 =
              s2.symbolic != null ? s2.symbolic.setop(COMPARISON_OPS.GT) : null;
          if (int1 != null && int2 != null) {
            SymbolicAndConstraint ret = new SymbolicAndConstraint(int1);
            return ret.AND(int2);
          } else if (int1 != null) {
            return int1;
          } else if (int2 != null) {
            return int2;
          } else { 
            return SymbolicTrueConstraint.instance;
          }
        case IN:
          // @todo regex_escape
          return RegexpEncoder.getLengthFormulaString(
              (String) this.right, "x", s1.getSymbol(), true);
        case NOTIN:
          // @todo regex_escape
          return RegexpEncoder.getLengthFormulaString(
              (String) this.right, "x", s1.getSymbol(), false);
      }
    } else if (mode == CONSTRAINT_TYPE.STR) {
      switch (this.op) {
        case EQ:
          if (length1 != length2) {
            return SymbolicFalseConstraint.instance;
          } else {
            return getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments);
          }
        case NE:
          if (length1 != length2) {
            return SymbolicTrueConstraint.instance;
          } else {
            return new SymbolicNotConstraint(
                getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments));
          }
          //                    return (length1 !== length2)?"TRUE":"FALSE";
        case IN:
          for (j = 0; j < length1; j++) {
            freeVars.add("x" + this.left + "__" + j);
          }
          // @todo regex_escape
          return RegexpEncoder.getRegexpFormulaString(
              (String) this.right, "x" + this.left + "__", (int) length1);
        case NOTIN:
          for (j = 0; j < length1; j++) {
            freeVars.add("x" + this.left + "__" + j);
          }
          // @todo regex_escape
          return RegexpEncoder.getRegexpFormulaString(
              "~(" + (String) this.right + ")", "x" + this.left + "__", (int) length1);
      }
    }
    throw new RuntimeException("Unsupported type");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof SymbolicStringPredicate)) {
      return false;
    }

    SymbolicStringPredicate tmp = (SymbolicStringPredicate) o;
    if (this.op != tmp.op) {
      return false;
    }
    String s1 = stringfy(left);
    String s2 = stringfy(right);

    String s3 = stringfy(tmp.left);
    String s4 = stringfy(tmp.right);

    return (s1.equals(s3) && s2.equals(s4));
  }
}
