package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class SymbolicStringExpressionTest {
  
  @Test
  void testConcat() {
    def sym = new SymbolicStringExpression(1, new IntValue(1))
    def x = sym.concatStr("a")
    def y = x.concatStr("b")

    assertTrue(y.toString().contains("ab"))
    
    def z = sym.concatStr("ab")
    assertEquals(z, y)

    assertTrue(z.isCompound())
  }

  @Test
  void testConcatToStr() {
    def sym = new SymbolicStringExpression(1, new IntValue(1))
    def x = sym.concatToStr("a")
    def y = x.concatToStr("b")
    assertTrue(y.toString().contains("ba"))
  }

  @Test
  void testConcatExpr() {
    def sym = new SymbolicStringExpression(1, new IntValue(1))
    def x = sym.concatStr("a")
    def y = sym.concatToStr("b")
    def z = x.concat(y)
    assertTrue(z.toString().contains("ab"))
  }
  
  @Test
  void testLength() {
    def sym = new SymbolicStringExpression(1, new IntValue(1))
    def x = sym.concatStr("ab")
    
    assertEquals(new IntValue(3), x.getField("length"))
  }

  @Test
  void testExprAt() {
    def sym = new SymbolicStringExpression(1, new IntValue(2))
    def x = sym.concatStr("ab")

    char a = 'a'
    def set = new HashSet<String>()
    def map = new HashMap<String, Long>()
    assertEquals(new SymOrInt((long)a), x.getExprAt(2, set, map))

    def z = x.concat(x)
    println(z)
    assertEquals(new SymOrInt("x1__1"), z.getExprAt(5, set, map))
  }

}
