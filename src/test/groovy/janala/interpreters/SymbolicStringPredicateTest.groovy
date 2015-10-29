package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import janala.solvers.CVC4Solver.CONSTRAINT_TYPE
import janala.interpreters.SymbolicStringPredicate.STRING_COMPARISON_OPS

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class SymbolicStringPredicateTest {

  @Test
  void testNot() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.EQ, "a", "b")
    SymbolicStringPredicate p2 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NE, "a", "b")
    
    assertEquals(p2, p1.not())
    assertEquals(p1, p2.not())

    SymbolicStringPredicate p3 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.IN, "a", "b")
    SymbolicStringPredicate p4 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NOTIN, "a", "b")
    
    assertEquals(p4, p3.not())
    assertEquals(p3, p4.not())
  }

  @Test
  void testStringfy() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.EQ, "a", "b")
    assertEquals('"a" == "b"', p1.toString())

    p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NE, "a", new Integer(1))
    assertEquals('"a" != 1', p1.toString())

    p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.IN, "a", null)
    assertEquals('"a" regexin null', p1.toString())

    p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NOTIN, "a", null)
    assertEquals('"a" regexnotin null', p1.toString())
  }

  @Test
  void testConstraintStr() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.EQ, "a", "b")
    def s = new HashSet<String>()
    def m = new HashMap<String, Long>()
    Constraint con = p1.getFormula(s, CONSTRAINT_TYPE.STR, m)
    assertTrue(con instanceof SymbolicAndConstraint)
    SymbolicAndConstraint a = (SymbolicAndConstraint) con
    assertEquals(1, a.constraints.size())
    
    char aChar = 'a'
    char bChar = 'b'
    def expected = new SymbolicIntCompareConstraint(
      new SymOrInt((long)aChar), new SymOrInt((long)bChar), 
      COMPARISON_OPS.EQ)
    assertEquals(expected, a.constraints.get(0))
  }

  @Test
  void testConstraintStr_length() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.EQ, "a", "bb")
    def s = new HashSet<String>()
    def m = new HashMap<String, Long>()
    Constraint con = p1.getFormula(s, CONSTRAINT_TYPE.STR, m)
    assertEquals(SymbolicFalseConstraint.instance, con)
  }

  @Test
  void testConstraintStrNE() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NE, "a", "b")
    def s = new HashSet<String>()
    def m = new HashMap<String, Long>()
    Constraint con = p1.getFormula(s, CONSTRAINT_TYPE.STR, m)
    assertTrue(con instanceof SymbolicNotConstraint)
    SymbolicNotConstraint a = (SymbolicNotConstraint) con
    
    char aChar = 'a'
    char bChar = 'b'
    def eqCon = new SymbolicIntCompareConstraint(
      new SymOrInt((long)aChar), new SymOrInt((long)bChar), 
      COMPARISON_OPS.EQ)
    def expected = new SymbolicAndConstraint(eqCon)
    assertEquals(expected, a.constraint)
  }

  @Test
  void testConstraintStrNE_length() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NE, "a", "bb")
    def s = new HashSet<String>()
    def m = new HashMap<String, Long>()
    Constraint con = p1.getFormula(s, CONSTRAINT_TYPE.STR, m)
    
    assertEquals(SymbolicTrueConstraint.instance, con)
  }

  @Test
  void testConstraintInt() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.EQ, "a", "b")
    def s = new HashSet<String>()
    def m = new HashMap<String, Long>()
    Constraint con = p1.getFormula(s, CONSTRAINT_TYPE.INT, m)
    // Only test the length.
    assertEquals(SymbolicTrueConstraint.instance, con)
  }

  @Test
  void testConstraintNEInt() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      STRING_COMPARISON_OPS.NE, "a", "b")
    def s = new HashSet<String>()
    def m = new HashMap<String, Long>()
    Constraint con = p1.getFormula(s, CONSTRAINT_TYPE.INT, m)
    // Only test the length.
    assertEquals(SymbolicTrueConstraint.instance, con)
  }
}