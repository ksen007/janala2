package janala.interpreters;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class SymbolicIntTest {
    @Test
    void testSymbolicIntAdd() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt y = x1.add(1)
        assertEquals(1L, y.constant)

        SymbolicInt a = x1.subtract(1L)
        assertEquals(-1L, a.constant)

        SymbolicInt x2 = new SymbolicInt(2)

        SymbolicInt z = y.add(x2)
        assertEquals(1L, z.constant)
        assertEquals(2, z.linear.size())

        SymbolicInt z2 = x2.subtract(y)
        assertEquals(-1L, z2.constant)
    }

    @Test
    void testSymbolicIntNegate() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        SymbolicInt y = x1.add(1)
        SymbolicInt z = y.add(x2)
        SymbolicInt nz = z.negate()

        assertEquals(-1L, nz.constant)
        assertEquals(2, nz.linear.size())
        assertEquals(-1L, nz.linear.get(1))
        assertEquals(-1L, nz.linear.get(2))

        SymbolicInt w = z.add(nz)
        assertEquals(null, w)
    }

    @Test
    void testSymbolicIntSubtractFrom() {
        SymbolicInt x1 = new SymbolicInt(1)
        
        SymbolicInt y = x1.negate().add(1)
        SymbolicInt z = x1.subtractFrom(1)
        assertEquals(y, z)
    }

    @Test
    void testSymbolicIntMultiply() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt y = x1.add(1)
        
        SymbolicInt z = y.multiply(2)
        assertEquals(2L, z.linear.get(1))
        assertEquals(2L, z.constant)
    }

    @Test
    void testToString() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt y = x1.multiply(2)
        SymbolicInt z = y.negate()
        SymbolicInt x2 = new SymbolicInt(2)
        SymbolicInt w = x1.add(x2)
        System.out.println(x1.toString())
        System.out.println(y.toString())
        System.out.println(z.toString())
        System.out.println(w.toString())

        String s = x1.toString()
        assertTrue(s.contains("x1"))
        s = y.toString()
        assertTrue(s.contains("x1") && s.contains("2"))
        s = z.toString()
        assertTrue(s.contains("x1") && s.contains("-2"))

        s = w.toString()
        assertTrue(s.contains("x1") && s.contains("x2"))
    }

    @Test
    void testEquals() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        assertFalse(x1.equals(null))
        assertFalse(x1.equals("a"))
        assertTrue(x1.equals(x1))

        assertFalse(x1.equals(x2))
        assertFalse(x1.hashCode() == x2.hashCode())
    }

    @Test
    void testNot() {
        SymbolicInt x1 = new SymbolicInt(1)
        x1.setOp(COMPARISON_OPS.EQ)
        SymbolicInt y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.NE)

        x1.setOp(COMPARISON_OPS.NE)
        y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.EQ)   

        x1.setOp(COMPARISON_OPS.LT)
        y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.GE)   

        x1.setOp(COMPARISON_OPS.GE)
        y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.LT)

        x1.setOp(COMPARISON_OPS.LE)
        y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.GT)   

        x1.setOp(COMPARISON_OPS.GT)
        y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.LE)

        x1.setOp(COMPARISON_OPS.UN)
        y = x1.not()
        assertEquals(y.getOp(), COMPARISON_OPS.UN)
    }

    @Test
    void testSetop() {
        SymbolicInt x1 = new SymbolicInt(1)
        x1.setOp(COMPARISON_OPS.EQ)
        SymbolicInt y = x1.setop(COMPARISON_OPS.EQ)
        assertEquals(COMPARISON_OPS.EQ, y.getOp())
    }

    @Test
    void testSubstitue() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt y = x1.add(1)
        y.setOp(COMPARISON_OPS.LE) // x1 + 1 <= 0
        Constraint z = y.substitute(["x1": 1L])
        assertTrue(z == SymbolicFalseConstraint.instance)

        Constraint z2 = y.substitute(["x1": -2L])
        assertTrue(z2 == SymbolicTrueConstraint.instance)

        SymbolicInt a = x1.add(new SymbolicInt(2))
        a.setOp(COMPARISON_OPS.GE) // x1 + x2 >= 0
        Constraint b = a.substitute(["x1": 1L])
        System.out.println(b)
        assertTrue(b instanceof SymbolicInt)
        SymbolicInt b2 = (SymbolicInt) b
        assertEquals(1, b2.linear.size())
        assertEquals(1, b2.constant)
        assertTrue(b2.linear.containsKey(2))

        Constraint c = a.substitute(["x1": 1L, "x2": 1L])
        assertTrue(c == SymbolicTrueConstraint.instance)
    }

}