package janala.solvers;

import janala.Main;
import janala.instrument.Coverage;

import java.util.List;
import java.util.LinkedList;

public class AbstractRefineStrategy implements Strategy {
  @Override
  public int solve(List<Element> history, int historySize, History solver) {
    int oldBeginIndex = findUnsatBeginScopeIndex(history, historySize);
    int beginIndex = oldBeginIndex;
    if (Main.skipPath) {
      beginIndex = -1;
    }
    int endIndex = findMatchingEndScopeIndex(history, historySize, beginIndex);
    if (oldBeginIndex == -1 && !Main.skipPath) {
      Coverage.instance.commitBranches(false);
      Main.setRealInput(true);
    } else {
      Main.setRealInput(false);
    }
    int ret = searchWithIfPossibleAssert(history, beginIndex, endIndex, historySize, solver);
    while (ret == -1) {
      if (beginIndex == -1) {
        return ret;
      }
      beginIndex = findPreviousBeginScopeIndex(history, beginIndex);
      endIndex = findMatchingEndScopeIndex(history, historySize, beginIndex);
      ret = searchWithIfPossibleAssert(history, beginIndex, endIndex, historySize, solver);
    }
    return ret;
  }

  private int findPreviousBeginScopeIndex(List<Element> history, int beginScopeIndex) {
    int ret = -1; // Assume
    for (int i = 0; i <= beginScopeIndex; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (i == beginScopeIndex) {
          return ret;
        }
        if (((MethodElement) tmp).isBegin) {
          ret = i;
        }
      }
    }
    throw new RuntimeException(
        "Should not reach here beginScopeIndex =" + beginScopeIndex + " history " + history);
  }

  private int findNextBeginScopeIndex(List<Element> history, int start, int end) {
    for (int i = start + 1; i < end; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (((MethodElement) tmp).isBegin) {
          return i;
        } else {
          return -1;
        }
      }
    }
    return -1;
  }

  private int findMatchingEndScopeIndex(List<Element> history, int historySize, int beginScopeIndex) {
    int scopeIdx = 0;
    for (int i = beginScopeIndex + 1; i < historySize; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (((MethodElement) tmp).isBegin) {
          scopeIdx++;
        } else {
          if (scopeIdx == 0) {
            return i;
          } else {
            scopeIdx--;
          }
        }
      }
    }
    return historySize;
  }

  class RemovalPair {
    final int b;
    final int e;

    RemovalPair(int b, int e) {
      this.b = b;
      this.e = e;
    }
  }

  private List<RemovalPair> findRanges(List<Element> history, int from, int to) {
    List<RemovalPair> toBeRemovedRanges = new LinkedList<RemovalPair>();
    
    int bi = findNextBeginScopeIndex(history, from, to);
    while (bi != -1) {
      int ei = findMatchingEndScopeIndex(history, to, bi);
      toBeRemovedRanges.add(new RemovalPair(bi, ei));
      bi = findNextBeginScopeIndex(history, ei, to);
    }
    return toBeRemovedRanges;
  }
  
  private void removeElements(
      List<Element> history, int low, int high, int idx, int historySize) {
    
    List<RemovalPair> toRemove = findRanges(history, low, idx);
    toRemove.add(new RemovalPair(idx, high));
    toRemove.addAll(findRanges(history, high, historySize));
    
    for (int i = toRemove.size() - 1; i >= 0; i--) {
      RemovalPair pair = toRemove.get(i);
      for (int j = pair.e - 1; j > pair.b; j--) {
        history.remove(j);
      }
    }
  }

  private int findUnsatBeginScopeIndex(List<Element> history, int historySize) {
    for (int i = 0; i < historySize; i++) {
      Element tmp = history.get(i);
      if (tmp.isInvalidScopeBegin()) {
        return i;
      }
    }
    return -1;
  }

  public int searchWithIfPossibleAssert(
      List<Element> history, int low, int high, int historySize, History solver) {
    int to, from = low, ret;

    for (to = low + 1; to < high; to++) {
      Element tmp = history.get(to);
      BranchElement current;
      if (tmp instanceof BranchElement) {
        current = (BranchElement) tmp;
        if (current.isForceTruth) {
          if (!current.getBranch()) {
            ret = dfs(history, from, to + 1, high, historySize, solver);
            if (ret != -1) {
              // Found a solution to satisfy the ForceTruth
              return ret;
            }
          }
          // Do not solve for any path that is no deeper than the 
          // ForceTruth
          from = to;
        }
      }
    }

    return dfs(history, from, to, high, historySize, solver);
  }

  private int dfs(List<Element> history, int low, int start, int high, int historySize, History solver) {
    LinkedList<Integer> indices = new LinkedList<Integer>();
    int skip = 0;
    for (int i = start - 1; i > low; i--) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (((MethodElement) tmp).isBegin) {
          skip++;
        } else {
          skip--;
        }
      }
      if (tmp instanceof BranchElement && skip == 0) {
        indices.addLast(i);
      }
    }
    
    for (int i : indices) {
      Element tmp = history.get(i);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (!current.getDone() && current.pathConstraintIndex != -1) {
          if (solver.solveAt(low, i)) {
            current.setDone(true);
            current.setBranch(!current.getBranch());
            removeElements(history, low, high, i, historySize);
            return Integer.MAX_VALUE;
          }
        }
      }
    }
    return -1;
  }
}
