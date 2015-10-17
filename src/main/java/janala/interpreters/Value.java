package janala.interpreters;

import janala.solvers.History;

/** Abstract value of any type. Base class for the concrete values. */
public abstract class Value {
  protected static int symbol = 1;  // Number of the next symbol
  protected final static int inc = 2;

  public static void reset() {
    symbol = 1;
  }

  public int MAKE_SYMBOLIC(History history) {
    throw new RuntimeException("Cannot make " + this + " symbolic");
  }

  public Object getConcrete() {
    return null;
  }
}
