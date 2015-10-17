package janala.interpreters;

import java.util.LinkedList;
import java.util.Map;

public final class SymbolicAndConstraint extends Constraint {
  public LinkedList<Constraint> constraints;

  public SymbolicAndConstraint(Constraint c) {
    constraints = new LinkedList<Constraint>();
    if (c != null) {
      constraints.add(c);
    }
  }

  public SymbolicAndConstraint(LinkedList<Constraint> cons) {
    constraints = new LinkedList<Constraint>();
    constraints.addAll(cons);
  }

  private SymbolicAndConstraint() {
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }

    if (o instanceof SymbolicAndConstraint) {
      SymbolicAndConstraint other = (SymbolicAndConstraint) o;
      return (constraints.equals(other.constraints));
    } else {
      return false;
    }
  }

  public SymbolicAndConstraint AND(Constraint c) {
    if (c != null) {
      SymbolicAndConstraint ret = new SymbolicAndConstraint(constraints);
      ret.constraints.add(c);
      return ret;
    } else {
      return this;
    }
  }

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicAnd(this);
  }

  @Override
  public Constraint not() {
    return new SymbolicNotConstraint(this);
  }

  @Override
  public Constraint substitute(Map<String, Long> assignments) {
    LinkedList<Constraint> tmp = new LinkedList<Constraint>();
    Constraint c2;
    if (constraints.isEmpty()) {
      return SymbolicTrueConstraint.instance;
    }
    for (Constraint c : constraints) {
      c2 = c.substitute(assignments);
      if (c2 == SymbolicFalseConstraint.instance) {
        return SymbolicFalseConstraint.instance;
      } else if (c2 != SymbolicTrueConstraint.instance) {
        tmp.add(c2);
      }
    }
    if (!tmp.isEmpty()) {
      SymbolicAndConstraint ret = new SymbolicAndConstraint();
      ret.constraints = tmp;
      return ret;
    } else {
      return SymbolicTrueConstraint.instance;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Constraint c : constraints) {
      if (first) {
        first = false;
      } else {
        sb.append(" && ");
      }
      sb.append("(");
      sb.append(c);
      sb.append(")");
    }
    return sb.toString();
  }
}
