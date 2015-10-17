package janala.interpreters;

import java.util.LinkedList;
import java.util.List;

class Pair<T1, T2> {
  public final T1 fst;
  public final T2 snd;

  Pair(T1 fst, T2 snd) {
    this.fst = fst;
    this.snd = snd;
  }
}

public class SymbolicObject {
  List<Pair<Constraint, ObjectValue>> guards;

  public SymbolicObject() {
    guards = new LinkedList<Pair<Constraint, ObjectValue>>();
  }

  public void addGuardedObjectValue(Constraint c, ObjectValue ov) {
    guards.add(new Pair<Constraint, ObjectValue>(c, ov));
  }
}
