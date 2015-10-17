package janala.interpreters;

import java.util.Map;


public final class SymbolicIntCompareConstraint extends Constraint {

  public final SymOrInt left;
  public final SymOrInt right;
  public final COMPARISON_OPS op;

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o == this) {
      return true;
    } else if (o instanceof SymbolicIntCompareConstraint) {
      SymbolicIntCompareConstraint other = (SymbolicIntCompareConstraint) o;
      return left.equals(other.left) && right.equals(other.right) &&
        (op == other.op);
    } else {
      return false;
    }
  }

  public SymbolicIntCompareConstraint(SymOrInt left, SymOrInt right, COMPARISON_OPS op) {
    this.left = left;
    this.right = right;
    this.op = op;
  }

  public SymbolicIntCompareConstraint(SymbolicIntCompareConstraint from) {
    this.left = from.left;
    this.right = from.right;
    this.op = from.op;
  }

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicIntCompare(this);
  }

  @Override
  public Constraint not() {
    COMPARISON_OPS retOp = op;
    if (op == COMPARISON_OPS.EQ) {
      retOp = COMPARISON_OPS.NE;
    } else if (op == COMPARISON_OPS.NE) {
      retOp = COMPARISON_OPS.EQ;
    } else if (op == COMPARISON_OPS.GT) {
      retOp = COMPARISON_OPS.LE;
    } else if (op == COMPARISON_OPS.GE) {
      retOp = COMPARISON_OPS.LT;
    } else if (op == COMPARISON_OPS.LT) {
      retOp = COMPARISON_OPS.GE;
    } else if (op == COMPARISON_OPS.LE) {
      retOp = COMPARISON_OPS.GT;
    } 
    return new SymbolicIntCompareConstraint(left, right, retOp);
  }

  public Constraint substitute(Map<String, Long> assignments) {
    long val;
    SymOrInt tmp1, tmp2;
    Constraint ret2 = null;

    if (left.isSym && assignments.containsKey(left.getSym())) {
      tmp1 = new SymOrInt(assignments.get(left.getSym()));
    } else {
      tmp1 = left;
    }
    if (right.isSym && assignments.containsKey(right.getSym())) {
      tmp2 = new SymOrInt(assignments.get(right.getSym()));
    } else {
      tmp2 = right;
    }

    if (!tmp1.isSym && !tmp2.isSym) {
      val = tmp1.getConstant() - tmp2.getConstant();
    } else {
      return new SymbolicIntCompareConstraint(tmp1, tmp2, this.op);
    }

    if (this.op == COMPARISON_OPS.EQ) {
      ret2 = (val == 0) ? SymbolicTrueConstraint.instance : SymbolicFalseConstraint.instance;
    } else if (this.op == COMPARISON_OPS.NE) {
      ret2 = (val != 0) ? SymbolicTrueConstraint.instance : SymbolicFalseConstraint.instance;
    } else if (this.op == COMPARISON_OPS.LE) {
      ret2 = (val <= 0) ? SymbolicTrueConstraint.instance : SymbolicFalseConstraint.instance;
    } else if (this.op == COMPARISON_OPS.LT) {
      ret2 = (val < 0) ? SymbolicTrueConstraint.instance : SymbolicFalseConstraint.instance;
    } else if (this.op == COMPARISON_OPS.GE) {
      ret2 = (val >= 0) ? SymbolicTrueConstraint.instance : SymbolicFalseConstraint.instance;
    } else if (this.op == COMPARISON_OPS.GT) {
      ret2 = (val > 0) ? SymbolicTrueConstraint.instance : SymbolicFalseConstraint.instance;
    }
    return ret2;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(left);
    sb.append('-');
    sb.append(right);
    if (op == COMPARISON_OPS.EQ) {
      sb.append("==");
      sb.append('0');
    } else if (op == COMPARISON_OPS.NE) {
      sb.append("!=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.LE) {
      sb.append("<=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.LT) {
      sb.append("<");
      sb.append('0');
    } else if (op == COMPARISON_OPS.GE) {
      sb.append(">=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.GT) {
      sb.append(">");
      sb.append('0');
    }
    return sb.toString();
  }
}
