package janala.interpreters;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SymbolicStringVar {
  int sym;
  IntValue length;

  public SymbolicStringVar(int sym, IntValue length) {
    this.sym = sym;
    this.length = length;
  }

  @Override
  public String toString() {
    return this.sym + "";
  }

  public SymbolicStringVar substitute(ArrayList<Value> assignments) {
    return this;
  }

  public IntValue getField(String offset) {
    if (offset.equals("length")) {
      return this.length;
    }
    return null;
  }
}
