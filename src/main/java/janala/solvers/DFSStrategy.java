package janala.solvers;

import java.util.List;

public class DFSStrategy implements Strategy {
  private int deepestForceTruth; // Index of the deepest forced truth (that is, a ForceTruth branch
  // with a true value.

 
  @Override
  public int solve(List<Element> history, int historySize, History solver) {
    int result = solveForceTruth(history, historySize, solver);
    if (result > 0) {
      return result;
    }
    return solveDeepest(history, historySize - 1, deepestForceTruth, solver);
  }
  
  private int solveForceTruth(List<Element> history, int historySize, History solver) {
    deepestForceTruth = -1; // unknown
    
    for (int j = 0; j < historySize; j++) {
      Element tmp = history.get(j);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (current.isForceTruth) {
          if (!current.getBranch()) {
            int ret = solveDeepest(history, j, deepestForceTruth, solver);
            if (ret != -1) {
              // Found a solution that leads to the ForceTruth
              return ret;
            }
          }
          // There is no need to solve any path less than the ForceTruth
          deepestForceTruth = j;
        }
      }
    }
    return -1;
  }

  private int solveDeepest(List<Element> history, int from, int to, History solver) {
    for (int i = from; i > to; i--) {
      Element tmp = history.get(i);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (!current.getDone() && current.pathConstraintIndex != -1) {
          if (solver.solveAt(current.pathConstraintIndex)) {
            return i;
          }
        }
      }
    }
    return -1;
  }
}
