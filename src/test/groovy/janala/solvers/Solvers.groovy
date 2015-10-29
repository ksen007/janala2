package janala.solvers

import janala.interpreters.Constraint
import janala.interpreters.SymbolicInt
import janala.interpreters.SymbolicOrConstraint
import janala.interpreters.SymbolicStringPredicate
import janala.interpreters.SymbolicAndConstraint
import janala.interpreters.SymbolicNotConstraint
import janala.interpreters.SymbolicTrueConstraint
import janala.interpreters.SymbolicFalseConstraint
import janala.interpreters.SymbolicIntCompareConstraint

class NoopSolver implements Solver {
  public boolean solve() { 1 }

  public void setInputs(List<InputElement> inputs) {}

  public void setPathConstraint(List<Constraint> pathConstraint) {}

  public void setPathConstraintIndex(int pathConstraintIndex) {}

  void visitSymbolicInt(SymbolicInt c) {}

  void visitSymbolicOr(SymbolicOrConstraint c) {}

  void visitSymbolicStringPredicate(SymbolicStringPredicate c) {}

  void visitSymbolicAnd(SymbolicAndConstraint c) {}

  void visitSymbolicNot(SymbolicNotConstraint c) {}

  void visitSymbolicTrue(SymbolicTrueConstraint c) {}

  void visitSymbolicFalse(SymbolicFalseConstraint c) {}

  void visitSymbolicIntCompare(SymbolicIntCompareConstraint c) {}
  
  List<String> getSolution() { return null; }
}