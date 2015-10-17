package database.table.internals;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class ArrayBasedTuple {
  public Object[] array;
  private int hc;
  private boolean isHcInitialized = false;

  public ArrayBasedTuple(Object[] array) {
    this.array = array;
  }

  @Override
  public int hashCode() {
    if (!isHcInitialized) {
      hc = 0;
      for (int i = 0; i < array.length; i++) {
        Object o = array[i];
        hc += o.hashCode();
      }
      isHcInitialized = true;
    }
    return hc;
  }

  public void invalidateHashCode() {
    isHcInitialized = false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ArrayBasedTuple)) return false;
    ArrayBasedTuple other = (ArrayBasedTuple) o;
    if (array == other.array) return true;
    if (other.array == null || array == null) return false;
    if (other.array.length != array.length) return false;
    for (int i = 0; i < array.length; i++) {
      if (!array[i].equals(other.array[i])) return false;
    }
    return true;
  }
}
