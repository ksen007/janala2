package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/8/12
 * Time: 12:38 PM
 */
public class OrValue {
  boolean val;

  public OrValue(boolean val) {
    this.val = val;
  }

  public boolean boolValue() {
    return val;
  }
}
