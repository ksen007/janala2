package janala.interpreters

import static org.junit.Assert.assertEquals

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class LongObjectValueTest {
	@Test
  void testInit() {
    LongObjectValue x = new LongObjectValue()
    Value[] b = new Value[1]
    b[0] = new LongValue(1L)
    x.invokeMethod("<init>", b, null)

    assertEquals(new LongValue(1L), x.longValue)

    x = new LongObjectValue()
    b[0] = new StringValue("1", 10)
    x.invokeMethod("<init>", b, null)

    assertEquals(new LongValue(1L), x.longValue)
  }

  @Test
  void testIntValue() {
    LongObjectValue x = new LongObjectValue(new LongValue(1L), 20)
    Value y = x.invokeMethod("intValue", null, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testLongValue() {
    LongObjectValue x = new LongObjectValue(new LongValue(1L), 20)
    Value y = x.invokeMethod("longValue", null, null)
    assertEquals(new LongValue(1L), y)
  }

  @Test
  void testShortValue() {
    LongObjectValue x = new LongObjectValue(new LongValue(1L), 20)
    Value y = x.invokeMethod("shortValue", null, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testByteValue() {
    LongObjectValue x = new LongObjectValue(new LongValue(1L), 20)
    Value y = x.invokeMethod("byteValue", null, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testEquals() {
    LongObjectValue x = new LongObjectValue(new LongValue(1L), 20)
    Value[] b = new Value[1]
    b[0] = x
    Value y = x.invokeMethod("equals", b, null)
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testCompareTo() {
    LongObjectValue x = new LongObjectValue(new LongValue(1L), 20)
    Value[] b = new Value[1]
    b[0] = x
    Value y = x.invokeMethod("compareTo", b, null)
    assertEquals(new IntValue(0), y)

    b[0] = new LongObjectValue(new LongValue(0L), 20)
    y = x.invokeMethod("compareTo", b, null)
    assertEquals(new IntValue(1), y)

    b[0] = new LongObjectValue(new LongValue(3L), 20)
    y = x.invokeMethod("compareTo", b, null)
    assertEquals(new IntValue(-1), y)
  }
}