package janala.solvers;

public class MethodElement extends Element {
  final boolean isBegin;
  final boolean isAbstracted;
  final int iid;

  @Override
  public int getIid() { return iid; }

  boolean isValidExpansion;

  public MethodElement(boolean isBegin, int iid) {
    this.isBegin = isBegin;
    this.isAbstracted = true;
    this.isValidExpansion = true;
    this.iid = iid;
  }

  @Override
  public boolean isInvalidScopeBegin() {
    return isBegin && !isValidExpansion;
  }

  @Override
  public String toString() {
    return "MethodElement{"
        + "isBegin="
        + isBegin
        + ", isAbstracted="
        + isAbstracted
        + ", isValidExpansion="
        + isValidExpansion
        + ", iid="
        + iid
        + '}';
  }
}
