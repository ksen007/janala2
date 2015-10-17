package janala.interpreters;

/** A Symbol name or a concrete int value */

// For symbolic name, it must be of the format 'x1', 'x2' where the number is the 
// index in the affine form. See SymbolicInt.
public final class SymOrInt {
  private final String sym;
  private final long constant;
  public final boolean isSym;

  public SymOrInt(String sym) {
    this.sym = sym;
    constant = 0;
    isSym = true;
  }

  public SymOrInt(long constant) {
    this.constant = constant;
    isSym = false;
    sym = null;
  }

  public String getSym() {
    assert(isSym);
    return sym;
  }

  public long getConstant() {
    assert(!isSym);
    return constant;
  }

  public String toString() {
    if (sym != null) {
      return sym;
    } else {
      return "" + constant;
    }
  }

  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o == this) {
      return true;
    } else if (o instanceof SymOrInt) {
      SymOrInt other = (SymOrInt) o;
      return (sym == other.sym || sym.equals(other.sym)) 
        && (constant == other.constant) 
        && (isSym == other.isSym);
    } else {
      return false;
    }
  }
}
