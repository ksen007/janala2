package janala.interpreters;

/** A concrete value associated with a symbolic or-constraint */
public class SymbolicOrValue extends ObjectValue {
  public final boolean concrete;
  public final SymbolicOrConstraint symbolic;

  public SymbolicOrValue(boolean concrete, SymbolicOrConstraint symbolic) {
    super(100, -1);
    this.concrete = concrete;
    this.symbolic = symbolic;
  }
}
