package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse

import org.junit.Test
import groovy.transform.CompileStatic

class FloatValueTest {
  @Test 
  void testEqual() {
    FloatValue a = new FloatValue(1.0)
    FloatValue b = new FloatValue(1.0)
    assertEquals(a, b)
    assertFalse(a.equals(null))
    assertFalse(a.equals("a"))
    assertTrue(a.equals(a))
  }

  @Test
  void testFADD() {
    FloatValue a = new FloatValue(1.0F)
    FloatValue b = new FloatValue(1.0F)
    assertEquals(new FloatValue((float)(1.0F + 1.0F)), a.FADD(b))
  }

  @Test
  void testFSUB() {
    FloatValue a = new FloatValue(1.0F)
    FloatValue b = new FloatValue(1.0F)
    assertEquals(new FloatValue((float)(1.0F - 1.0F)), a.FSUB(b))
  }

  @Test
  void testFMUL() {
    FloatValue a = new FloatValue(1.0F)
    FloatValue b = new FloatValue(1.0F)
    assertEquals(new FloatValue((float)(1.0F * 1.0F)), a.FMUL(b))
  }

  @Test
  void testFDIV() {
    FloatValue a = new FloatValue(1.0F)
    FloatValue b = new FloatValue(1.0F)
    assertEquals(new FloatValue((float)(1.0F / 1.0F)), a.FDIV(b))
  }

  @Test
  void testFREM() {
    FloatValue a = new FloatValue(5.0F)
    FloatValue b = new FloatValue(2.0F)
    assertEquals(new FloatValue((float)(5.0F % 2.0F)), a.FREM(b))
  }

  @Test
  void testFNEG() {
    FloatValue a = new FloatValue(5.0F)
    assertEquals(new FloatValue((float)(- 5.0F)), a.FNEG())
  }

  @Test
  void testFCMPL() {
    FloatValue a = new FloatValue(5.0F)
    FloatValue b = new FloatValue(1.0F)
    assertEquals(new IntValue(1), b.FCMPL(a))
    assertEquals(new IntValue(-1), a.FCMPL(b))
    assertEquals(new IntValue(0), a.FCMPL(a))
  }

  @Test
  void testFCMPG() {
    FloatValue a = new FloatValue(5.0F)
    FloatValue b = new FloatValue(1.0F)
    assertEquals(new IntValue(1), a.FCMPG(b))
    assertEquals(new IntValue(-1), b.FCMPG(a))
    assertEquals(new IntValue(0), a.FCMPG(a))
  }
}