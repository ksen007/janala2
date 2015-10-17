package janala.interpreters;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue

import org.junit.Test
import org.junit.Before
import java.util.HashMap

class IntValueTest {
    @Before
    void setup() {
        Value.reset()
    }

    @Test
    void testNewId() {
        IntValue i = new IntValue(0)
        assertEquals(0, i.concrete)
        def b = i.MAKE_SYMBOLIC(null)
        assertEquals(1, b)
        assertEquals(1, i.getSymbolicInt().linear.size())
        assertEquals(b, i.getSymbol())
    }

    @Test
    void testConstructor() {
        IntValue a = new IntValue(1, SymbolicFalseConstraint.instance)
        assertEquals(a.concrete, 1)
        assertEquals(a.symbolic, SymbolicFalseConstraint.instance)

        SymbolicInt b = new SymbolicInt(1)
        b.setOp(COMPARISON_OPS.EQ)
        IntValue c = new IntValue(1, b)
        assertEquals(1, c.concrete)
        assertEquals(b, c.symbolic)
        assertEquals(b, c.symbolicInt)
    }

    @Test
    void testSubstitute() {
        IntValue i = new IntValue(0)
        def b = i.MAKE_SYMBOLIC(null)
        def m = ["x1": 1L]
        assertEquals(1, i.substituteInLinear(m))
    }

    @Test
    void testSubstitueNone() {
        IntValue i = new IntValue(0)
        assertEquals(0, i.substituteInLinear(["x2": 1L]))
        IntValue j = new IntValue(0)
        def b = j.MAKE_SYMBOLIC(null)
        assertEquals(0, j.substituteInLinear(["x2": 1L]))
    }

    @Test
    public void testIINC() {
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)

