package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertNull

import org.junit.Test
import org.junit.Before
import groovy.transform.CompileStatic

@CompileStatic
class LongValueTest {
  @Before
  void setup() {
    Value.reset()
  }

  @Test
  void testNewId() {
    LongValue i = new LongValue(0)
    assertEquals(0L, i.concrete)
    def b = i.MAKE_SYMBOLIC(null)
    assertEquals(1, b)
    assertEquals(1, i.getSymbolic().linear.size())
  }

  @Test
  void testLAdd1() {
    LongValue i = new LongValue(1L)
    LongValue j = new LongValue(1L)
    def r = i.LADD(j)
    assertEquals(2L, r.concrete)
  }

  @Test
  void testLAddSymbolic() {
    SymbolicInt x1 = new SymbolicInt(1)
    LongValue i = new LongValue(1L, x1)
    LongValue j = new LongValue(1L)
    def r = i.LADD(j)
    assertEquals(2L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())

    r = j.LADD(i)
    assertEquals(2L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())

    r = i.LADD(i)
    assertEquals(2L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())
  }

  @Test
  void testLSub1() {
    LongValue i = new LongValue(1L)
    LongValue j = new LongValue(1L)
    def r = i.LSUB(j)
    assertEquals(0L, r.concrete)
  }

  @Test
  void testLSubSymbolic() {
    SymbolicInt x1 = new SymbolicInt(1)
    LongValue i = new LongValue(2L, x1)
    LongValue j = new LongValue(1L)
    def r = i.LSUB(j)
    assertEquals(1L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())

    r = j.LSUB(i)
    assertEquals(-1L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())

    r = i.LSUB(i)
    assertEquals(0L, r.concrete)
    assertNull(r.getSymbolic())
  }

  @Test
  void testLMul1() {
    LongValue i = new LongValue(1L)
    LongValue j = new LongValue(1L)
    def r = i.LMUL(j)
    assertEquals(1L, r.concrete)
  }

  @Test
  void testLMulSymbolic() {
    SymbolicInt x1 = new SymbolicInt(1)
    LongValue i = new LongValue(2L, x1)
    LongValue j = new LongValue(1L)
    def r = i.LMUL(j)
    assertEquals(2L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())

    r = j.LMUL(i)
    assertEquals(2L, r.concrete)
    assertEquals(1, r.getSymbolic().linear.size())

    r = i.LMUL(i)
    assertEquals(4L, r.concrete) 
    assertEquals(1, r.getSymbolic().linear.size()) // Linear form.
  }

  @Test
  void testLDiv() {
    LongValue i = new LongValue(1L)
    LongValue j = new LongValue(1L)
    def r = i.LDIV(j)
    assertEquals(1L, r.concrete)
  }

  @Test
  void testLRem() {
    LongValue i = new LongValue(1L)
    LongValue j = new LongValue(1L)
    def r = i.LREM(j)
    assertEquals(0L, r.concrete)
  }

  @Test
  void testLNeg() {
    LongValue i = new LongValue(1L)
    def r = i.LNEG()
    assertEquals(-1L, r.concrete)

    SymbolicInt x1 = new SymbolicInt(1)
    i = new LongValue(1L, x1)
    r = i.LNEG()
    assertEquals(-1L, r.concrete)
    assertEquals(-1L, r.concrete)
  }

  @Test
  void testLCMP() {
    LongValue one = new LongValue(1L)
    LongValue two = new LongValue(2L)
    def r = one.LCMP(two)
    assertEquals(-1, r.concrete)
    r = two.LCMP(one)
    assertEquals(1, r.concrete)
    r = one.LCMP(one)
    assertEquals(0, r.concrete)
  }

  @Test
  void testLCMPSymbolic() {
    SymbolicInt x1 = new SymbolicInt(1)
    LongValue one = new LongValue(1L, x1)
    LongValue two = new LongValue(2L)
    def r = one.LCMP(two)
    assertEquals(-1, r.concrete)
    assertEquals(1, r.getSymbolicInt().linear.size()) 
    r = two.LCMP(one)
    assertEquals(1, r.concrete)
    assertEquals(1, r.getSymbolicInt().linear.size()) 
    r = one.LCMP(one)
    assertEquals(0, r.concrete)
    assertNull(r.getSymbolic()) 
  }

  @Test
  void testL2I() {
    LongValue i = new LongValue(1L)
    IntValue j = i.L2I()
    assertEquals(1, j.concrete)
  }

  @Test
  void testLOR() {
    LongValue i = new LongValue(1L)
    def r = i.LOR(i)
    assertEquals(1L, r.concrete)
  }

  @Test
  void testLAND() {
    LongValue i = new LongValue(1L)
    LongValue r = i.LAND(i)
    assertEquals(1L, r.concrete)
  }

  @Test
  void testLXOR() {
    LongValue i = new LongValue(1L)
    LongValue r = i.LXOR(i)
    assertEquals(0L, r.concrete)
  }

  @Test
  void testLSHL() {
    LongValue i = new LongValue(1L)
    LongValue r = i.LSHL(i)
    assertEquals(2L, r.concrete)
  }

  @Test
  void testLSHR() {
    LongValue i = new LongValue(1L)
    LongValue r = i.LSHR(i)
    assertEquals(0L, r.concrete)
  }

  @Test
  void testLUSHR() {
    LongValue i = new LongValue(1L)
    LongValue r = i.LUSHR(i)
    assertEquals(0L, r.concrete)
    assertTrue(r.toString().contains("LongValue"))
  }
}