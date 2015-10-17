package janala.interpreters

import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertEquals

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class ConstraintTest {
  @Test
  public void testBoolConstraint() {
    def t = SymbolicTrueConstraint.instance
    assertEquals(SymbolicFalseConstraint.instance, t.not())
    assertEquals(t, t.substitute(["x1": 1L]))
    assertTrue(t.toString().contains("TRUE"))

    def f = SymbolicFalseConstraint.instance
    assertEquals(t, f.not())
    assertEquals(f, f.substitute(["x1": 1L]))
    assertTrue(f.toString().contains("FALSE"))
  }

  @Test
  public void testOr() {
    def t = SymbolicTrueConstraint.instance
    def f = SymbolicFalseConstraint.instance
    def x1 = new SymbolicInt(1)

    def o1 = new SymbolicOrConstraint(f)
    def o2 = o1.OR(f)
    assertEquals(f, o2.substitute(["x1": 1L]))

    def o3 = o1.OR(t)
    assertEquals(t, o3.substitute(["x1": 1L]))

    def o4 = o1.not()
    assertEquals(t, o4.substitute(["x1": 1L]))

    def o5 = new SymbolicOrConstraint(f).OR(x1)
    o5 = o5.substitute(["not": 1L])
    assertTrue(o5.toString().contains("x1"))

    assertTrue(o2.toString().contains("||"))
  }

  @Test
  public void testAnd() {
    def t = SymbolicTrueConstraint.instance
    def f = SymbolicFalseConstraint.instance
    def x1 = new SymbolicInt(1)

    def a1 = new SymbolicAndConstraint(t)
    def a2 = a1.AND(f)
    assertEquals(f, a2.substitute(["x1": 1L]))

    def a3 = a1.AND(t)
    assertEquals(t, a3.substitute(["x1": 1L]))

    def a4 = a1.not()
    assertEquals(f, a4.substitute(["x1": 1L]))

    def a5 = new SymbolicAndConstraint(t).AND(x1)
    a5 = a5.substitute(["not": 1L])
    assertTrue(a5.toString().contains("x1"))

    assertTrue(a2.toString().contains("&&"))
  }  
}