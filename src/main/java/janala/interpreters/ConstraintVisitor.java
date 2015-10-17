package janala.interpreters;

public interface ConstraintVisitor {
  void visitSymbolicInt(SymbolicInt c);

  void visitSymbolicOr(SymbolicOrConstraint c);

  void visitSymbolicStringPredicate(SymbolicStringPredicate c);

  void visitSymbolicAnd(SymbolicAndConstraint c);

  void visitSymbolicNot(SymbolicNotConstraint c);

  void visitSymbolicTrue(SymbolicTrueConstraint c);

  void visitSymbolicFalse(SymbolicFalseConstraint c);

  void visitSymbolicIntCompare(SymbolicIntCompareConstraint c);
}
