package janala.interpreters

import static org.junit.Assert.assertEquals

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class FrameTest {
  @Test
  void testInit() {
    Frame frame = new Frame(1)
    assertEquals(1, frame.nReturnWords)
  }

  @Test
  void testPushAndPop() {
    Frame frame = new Frame(1)
    Value value = new IntValue(1)
    frame.push(value)

    assertEquals(value, frame.pop())

    frame.push2(value)
    assertEquals(value, frame.pop2())

    frame.clear()
  }

  @Test
  void testLocals() {
    Frame frame = new Frame(0)
    Value value = new IntValue(1)

    frame.setLocal(1, value)
    assertEquals(value, frame.getLocal(1))
    assertEquals(PlaceHolder.instance, frame.getLocal(0))

    frame.setLocal2(1, value)
    assertEquals(value, frame.getLocal2(1))
    frame.addLocal(value) // This should be 3
    assertEquals(value, frame.getLocal(3))
    frame.addLocal2(value)
    assertEquals(value, frame.getLocal(4))
  }

  @Test
  void testPeek() {
    Frame frame = new Frame(0)
    def a = new IntValue(0)
    def b = new IntValue(1)
    def c = new IntValue(2)
    def d = new IntValue(4)
    frame.push(a)
    frame.push(b)
    frame.push(c)
    frame.push(d)
    assertEquals(d, frame.peek())
    assertEquals(c, frame.peek2())
    assertEquals(b, frame.peek3())
    assertEquals(a, frame.peek4())
  }
}
