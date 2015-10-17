package example;

import static org.junit.Assert.assertEquals;

import catg.CATG;
import janala.utils.Annotations.Test;

public class BooleanExample {
  public int and(boolean x, boolean y) {
    if (x && y) {
      return 1;
    } else {
      return 0;
    }
  }

  @Test
  public void testAnd() {
    int x = and(true, true);
    assertEquals(1, x);

    x = and(false, true);
    assertEquals(0, x);
  }

  public int or(boolean x, boolean y) {
    if (x || y) {
      return 1;
    } else {
      return 0;
    }
  }

  @Test
  public void testOr() {
    int x = or(false, true);
    assertEquals(1, x);

    x = or(false, false);
    assertEquals(0, x);
  }
  
  public int not(boolean x) {
    if (x) {
      return 0;
    }
    return 1;
  }
  
  @Test
  public void testNot() {
    boolean input = CATG.readBool(false);
    int x = not(input);
    assertEquals(1, x);
  }
}
