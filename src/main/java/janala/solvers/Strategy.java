package janala.solvers;

import java.util.List;

public interface Strategy {
  public int solve(List<Element> history, int historySize, History solver);
}