        IntValue j = i.IINC(1)
        assertEquals(1, j.concrete)
        def m = new HashMap<Integer, Long>()
        m.put(b, 1L)
        assertEquals(new SymbolicInt(m, 1), j.symbolicInt)
    }

    @Test
    public void testIFEQNoConstraint() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.TRUE, i.IFEQ())
        IntValue j = new IntValue(1)
        assertEquals(IntValue.FALSE, j.IFEQ())
    }

    @Test
    public void testIFEQSymbolicInt() {
        // (0, x) == 0 
        // -> (1, x == 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFEQ()
        assertEquals(1, r.concrete)
        assertEquals(COMPARISON_OPS.EQ, r.symbolicInt.op)

        // (0, x == 0) == 0 
        // -> (1, x != 0)
        SymbolicInt x = new SymbolicInt(1)
        x.setOp(COMPARISON_OPS.EQ)
        IntValue j = new IntValue(0, x)
        r = j.IFEQ()
        assertEquals(1, r.concrete)
        assertEquals(COMPARISON_OPS.NE, r.symbolicInt.op)
    }

    @Test
    public void testIFEQConstraint() {
        // (0, FALSE) == 0
        // -> (1, TRUE)
        IntValue i = new IntValue(0, SymbolicFalseConstraint.instance)
        def r = i.IFEQ()
        assertEquals(1, r.concrete)
        assertEquals(SymbolicTrueConstraint.instance, r.symbolic)
    }

    @Test
    public void testIFNENoConstraint() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.FALSE, i.IFNE())
        IntValue j = new IntValue(1)
        assertEquals(IntValue.TRUE, j.IFNE())
    }

    @Test
    public void testIFNESymbolicInt() {
        // (0, x) != 0 
        // -> (0, x == 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFNE()
        assertEquals(0, r.concrete)
        assertEquals(COMPARISON_OPS.EQ, r.symbolicInt.op)

        // (0, x == 0) != 0 
        // -> (0, x == 0)
        SymbolicInt x = new SymbolicInt(1)
        x.setOp(COMPARISON_OPS.EQ)
        IntValue j = new IntValue(0, x)
        r = j.IFNE()
        assertEquals(0, r.concrete)
        assertEquals(COMPARISON_OPS.NE, r.symbolicInt.op)
    }

    @Test
    public void testIFNEConstraint() {
        // (0, FALSE) != 0
        // -> (0, TRUE)
        IntValue i = new IntValue(0, SymbolicFalseConstraint.instance)
        def r = i.IFNE()
        assertEquals(0, r.concrete)
        assertEquals(SymbolicTrueConstraint.instance, r.symbolic)
    }

    @Test
    public void testIFLTNoConstraint() {
        IntValue i = new IntValue(-1)
        assertEquals(IntValue.TRUE, i.IFLT())
        IntValue j = new IntValue(0)
        assertEquals(IntValue.FALSE, j.IFLT())
    }

    @Test
    public void testIFLTSymbolicInt() {
        // (0, x) < 0 
        // -> (false, x >= 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFLT()
        assertEquals(0, r.concrete)
        assertEquals(COMPARISON_OPS.GE, r.symbolicInt.op)

        // (-1, x) < 0 
        // -> (true, x < 0)
        SymbolicInt x = new SymbolicInt(1)
        IntValue j = new IntValue(-1, x)
        r = j.IFLT()
        assertEquals(1, r.concrete)
        assertEquals(COMPARISON_OPS.LT, r.symbolicInt.op)
    }

    @Test
    public void testIFGENoConstraint() {
        IntValue i = new IntValue(-1)
        assertEquals(IntValue.FALSE, i.IFGE())
        IntValue j = new IntValue(0)
        assertEquals(IntValue.TRUE, j.IFGE())
    }

    @Test
    public void testIFGESymbolicInt() {
        // (0, x) >= 0 
        // -> (true, x >= 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFGE()
        assertEquals(1, r.concrete)
        assertEquals(COMPARISON_OPS.GE, r.symbolicInt.op)

        // (-1, x) >= 0 
        // -> (false, x < 0)
        SymbolicInt x = new SymbolicInt(1)
        IntValue j = new IntValue(-1, x)
        r = j.IFGE()
        assertEquals(0, r.concrete)
        assertEquals(COMPARISON_OPS.LT, r.symbolicInt.op)
    }

    @Test
    public void testIFLENoConstraint() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.TRUE, i.IFLE())
        IntValue j = new IntValue(1)
        assertEquals(IntValue.FALSE, j.IFLE())
    }

    @Test
    public void testIFLESymbolicInt() {
        // (0, x) <= 0 
        // -> (true, x <= 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFLE()
        assertEquals(1, r.concrete)
        assertEquals(COMPARISON_OPS.LE, r.symbolicInt.op)

        // (1, x) <= 0 
        // -> (false, x > 0)
        SymbolicInt x = new SymbolicInt(1)
        IntValue j = new IntValue(1, x)
        r = j.IFLE()
        assertEquals(0, r.concrete)
        assertEquals(COMPARISON_OPS.GT, r.symbolicInt.op)
    }    

    @Test
    public void testIFGTNoConstraint() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.FALSE, i.IFGT())
        IntValue j = new IntValue(1)
        assertEquals(IntValue.TRUE, j.IFGT())
    }

    @Test
    public void testIFGTSymbolicInt() {
        // (0, x) > 0 
        // -> (false, x <= 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFGT()
        assertEquals(0, r.concrete)
        assertEquals(COMPARISON_OPS.LE, r.symbolicInt.op)

        // (1, x) > 0 
        // -> (true, x > 0)
        SymbolicInt x = new SymbolicInt(1)
        IntValue j = new IntValue(1, x)
        r = j.IFGT()
        assertEquals(1, r.concrete)
        assertEquals(COMPARISON_OPS.GT, r.symbolicInt.op)
    }

    @Test
    public void testIFICMPEQNoSymbol() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.TRUE, i.IF_ICMPEQ(i))
        IntValue j = new IntValue(1)
        assertEquals(IntValue.FALSE, j.IF_ICMPEQ(i))
    }

    @Test
    public void testIFICMPEQSymbol() {
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue i = new IntValue(0, x1)
        // (0, x) == (0, x)
        // -> (1, null)
        IntValue r = i.IF_ICMPEQ(i);
        assertEquals(1, r.concrete)
        assertNull(r.symbolic)
    }

    @Test
    public void testIFICMPEQSymbolTwoSymbols() {
        // (0, x1) == (0, x2)
        // -> (1, x1 - x2 == 0)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0, x2)
        IntValue r = a.IF_ICMPEQ(b)

        assertEquals(1, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.EQ, r.symbolic.op)
    }

    @Test
    public void testIFICMPEQSymbolOneSymbol() {
        // (0, x1) == (0, null)
        // -> (1, x1 == 0)
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0)

        IntValue r = a.IF_ICMPEQ(b)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.EQ, r.symbolic.op)

        r = b.IF_ICMPEQ(a)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.EQ, r.symbolic.op)
    }

    @Test
    public void testIFICMPNESymbol() {
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue i = new IntValue(0, x1)
        // (0, x) != (0, x)
        // -> (0, null)
        IntValue r = i.IF_ICMPNE(i);
        assertEquals(0, r.concrete)
        assertNull(r.symbolic)
    }

    @Test
    public void testIFICMPNESymbolTwoSymbols() {
        // (0, x1) != (0, x2)
        // -> (0, x1 - x2 == 0)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0, x2)
        IntValue r = a.IF_ICMPNE(b)

        assertEquals(0, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.EQ, r.symbolic.op)
    }

    @Test
    public void testIFICMPNESymbolOneSymbol() {
        // (0, x1) != (0, null)
        // -> (0, x1 == 0)
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0)

        IntValue r = a.IF_ICMPNE(b)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.EQ, r.symbolic.op)

        r = b.IF_ICMPNE(a)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.EQ, r.symbolic.op)
    }

    @Test
    public void testIF_ICMPLTNoSymbol() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.FALSE, i.IF_ICMPLT(i))
        IntValue j = new IntValue(1)
        assertEquals(IntValue.TRUE, i.IF_ICMPLT(j))
    }

    @Test
    public void testIF_ICMPLTSymbolTwoSymbols() {
        // (0, x1) < (0, x2)
        // -> (0, x1 - x2 >= 0)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0, x2)
        IntValue r = a.IF_ICMPLT(b)

        assertEquals(0, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.GE, r.symbolic.op)
    }

    @Test
    public void testIFICMPLTSymbolOneSymbol() {
        // (0, x1) < (1, null)
        // -> (1, x1 - 1< 0)
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.IF_ICMPLT(b)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(-1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.LT, r.symbolic.op)

        // (1, null) < (0, x1) 
        // -> (0, 1 - x1 >= 0)
        r = b.IF_ICMPLT(a)
        println(r)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.GE, r.symbolic.op)
    }
 
    @Test
    public void testIF_ICMPGENoSymbol() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.TRUE, i.IF_ICMPGE(i))
        IntValue j = new IntValue(1)
        assertEquals(IntValue.FALSE, i.IF_ICMPGE(j))
    }

    @Test
    public void testIF_ICMPGESymbolTwoSymbols() {
        // (0, x1) >= (0, x2)
        // -> (1, x1 - x2 >= 0)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0, x2)
        IntValue r = a.IF_ICMPGE(b)

        assertEquals(1, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.GE, r.symbolic.op)
    }

    @Test
    public void testIFICMPGESymbolOneSymbol() {
        // (0, x1) >= (1, null)
        // -> (0, x1 - 1< 0)
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.IF_ICMPGE(b)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(-1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.LT, r.symbolic.op)

        // (1, null) >= (0, x1) 
        // -> (1, 1 - x1 >= 0)
        r = b.IF_ICMPGE(a)
        println(r)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.GE, r.symbolic.op)
    }
 
    @Test
    public void testIF_ICMPGTNoSymbol() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.FALSE, i.IF_ICMPGT(i))
        IntValue j = new IntValue(1)
        assertEquals(IntValue.TRUE, j.IF_ICMPGT(i))
    }

    @Test
    public void testIF_ICMPGTSymbolTwoSymbols() {
        // (0, x1) > (0, x2)
        // -> (0, x1 - x2 <= 0)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0, x2)
        IntValue r = a.IF_ICMPGT(b)

        assertEquals(0, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.LE, r.symbolic.op)
    }

    @Test
    public void testIFICMPGTSymbolOneSymbol() {
        // (0, x1) > (1, null)
        // -> (0, x1 - 1 <= 0)
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.IF_ICMPGT(b)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(-1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.LE, r.symbolic.op)

        // (1, null) > (0, x1) 
        // -> (1, 1 - x1 > 0)
        r = b.IF_ICMPGT(a)
        println(r)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.GT, r.symbolic.op)
    }

    @Test
    public void testIF_ICMPLENoSymbol() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.TRUE, i.IF_ICMPLE(i))
        IntValue j = new IntValue(1)
        assertEquals(IntValue.FALSE, j.IF_ICMPLE(i))
    }

    @Test
    public void testIF_ICMPLESymbolTwoSymbols() {
        // (0, x1) <= (0, x2)
        // -> (1, x1 - x2 <= 0)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(0, x2)
        IntValue r = a.IF_ICMPLE(b)

        assertEquals(1, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
        assertEquals(COMPARISON_OPS.LE, r.symbolic.op)
    }

    @Test
    public void testIFICMPLESymbolOneSymbol() {
        // (0, x1) <= (1, null)
        // -> (1, x1 - 1 <= 0)
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(0, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.IF_ICMPLE(b)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(-1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.LE, r.symbolic.op)

        // (1, null) <= (0, x1) 
        // -> (0, 1 - x1 > 0)
        r = b.IF_ICMPLE(a)
        println(r)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        assertEquals(1, r.symbolic.constant)
        assertEquals(COMPARISON_OPS.GT, r.symbolic.op)
    }

    @Test
    public void testIADDNoSymbols() {
        IntValue a = new IntValue(1)
        IntValue b = a.IADD(a)
        assertEquals(2, b.concrete)
        assertNull(b.symbolic)
    }   
 
    @Test
    public void testIADDTwoSymbols() {
        // (1, x1) + (1, x2) = (2, x1 + x2)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(1, x1)
        IntValue b = new IntValue(1, x2)
        IntValue r = a.IADD(b)

        assertEquals(2, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
    }

   @Test
    public void testIADDOneSymbol() {
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(1, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.IADD(b)
        assertEquals(2, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        
        r = b.IADD(a)
        assertEquals(2, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
    }

    @Test
    public void testISUBNoSymbols() {
        IntValue a = new IntValue(1)
        IntValue b = a.ISUB(a)
        assertEquals(0, b.concrete)
        assertNull(b.symbolic)
    } 

    @Test
    public void testISUBTwoSymbols() {
        // (1, x1) - (1, x2) = (0, x1 - x2)
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(1, x1)
        IntValue b = new IntValue(1, x2)
        IntValue r = a.ISUB(b)

        assertEquals(0, r.concrete)
        assertEquals(2, r.symbolic.linear.size())
    }          
 
    @Test
    public void testISUBOneSymbol() {
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(1, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.ISUB(b)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        
        r = b.ISUB(a)
        assertEquals(0, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
    }

   @Test
    public void testIMULNoSymbols() {
        IntValue a = new IntValue(1)
        IntValue b = a.IMUL(a)
        assertEquals(1, b.concrete)
        assertNull(b.symbolic)
    } 

    @Test
    public void testIMULTwoSymbols() {
        // (1, x1) * (1, x2) = (0, x1 )
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        IntValue a = new IntValue(1, x1)
        IntValue b = new IntValue(1, x2)
        IntValue r = a.IMUL(b)

        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
    }          
 
    @Test
    public void testIMULOneSymbol() {
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(1, x1)
        IntValue b = new IntValue(1)

        IntValue r = a.IMUL(b)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
        
        r = b.IMUL(a)
        assertEquals(1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
    }    

    @Test
    public void testIDIVNoSymbols() {
        IntValue a = new IntValue(4)
        IntValue b = new IntValue(2)
        IntValue r = a.IDIV(b)
        assertEquals(2, r.concrete)
        assertNull(r.symbolic)
    } 

    @Test
    public void testIREMNoSymbols() {
        IntValue a = new IntValue(3)
        IntValue b = new IntValue(2)
        IntValue r = a.IREM(b)
        assertEquals(1, r.concrete)
        assertNull(r.symbolic)
    }

    @Test
    public void testINEGNoSymbols() {
        IntValue a = new IntValue(3)
        IntValue r = a.INEG()
        assertEquals(-3, r.concrete)
        assertNull(r.symbolic)
    }

    @Test
    public void testINEGOneSymbol() {
        SymbolicInt x1 = new SymbolicInt(1)
        IntValue a = new IntValue(1, x1)

        IntValue r = a.INEG()
        assertEquals(-1, r.concrete)
        assertEquals(1, r.symbolic.linear.size())
    } 

    @Test
    public void testSimpleFcns() {
        testFcnNoSymbol({IntValue v -> v.I2B()}, {x -> x}, 1)
        testFcnNoSymbol({IntValue v -> v.I2C()}, {x -> x}, 1)
        testFcnNoSymbol({IntValue v -> v.I2S()}, {x -> x}, 1)

        testFcn2NoSymbol({x, y -> x.ISHL(y)}, {a, b -> a << b}, 2, 1)
        testFcn2NoSymbol({x, y -> x.ISHR(y)}, {a, b -> a >> b}, 2, 1)
        testFcn2NoSymbol({x, y -> x.IUSHR(y)}, {a, b -> a >>> b}, 2, 1)
        testFcn2NoSymbol({x, y -> x.IAND(y)}, {a, b -> a & b}, 1, 1)
        testFcn2NoSymbol({x, y -> x.IOR(y)}, {a, b -> a | b}, 1, 1)
        testFcn2NoSymbol({x, y -> x.IXOR(y)}, {a, b -> a ^ b}, 1, 1)
    }

    private testFcnNoSymbol(Closure intValClosure, Closure nativeClosure, int value) {
        int expected = nativeClosure(value)
        IntValue wrapped = new IntValue(value)
        IntValue actual = intValClosure(wrapped)
        assertEquals(expected, actual.concrete)
    }

    private testFcn2NoSymbol(Closure intValClosure, Closure nativeClosure, 
                            int x, int y) {
        int expected = nativeClosure(x, y)
        IntValue wrappedX = new IntValue(x)
        IntValue wrappedY = new IntValue(y)
        IntValue actual = intValClosure(wrappedX, wrappedY)
        assertEquals(expected, actual.concrete)
    }
}