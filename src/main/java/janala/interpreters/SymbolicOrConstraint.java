package janala.interpreters;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SymbolicOrConstraint extends Constraint {
  public final List<Constraint> constraints;

  private SymbolicOrConstraint(List<Constraint> list) {
    constraints = new LinkedList<Constraint>();
    constraints.addAll(list);
  }

  public SymbolicOrConstraint(Constraint c) {
    constraints = new LinkedList<Constraint>();
    if (c != null) {
      constraints.add(c);
    }
  }

  private SymbolicOrConstraint(SymbolicOrConstraint c) {
    this(c.constraints);
  }

  public SymbolicOrConstraint OR(Constraint c) {
    if (c != null) {
      SymbolicOrConstraint ret = new SymbolicOrConstraint(constraints);
      ret.constraints.add(c);
      return ret;
    } else {
      return this;
    }
  }

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicOr(this);
  }

  @Override
  public Constraint not() {
    return new SymbolicNotConstraint(this);
  }

  @Override
  public Constraint substitute(Map<String, Long> assignments) {
    
    if (constraints.isEmpty()) {
      return SymbolicTrueConstraint.instance;
    }

    LinkedList<Constraint> tmp = new LinkedList<Constraint>();
    for (Constraint c : constraints) {
      Constraint c2 = c.substitute(assignments);
      if (c2 == SymbolicTrueConstraint.instance) {
        return SymbolicTrueConstraint.instance;
      } else if (c2 != SymbolicFalseConstraint.instance) {
        tmp.add(c2);
      }
    }
    if (!tmp.isEmpty()) {
      return new SymbolicOrConstraint(tmp);
    } else {
      return SymbolicFalseConstraint.instance;
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
        sb.append(" || ");
      }
      sb.append("(");
      sb.append(c);
      sb.append(")");
    }
    return sb.toString();
  }
}
