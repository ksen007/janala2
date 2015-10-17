package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse

import org.junit.Test
import groovy.transform.CompileStatic

class DoubleValueTest {
  @Test 
  void testEqual() {
    DoubleValue a = new DoubleValue(1.0)
    DoubleValue b = new DoubleValue(1.0)
    assertEquals(a, b)
    assertFalse(a.equals(null))
    assertFalse(a.equals("a"))
    assertTrue(a.equals(a))
  }

  @Test
  void testDADD() {
    DoubleValue a = new DoubleValue(1.0D)
    DoubleValue b = new DoubleValue(1.0D)
    assertEquals(new DoubleValue((Double)(1.0D + 1.0D)), a.DADD(b))
  }

  @Test
  void testDSUB() {
    DoubleValue a = new DoubleValue(1.0D)
    DoubleValue b = new DoubleValue(1.0D)
    assertEquals(new DoubleValue((Double)(1.0D - 1.0D)), a.DSUB(b))
  }

  @Test
  void testDMUL() {
    DoubleValue a = new DoubleValue(1.0D)
    DoubleValue b = new DoubleValue(1.0D)
    assertEquals(new DoubleValue((Double)(1.0D * 1.0D)), a.DMUL(b))
  }

  @Test
  void testDDIV() {
    DoubleValue a = new DoubleValue(1.0D)
    DoubleValue b = new DoubleValue(1.0D)
    assertEquals(new DoubleValue((Double)(1.0D / 1.0D)), a.DDIV(b))
  }

  @Test
  void testDREM() {
    DoubleValue a = new DoubleValue(5.0D)
    DoubleValue b = new DoubleValue(2.0D)
    assertEquals(new DoubleValue((Double)(5.0D % 2.0D)), a.DREM(b))
  }

  @Test
  void testDNEG() {
    DoubleValue a = new DoubleValue(5.0D)
    assertEquals(new DoubleValue((Double)(- 5.0D)), a.DNEG())
  }

  @Test
  void testDCMPL() {
    DoubleValue a = new DoubleValue(5.0D)
    DoubleValue b = new DoubleValue(1.0D)
    assertEquals(new IntValue(1), b.DCMPL(a))
    assertEquals(new IntValue(-1), a.DCMPL(b))
    assertEquals(new IntValue(0), a.DCMPL(a))
  }

  @Test
  void testDCMPG() {
    DoubleValue a = new DoubleValue(5.0D)
    DoubleValue b = new DoubleValue(1.0D)
    assertEquals(new IntValue(1), a.DCMPG(b))
    assertEquals(new IntValue(-1), b.DCMPG(a))
    assertEquals(new IntValue(0), a.DCMPG(a))
  }
}