package janala.interpreters;

public class ConstraintPrinter implements ConstraintVisitor {
  public void visitSymbolicInt(SymbolicInt c){}

  public void visitSymbolicOr(SymbolicOrConstraint c){}

  public void visitSymbolicStringPredicate(SymbolicStringPredicate c){}

  public void visitSymbolicAnd(SymbolicAndConstraint c){}

  public void visitSymbolicNot(SymbolicNotConstraint c){}

  public void visitSymbolicTrue(SymbolicTrueConstraint c){}

  public void visitSymbolicFalse(SymbolicFalseConstraint c){}

  public void visitSymbolicIntCompare(SymbolicIntCompareConstraint c){}
}