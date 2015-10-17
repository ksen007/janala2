package janala.interpreters;

import java.util.Map;

/** A contraint is essentially a predicate, i.e., a function that evaluates to True or False */
public abstract class Constraint {
  public int iid;
  public int index;

  public abstract void accept(ConstraintVisitor v);

  public abstract Constraint not();

  public abstract Constraint substitute(Map<String, Long> assignments);
}
