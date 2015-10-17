package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.anyInt
import static org.mockito.Mockito.eq

import janala.config.Config
import janala.solvers.History

import org.junit.Before
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class StaticInvocationTest {
  StaticInvocation context
  Config config
  History history

  @Before
  void setup() {
    config = new Config()
    context = new StaticInvocation(config)
    history = mock(History.class)
  }

  @Test
  void testIntegerValueOf() {
    Value[] x = new Value[1]
    x[0] = new IntValue(1)
    Value v = context.invokeMethod(0, "java/lang/Integer", "valueOf", x, null)
    assertEquals(new IntegerObjectValue(new IntValue(1), 10), v)
  }

  @Test
  void testLongValueOf() {
    Value[] x = new Value[1]
    x[0] = new LongValue(1L)
    Value v = context.invokeMethod(0, "java/lang/Long", "valueOf", x, null)
    assertEquals(new LongObjectValue(new LongValue(1L), 10), v)
  }

  @Test
  void testAssume() {
    Value[] x = new Value[1]
    x[0] = new IntValue(1)
    Value v = context.invokeMethod(0, "janala/Main", "Assume", x, history)
    verify(history).setLastBranchDone()
  }

  @Test
  void testForceTruth() {
    Value[] x = new Value[1]
    x[0] = new IntValue(1)
    Value v = context.invokeMethod(0, "janala/Main", "ForceTruth", x, history)
    verify(history).setLastForceTruth()
  }

  @Test
  void testMakeSymbolic() {
    Value[] x = new Value[1]
    IntValue a = new IntValue(1)
    x[0] = a
    Value v = context.invokeMethod(0, "janala/Main", "MakeSymbolic", x, history)
    assertNotNull(a.getSymbolic())
    verify(history).addInput(anyInt(), eq(a))
  }

  @Test
  void testBeginScope() {
    Value[] x = new Value[0]
    Value v = context.invokeMethod(0, "janala/Main", "BeginScope", x, history)
    verify(history).addInput(config.scopeBeginSymbol, null)
    verify(history).beginScope(0)
  }

  @Test
  void testEndScope() {
    Value[] x = new Value[0]
    Value v = context.invokeMethod(0, "janala/Main", "EndScope", x, history)
    verify(history).addInput(config.scopeEndSymbol, null)
    verify(history).endScope(0)
  }
}