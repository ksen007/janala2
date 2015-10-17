package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import janala.interpreters.COMPARISON_OPS
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class SymbolicIntCompareConstraintTest {
  @Test
  void testNot() {
    def a = new SymOrInt("x_1")
    def b = new SymOrInt("x_2")

    def con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.EQ)
    assertEquals(
      new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.NE),
      con.not())

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.NE)
    assertEquals(
      new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.EQ),
      con.not())

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GT)
    assertEquals(
      new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LE),
      con.not())

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LE)
    assertEquals(
      new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GT),
      con.not())

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GE)
    assertEquals(
      new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LT),
      con.not())

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LT)
    assertEquals(
      new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GE),
      con.not())
  }

  @Test
  void testToString() {
    def a = new SymOrInt(1L)
    def b = new SymOrInt(2L)

    def con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.EQ)
    println(con)
    assertTrue(con.toString().contains("=="))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.NE)
    assertTrue(con.toString().contains("!="))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GT)
    assertTrue(con.toString().contains(">"))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LE)
    assertTrue(con.toString().contains("<="))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LT)
    assertTrue(con.toString().contains("<"))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GE)
    assertTrue(con.toString().contains(">="))
  }

  @Test
  void testSubstitute() {
    def a = new SymOrInt(1L)
    def b = new SymOrInt(2L)

    def m = new HashMap<String, Long>()
    
    def con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.EQ)
    assertEquals(SymbolicFalseConstraint.instance, con.substitute(m))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.NE)
    assertEquals(SymbolicTrueConstraint.instance, con.substitute(m))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GT)
    assertEquals(SymbolicFalseConstraint.instance, con.substitute(m))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LE)
    assertEquals(SymbolicTrueConstraint.instance, con.substitute(m))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.LT)
    assertEquals(SymbolicTrueConstraint.instance, con.substitute(m))

    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.GE)
    assertEquals(SymbolicFalseConstraint.instance, con.substitute(m))
  }

  @Test
  void testSubstituteSymbol() {
    def a = new SymOrInt(1L)
    def b = new SymOrInt("x1")

    def m = new HashMap<String, Long>()
    def con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.EQ)
    assertEquals(con, con.substitute(m))

    m.put("x1", 1L)
    con = new SymbolicIntCompareConstraint(a, b, COMPARISON_OPS.EQ)
    assertEquals(SymbolicTrueConstraint.instance, con.substitute(m))
  }
}