package janala.system

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import janala.logger.DJVM
import janala.logger.DirectConcolicExecution
import janala.logger.ClassNames
import janala.interpreters.ConcolicInterpreter
import janala.interpreters.IntValue
import janala.interpreters.Value
import janala.interpreters.ConcolicInterpreter
import janala.instrument.Coverage
import janala.config.Config

import org.junit.Before
import org.junit.Test
import org.junit.Ignore

import groovy.transform.CompileStatic

// System test.
@CompileStatic
class IntepreterTest {
  private ConcolicInterpreter interpreter
  private ClassNames classNames = ClassNames.getInstance()

  @Before
  void setup() {
    Config.instance.cvc4Command = "unknown"
    interpreter = new ConcolicInterpreter(ClassNames.getInstance(), Config.instance)
    DirectConcolicExecution dc = new DirectConcolicExecution(interpreter, false)
    DJVM.setInterpreter(dc)
  }

  private int greaterThanZero(int x) {
    DJVM.ILOAD(0, 0, 1); // x is the 1st local (0 is)
    DJVM.IFLE(1, 0, 6);
    if (x > 0) {
      DJVM.SPECIAL(1); // True branch
      DJVM.ICONST_1(2, 0);
      DJVM.IRETURN(4, 0);
      return 1;
    }
    DJVM.ICONST_M1(5, 0);
    DJVM.IRETURN(6, 0);
    return -1;
  }

  @Test
  void testGreaterThanZero() {
    int classIdx = classNames.get("janala/system/IntepreterTest")

    DJVM.NEW(0, 1, "janala/system/IntepreterTest", classIdx)
    DJVM.ICONST_1(1, 1)
    DJVM.INVOKEVIRTUAL(2, 1, "janala/system/IntepreterTest", "greaterThanZero", "(I)I")
    int x = greaterThanZero(1)
    assertEquals(x, 1)

    DJVM.INVOKEMETHOD_END()
    DJVM.flush()
    Value y = interpreter.getCurrentFrame().peek()
    assertEquals(new IntValue(1), y)
    assertEquals(1, interpreter.getHistory().getHistory().size())
    assertEquals(0, interpreter.getHistory().getPathConstraint().size())
  }

  @Test
  void testGreaterThanZeroSymbol() {
    int classIdx = classNames.get("janala/system/IntepreterTest")

    DJVM.NEW(0, 1, "janala/system/IntepreterTest", classIdx)
    DJVM.ICONST_1(1, 1)
    DJVM.ISTORE(2, 1, 1)
    DJVM.ILOAD(3, 1, 1)
    DJVM.INVOKESTATIC(4, 1, "janala/Main", "MakeSymbolic", "(I)V")
    DJVM.INVOKEMETHOD_END()
    DJVM.ILOAD(5, 1, 1)
    DJVM.INVOKEVIRTUAL(6, 1, "janala/system/IntepreterTest", "greaterThanZero", "(I)I")
    int x = greaterThanZero(1)
    assertEquals(x, 1)

    DJVM.INVOKEMETHOD_END()
    DJVM.flush()
    Value y = interpreter.getCurrentFrame().peek()

    assertEquals(new IntValue(1), y)
    assertEquals(1, interpreter.getHistory().getHistory().size())
    assertEquals(1, interpreter.getHistory().getPathConstraint().size())
  }

  private boolean lessOrEqualZero(int x) {
    DJVM.ILOAD(0, 0, 1)
    DJVM.IFGT(1, 0, 6)
    if (x <= 0) {
      DJVM.SPECIAL(1)
      DJVM.ICONST_1(2, 0)
      DJVM.IRETURN(3, 0)
      return true;
    }
    DJVM.ICONST_0(5, 0)
    DJVM.IRETURN(6, 0)
    return false;
  }
  
  @Test
  void testLessOrEqualZero() {
    int classIdx = classNames.get("janala/system/IntepreterTest")

    DJVM.NEW(0, 1, "janala/system/IntepreterTest", classIdx)
    DJVM.ICONST_1(1, 1)
    DJVM.INVOKEVIRTUAL(2, 1, "janala/system/IntepreterTest", "lessOrEqualZero", "(I)Z")
    boolean x = lessOrEqualZero(1)
    assertEquals(false, x)

    DJVM.INVOKEMETHOD_END()
    DJVM.flush()
    Value y = interpreter.getCurrentFrame().peek()
    assertEquals(new IntValue(0), y)
  }

  private int booleanAnd(boolean x, boolean y) {
    DJVM.ILOAD(0, 0, 1)
    DJVM.IFEQ(1, 0, 10)
    if (x) {
      DJVM.ILOAD(2, 0, 2)
      DJVM.IFEQ(3, 0, 10)
      if (y) {
        DJVM.ICONST_1(4, 0)
        DJVM.IRETURN(5, 0)
        return 1;
      }
    }
    DJVM.ICONST_0(6, 0)
    DJVM.IRETURN(7, 0)
    return 0;
  }

  @Test
  void testBooleanAndTrue() {
    int classIdx = classNames.get("janala/system/IntepreterTest")

    DJVM.NEW(0, 1, "janala/system/IntepreterTest", classIdx)
    DJVM.ICONST_1(1, 1)
    DJVM.ICONST_1(1, 1)
    DJVM.INVOKEVIRTUAL(2, 1, "janala/system/IntepreterTest", "booleanAnd", "(ZZ)I")
    int x = booleanAnd(true, true)
    assertEquals(1, x)

    DJVM.INVOKEMETHOD_END()
    DJVM.flush()
    Value y = interpreter.getCurrentFrame().peek()
    assertEquals(new IntValue(1), y)
  }

  @Test
  void testBooleanAndFalse() {
    int classIdx = classNames.get("janala/system/IntepreterTest")

    DJVM.NEW(0, 1, "janala/system/IntepreterTest", classIdx)
    DJVM.ICONST_1(1, 0)
    DJVM.ICONST_1(1, 1)
    DJVM.INVOKEVIRTUAL(2, 1, "janala/system/IntepreterTest", "booleanAnd", "(ZZ)I")
    int x = booleanAnd(false, true)
    assertEquals(0, x)

    DJVM.INVOKEMETHOD_END()
    DJVM.flush()
    Value y = interpreter.getCurrentFrame().peek()
    assertEquals(new IntValue(0), y)
  }

}