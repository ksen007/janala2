package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class ObjectValueTest {
  @Test
  void testConstruction() {
    ObjectValue v1 = new ObjectValue(1)
    assertEquals(-1, v1.concrete)

    ObjectValue v2 = new ObjectValue(0, 1)
    assertEquals(1, v2.getConcrete())
  }

  @Test
  void testIF_ACMPEQ() {
    ObjectValue v1 = new ObjectValue(1)
    ObjectValue v2 = new ObjectValue(v1, null)
    assertEquals(new IntValue(1), v1.IF_ACMPNE(v2))
    assertEquals(new IntValue(1), v1.IF_ACMPEQ(v1))
  }

  @Test
  void testIF_NULL() {
    ObjectValue v1 = new ObjectValue(1, 0)
    assertEquals(new IntValue(1), v1.IFNULL())
    ObjectValue v2 = new ObjectValue(1)
    assertEquals(new IntValue(1), v2.IFNONNULL())
  }

  @Test
  void testField() {
    ObjectValue v = new ObjectValue(1)
    v.setAddress(10)
    def x = new IntValue(1)
    v.setField(0, x)
    def y = v.getField(0)
    assertEquals(x, y)
  }
}