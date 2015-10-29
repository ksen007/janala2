package janala.interpreters

import static org.junit.Assert.assertEquals

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class StringValueTest {
  
  @Test
  void testConstructor() {
    StringValue s = new StringValue("x", 10)
    assertEquals("x", s.concrete)

    def exp = new SymbolicStringExpression(1, new IntValue(1))
    s = new StringValue("x", exp)
    assertEquals("x", s.concrete)
    assertEquals(exp, s.symbolicExp)
  }

  @Test
  void testStringEquals() {
    StringValue s = new StringValue("x", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    assertEquals(new IntValue(1), s.invokeMethod("equals", b, null))

    b[0] = new StringValue("y", 20)
    assertEquals(new IntValue(0), s.invokeMethod("equals", b, null))
     
    b[0] = new ObjectValue(1)
    assertEquals(new IntValue(0), s.invokeMethod("equals", b, null))
  }

  @Test
  void testStringEquals_Symbol() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("x", exp)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.EQ,
      exp, "x")

    assertEquals(new IntValue(1, con), s.invokeMethod("equals", b, null))
  }

  @Test
  void testStringEquals_Symbol2() {
    StringValue s = new StringValue("x", 10)
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    Value[] b = new Value[1];
    b[0] = new StringValue("y", exp)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.EQ,
      "x", exp)
    assertEquals(new IntValue(0, con), s.invokeMethod("equals", b, null))
  }

  @Test
  void testStringEquals_Symbol3() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("x", exp)
    
    Value[] b = new Value[1];
    def exp2 = new SymbolicStringExpression(2, new IntValue(1)) 
    b[0] = new StringValue("x", exp2)
    
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.EQ,
      exp, exp2)
    assertEquals(new IntValue(1, con), s.invokeMethod("equals", b, null))
  }


  @Test
  void testStringStartsWith() {
    StringValue s = new StringValue("xy", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    assertEquals(new IntValue(1), s.invokeMethod("startsWith", b, null))

    b[0] = new StringValue("y", 20)
    assertEquals(new IntValue(0), s.invokeMethod("startsWith", b, null))

    Value[] c = new Value[2];
    c[0] = new StringValue("y", 20)
    c[1] = new IntValue(0)
    assertEquals(new IntValue(0), s.invokeMethod("startsWith", c, null))

    c[1] = new IntValue(1)
    assertEquals(new IntValue(1), s.invokeMethod("startsWith", c, null))
  }

  @Test
  void testStringStartsWith_Symbol() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("x", exp)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
      exp, "x.*")

    assertEquals(new IntValue(1, con), s.invokeMethod("startsWith", b, null))
  }

  @Test
  void testStringStartsWith_Symbol2() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("xy", exp)
    Value[] b = new Value[2];
    b[0] = new StringValue("x", 20)
    b[1] = new IntValue(0)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
      exp, ".{0}x.*")

    assertEquals(new IntValue(1, con), s.invokeMethod("startsWith", b, null))
  }

  @Test
  void testStringEndsWith() {
    StringValue s = new StringValue("xy", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("y", 20)
    assertEquals(new IntValue(1), s.invokeMethod("endsWith", b, null))

    b[0] = new StringValue("yz", 20)
    assertEquals(new IntValue(0), s.invokeMethod("endsWith", b, null))
  }

  @Test
  void testStringEndsWith_Symbol() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("xy", exp)
    Value[] b = new Value[1];
    b[0] = new StringValue("y", 20)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
      exp, ".*y")

    assertEquals(new IntValue(1, con), s.invokeMethod("endsWith", b, null))
  }

  @Test
  void testStringContains() {
    StringValue s = new StringValue("xy", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("y", 20)
    assertEquals(new IntValue(1), s.invokeMethod("contains", b, null))

    b[0] = new StringValue("yz", 20)
    assertEquals(new IntValue(0), s.invokeMethod("contains", b, null))
  }

  @Test
  void testStringContains_Symbol() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("xy", exp)
    Value[] b = new Value[1];
    b[0] = new StringValue("y", 20)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
      exp, ".*y.*")

    assertEquals(new IntValue(1, con), s.invokeMethod("contains", b, null))
  }

  @Test
  void testStringConcat() {
    StringValue s = new StringValue("a", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("y", 20)
    assertEquals(new StringValue("ay", null), s.invokeMethod("concat", b, null))
  }

  @Test
  void testStringConcat_Symbol() {
    Value[] b = new Value[1]
    b[0] = new StringValue("a", 20)
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue x = new StringValue("b", exp)
    assertEquals(new StringValue("ba", exp.concatStr("a")), 
      x.invokeMethod("concat", b, null))
  }

  @Test
  void testStringConcat_Symbol2() {
    Value[] b = new Value[1]
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue x = new StringValue("a", null)
    b[0] = new StringValue("b", exp)
    assertEquals(new StringValue("ab", exp.concatToStr("a")), 
      x.invokeMethod("concat", b, null))
  }

  @Test
  void testStringConcat_Symbol3() {
    Value[] b = new Value[1]
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue x = new StringValue("a", exp)
    b[0] = new StringValue("b", exp)
    assertEquals(new StringValue("ab", exp.concat(exp)), 
      x.invokeMethod("concat", b, null))
  }

  @Test
  void testStringLength() {
    StringValue x = new StringValue("a", null)
    Value[] b = new Value[0]
    assertEquals(new IntValue(1), x.invokeMethod("length", b, null))

    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue a = new StringValue("a", exp)
    assertEquals(new IntValue(1), a.invokeMethod("length", null, null))
  }

  @Test
  void testStringMatches() {
    StringValue s = new StringValue("x", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    assertEquals(new IntValue(1), s.invokeMethod("matches", b, null))

    b[0] = new StringValue("yz", 20)
    assertEquals(new IntValue(0), s.invokeMethod("matches", b, null))
  }

  @Test
  void testStringMatches_Symbol() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("y", exp)
    Value[] b = new Value[1];
    b[0] = new StringValue("y", 20)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.STRING_COMPARISON_OPS.IN,
      exp, "y")

    assertEquals(new IntValue(1, con), s.invokeMethod("matches", b, null))
  }

  @Test
  void testStringReplace() {
    StringValue s = new StringValue("x", 10)
    Value[] b = new Value[2];
    char a = (char) 'x'
    char y = (char) 'y'
    b[0] = new IntValue((int)a)
    b[1] = new IntValue((int)y)

    assertEquals(new StringValue("y", null), s.invokeMethod("replace", b, null))
  }
}