package janala.solvers;

import janala.interpreters.Constraint;
import janala.interpreters.ConstraintVisitor;

import java.util.List;

public interface Solver extends ConstraintVisitor {
  public boolean solve();
  
  public List<String> getSolution();

  public void setInputs(List<InputElement> inputs);

  public void setPathConstraint(List<Constraint> pathConstraint);

  public void setPathConstraintIndex(int pathConstraintIndex);
}
