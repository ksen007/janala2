package janala.interpreters;

import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SymbolicFalseConstraint extends Constraint {
  public static final SymbolicFalseConstraint instance = new SymbolicFalseConstraint();

  public SymbolicFalseConstraint() {}

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicFalse(this);
  }

  @Override
  public Constraint not() {
    return SymbolicTrueConstraint.instance;
  }

  @Override
  public Constraint substitute(Map<String, Long> assignments) {
    return this;
  }

  @Override
  public String toString() {
    return " FALSE ";
  }
}
