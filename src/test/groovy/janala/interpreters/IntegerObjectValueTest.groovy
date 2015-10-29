package janala.interpreters

import static org.junit.Assert.assertEquals

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class IntegerObjectValueTest {
	@Test
  void testInit() {
    IntegerObjectValue x = new IntegerObjectValue()
    Value[] b = new Value[1]
    b[0] = new IntValue(1)
    x.invokeMethod("<init>", b, null)

    assertEquals(new IntValue(1), x.intValue)

    x = new IntegerObjectValue()
    b[0] = new StringValue("1", 10)
    x.invokeMethod("<init>", b, null)

    assertEquals(new IntValue(1), x.intValue)
  }

  @Test
  void testIntValue() {
    IntegerObjectValue x = new IntegerObjectValue(new IntValue(1), 20)
    Value y = x.invokeMethod("intValue", null, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testLongValue() {
    IntegerObjectValue x = new IntegerObjectValue(new IntValue(1), 20)
    Value y = x.invokeMethod("longValue", null, null)
    assertEquals(new LongValue(1L), y)
  }

  @Test
  void testShortValue() {
    IntegerObjectValue x = new IntegerObjectValue(new IntValue(1), 20)
    Value y = x.invokeMethod("shortValue", null, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testByteValue() {
    IntegerObjectValue x = new IntegerObjectValue(new IntValue(1), 20)
    Value y = x.invokeMethod("byteValue", null, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testEquals() {
    IntegerObjectValue x = new IntegerObjectValue(new IntValue(1), 20)
    Value[] b = new Value[1]
    b[0] = x
    Value y = x.invokeMethod("equals", b, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testCompareTo() {
    IntegerObjectValue x = new IntegerObjectValue(new IntValue(1), 20)
    Value[] b = new Value[1]
    b[0] = x
    Value y = x.invokeMethod("compareTo", b, null)
    assertEquals(new IntValue(0), y)

    b[0] = new IntegerObjectValue(new IntValue(0), 20)
    y = x.invokeMethod("compareTo", b, null)
    assertEquals(new IntValue(1), y)

    b[0] = new IntegerObjectValue(new IntValue(3), 20)
    y = x.invokeMethod("compareTo", b, null)
    assertEquals(new IntValue(-1), y)
  }
}