package janala.interpreters;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.IdentityHashMap;
import java.util.List;

public class RegexpEncoder {
  public static Constraint getRegexpFormulaString(String regexp, String prefix, int length) {
    RegExp r = new RegExp(regexp);
    Automaton a = r.toAutomaton();
    length--;
    if (length == -1) {
      if (a.getInitialState().isAccept()) {
        return SymbolicTrueConstraint.instance;
      } else {
        return SymbolicFalseConstraint.instance;
      }
    } else {
      return createFormula(a, prefix, length);
    }
  }

  public static Constraint getLengthFormulaString(
      String regexp, String prefix, int sym, boolean accept) {
    RegExp r = new RegExp(regexp);
    Automaton a = r.toAutomaton();
    String example = a.getShortestExample(accept);
    if (example != null)
      return intCompare(
          prefix, sym, example.length(), COMPARISON_OPS.GE);
    else return intCompare(prefix, sym, 0, COMPARISON_OPS.GE);
  }

  public static Constraint createFormula(Automaton A, String prefix, int n) {
    State root = A.getInitialState();
    if (n == 0) {
      if (root.isAccept()) {
        return SymbolicTrueConstraint.instance;
      } else {
        return SymbolicFalseConstraint.instance;
      }
    } else {
      Constraint ret = createFormula(root, prefix, 0, n);
      return ret == null ? SymbolicFalseConstraint.instance : ret;
    }
  }

  private static Constraint intCompare(
      String prefix, int sym, int constant, COMPARISON_OPS op) {
    return new SymbolicIntCompareConstraint(new SymOrInt(prefix + sym), new SymOrInt(constant), op);
  }

  private static Constraint andFormula(Constraint f1, Constraint f2) {
    if (f1 instanceof SymbolicAndConstraint) {
      return ((SymbolicAndConstraint) f1).AND(f2);
    } else if (f2 instanceof SymbolicAndConstraint) {
      return ((SymbolicAndConstraint) f2).AND(f1);
    } else {
      return (new SymbolicAndConstraint(f1)).AND(f2);
    }
  }

  private static Constraint orFormula(Constraint f1, Constraint f2) {
    if (f1 instanceof SymbolicOrConstraint) {
      return ((SymbolicOrConstraint) f1).OR(f2);
    } else if (f2 instanceof SymbolicOrConstraint) {
      return ((SymbolicOrConstraint) f2).OR(f1);
    } else {
      return (new SymbolicOrConstraint(f1)).OR(f2);
    }
  }

  public static Constraint createFormula(State root, String prefix, int i, int n) {
    IdentityHashMap<State, Constraint> ret = new IdentityHashMap<State, Constraint>();
    Constraint collect = null;

    List<Transition> transitions = root.getSortedTransitions(false);
    for (Transition transition : transitions) {
      State next = transition.getDest();
      Constraint tmp1 =
          andFormula(
              intCompare(
                  prefix, i, transition.getMin(), COMPARISON_OPS.GE),
              intCompare(
                  prefix, i, transition.getMax(), COMPARISON_OPS.LE));
      Constraint tmp2 = ret.get(next);
      if (tmp2 != null) {
        Constraint tmp3 = orFormula(tmp2, tmp1);
        ret.put(next, tmp3);
      } else {
        ret.put(next, tmp1);
      }
    }
    if (i < n) {
      for (State next : ret.keySet()) {
        Constraint suffix = createFormula(next, prefix, i + 1, n);
        if (suffix != null) {
          Constraint tmp4 = andFormula(ret.get(next), suffix);
          if (collect == null) {
            collect = tmp4;
          } else {
            collect = orFormula(collect, tmp4);
          }
        }
      }
    } else {
      for (State next : ret.keySet()) {
        if (next.isAccept()) {
          if (collect == null) {
            collect = ret.get(next);
          } else {
            collect = orFormula(collect, ret.get(next));
          }
        }
      }
    }
    return collect;
  }
}
