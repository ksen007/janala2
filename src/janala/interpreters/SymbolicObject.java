package janala.interpreters;

import java.util.LinkedList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

class Pair<T1, T2> {
    public T1 fst;
    public T2 snd;

    Pair(T1 fst, T2 snd) {
        this.fst = fst;
        this.snd = snd;
    }
}

public class SymbolicObject {
    LinkedList<Pair<Constraint,ObjectValue>> guards;

    public SymbolicObject() {
        guards = new LinkedList<Pair<Constraint, ObjectValue>>();
    }

    public void addGuardedObjectValue(Constraint c, ObjectValue ov) {
        guards.add(new Pair<Constraint, ObjectValue>(c,ov));
    }
}
