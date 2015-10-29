package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

import janala.config.Config
import janala.solvers.History
import janala.solvers.Solver
import janala.solvers.BranchElement
import janala.logger.ClassNames
import janala.logger.ObjectInfo
import janala.logger.inst.*
import janala.instrument.Coverage
import janala.utils.FileUtil

import org.junit.Before
import org.junit.Test
import groovy.transform.CompileStatic

@CompileStatic
class ConcolicInterpreterTest {
  private Config config
  private ClassDepot classDepot
  private ClassNames classNames
  private ConcolicInterpreter interpreter
  private Solver solver
  private History history
  private Coverage coverage
 
  @Before
  void setup() {
    config = new Config()
    classDepot = mock(ClassDepot.class)
    classNames = new ClassNames(classDepot)
    solver = mock(Solver.class)
    history = new History(solver, new FileUtil(), config)
    coverage = mock(Coverage.class)
    interpreter = new ConcolicInterpreter(classNames, history, coverage, config)
  }

  @Test
  void testICONST0() {
    interpreter.visitICONST_0(new ICONST_0(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(0), frame.peek())
  }

  @Test
  void testICONST1() {
    interpreter.visitICONST_1(new ICONST_1(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testICONST2() {
    interpreter.visitICONST_2(new ICONST_2(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testICONST3() {
    interpreter.visitICONST_3(new ICONST_3(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(3), frame.peek())
  }

  @Test
  void testICONST4() {
    interpreter.visitICONST_4(new ICONST_4(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(4), frame.peek())
  }

  @Test
  void testICONST5() {
    interpreter.visitICONST_5(new ICONST_5(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(5), frame.peek())
  }

  @Test
  void testICONST_M1() {
    interpreter.visitICONST_M1(new ICONST_M1(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(-1), frame.peek())
  }

  @Test
  void testIADD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.visitIADD(new IADD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testISUB() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.visitISUB(new ISUB(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIFICMPLT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPLT(new IF_ICMPLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPLT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPLT(new IF_ICMPLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFEQ_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFEQ(new IFEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFEQ_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFEQ(new IFEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }  

  @Test
  void testIFNE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFNE(new IFNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFNE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFNE(new IFNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFGE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFGE(new IFGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFGE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(-1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFGE(new IFGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFGT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFGT(new IFGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFGT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFGT(new IFGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFLE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFLE(new IFLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFLE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(-1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFLE(new IFLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }       
  
  @Test
  void testIFLT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(-1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFLT(new IFLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFLT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFLT(new IFLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  } 

  @Test
  void testIFICMEQ_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPEQ(new IF_ICMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPEQ_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPEQ(new IF_ICMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  } 

  @Test
  void testIFICMNE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPNE(new IF_ICMPNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPNE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPNE(new IF_ICMPNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFICMLE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPLE(new IF_ICMPLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPLE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPLE(new IF_ICMPLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFICMGT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPGT(new IF_ICMPGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPGT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPGT(new IF_ICMPGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFICMGE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1))
    interpreter.visitIF_ICMPGE(new IF_ICMPGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPGE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPGE(new IF_ICMPGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFACMPEQ_true() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    frame.push(v)
    frame.push(v)
    interpreter.setNext(new SPECIAL(1)) 
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPEQ(new IF_ACMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFACMPEQ_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new ObjectValue(1))
    frame.push(new ObjectValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPEQ(new IF_ACMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFACMPNE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new ObjectValue(1))
    frame.push(new ObjectValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPNE(new IF_ACMPNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFACMPNE_false() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    frame.push(v)
    frame.push(v)
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPEQ(new IF_ACMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testGetValueObject_pass() {
    Frame frame = interpreter.getCurrentFrame()
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    frame.push(obj)
    interpreter.visitGETVALUE_Object(new GETVALUE_Object(10, "", false))
    assertEquals(obj, frame.peek())
  }

  @Test
  void testGetValueObject_failString() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_Object(new GETVALUE_Object(10, "A", true))
    assertEquals(new StringValue("A", 10), frame.peek())
  }

  @Test
  void testGetValueObject_failObj() {
    Frame frame = interpreter.getCurrentFrame()
    ObjectValue obj = new ObjectValue(1)
    frame.push(obj)
    interpreter.visitGETVALUE_Object(new GETVALUE_Object(10, "", false))
    ObjectValue exp = new ObjectValue(1)
    exp.setAddress(10)
    assertEquals(exp, frame.peek())
  }

  @Test
  void testGetValueObject_failNull() {
    Frame frame = interpreter.getCurrentFrame()
    ObjectValue obj = new ObjectValue(1)
    frame.push(obj)
    interpreter.visitGETVALUE_Object(new GETVALUE_Object(0, "", false))
    assertEquals(ObjectValue.NULL, frame.peek())
  }

  @Test
  void testGetValueInt_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_int(new GETVALUE_int(1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueInt_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_int(new GETVALUE_int(1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueLong_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitGETVALUE_long(new GETVALUE_long(1L))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testGetValueLong_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(PlaceHolder.instance)
    interpreter.visitGETVALUE_long(new GETVALUE_long(1L))
    assertEquals(new LongValue(1L), frame.peek2())
  }


  @Test
  void testGetValueShort_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_short(new GETVALUE_short((short)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueShort_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_short(new GETVALUE_short((short)1))
    assertEquals(new IntValue(1), frame.peek())
  }  

  @Test
  void testGetValueChar_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_char(new GETVALUE_char((char)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueChar_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_char(new GETVALUE_char((char)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueBoolean_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_boolean(new GETVALUE_boolean(true))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueBoolean_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_boolean(new GETVALUE_boolean(false))
    assertEquals(new IntValue(0), frame.peek())
  }

  @Test
  void testGetValueByte_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_byte(new GETVALUE_byte((byte)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueByte_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_byte(new GETVALUE_byte((byte)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueDouble_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_double(new GETVALUE_double(1.0D))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testGetValueDouble_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitGETVALUE_double(new GETVALUE_double(1.0D))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testGetValueFloat_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_float(new GETVALUE_float(1.0F))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testGetValueFloat_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(1.0F))
    interpreter.visitGETVALUE_float(new GETVALUE_float(1.0F))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testIFNULL_pass() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    frame.push(v)
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFNULL( new IFNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFNULL_fail() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    v.setAddress(10)
    frame.push(v)
    interpreter.visitIFNULL( new IFNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFNONNULL_pass() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    v.setAddress(10)
    frame.push(v)
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFNONNULL( new IFNONNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFNONNULL_fail() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    frame.push(v)
    interpreter.visitIFNONNULL( new IFNONNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testCASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitCASTORE(new CASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testCALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitCALOAD(new CALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testBASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitBASTORE(new BASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testBALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitBALOAD(new BALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testSASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitSASTORE(new SASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testSALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitSALOAD(new SALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testCALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitCALOAD(new CALOAD(0, 0))

    IntValue result = (IntValue) frame.peek()
    assertEquals(2, result.concrete)
    println(result.getSymbolic()) // TODO: how to test this?

    assertEquals(1, history.getHistory().size())
    BranchElement branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
  }

  @Test
  void testLALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new LongValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitLALOAD(new LALOAD(0, 0))
    assertEquals(new LongValue(2), frame.peek2())
  }

  @Test
  void testLASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push2(new LongValue(2)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitLASTORE(new LASTORE(0, 0))
    assertEquals(new LongValue(2), v.getFields()[0])
  }

  @Test
  void testLALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new LongValue(2))
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitLALOAD(new LALOAD(0, 0))

    LongValue result = (LongValue) frame.peek2()
    assertEquals(2L, result.concrete)
    println(result.getSymbolic()) // TODO: how to test this?

    assertEquals(1, history.getHistory().size())
    BranchElement branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
  }
  @Test
  void testIASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitIASTORE(new IASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testIALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitIALOAD(new IALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testIALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitIALOAD(new IALOAD(0, 0))

    IntValue result = (IntValue) frame.peek()
    assertEquals(2, result.concrete)
    println(result.getSymbolic()) // TODO: how to test this?

    assertEquals(1, history.getHistory().size())
    BranchElement branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
  }

  @Test
  void testAASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitAASTORE(new AASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testAALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    ObjectValue val = new ObjectValue(0, 1)
    v.setField(0, val)
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitAALOAD(new AALOAD(0, 0))
    assertEquals(val, frame.peek())
  }

  @Test
  void testAALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    ObjectValue val = new ObjectValue(0, 1)
    v.setField(0, val)
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitAALOAD(new AALOAD(0, 0))

    ObjectValue result = (ObjectValue) frame.peek()
    assertEquals(val.address, result.address)
    println(result.getSymbolic()) // TODO: how to test this?
  }  

  @Test
  void testLDC_int() {
    IntValue x = new IntValue(1)
    interpreter.visitLDC_int(new LDC_int(0, 0, 1))
    assertEquals(x, interpreter.getCurrentFrame().peek())
  }

  @Test
  void testLDC_long() {
    LongValue x = new LongValue(1L)
    interpreter.visitLDC_long(new LDC_long(0, 0, 1L))
    assertEquals(x, interpreter.getCurrentFrame().peek2())
  }

  @Test
  void testILOAD() {
    IntValue e = new IntValue(1)
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal(1, e)
    interpreter.visitILOAD(new ILOAD(0, 0, 1))
    assertEquals(e, frame.peek())
  }

  @Test
  void testISTORE() {
    IntValue e = new IntValue(1)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(e)
    interpreter.visitISTORE(new ISTORE(0, 0, 1))
    assertEquals(e, frame.getLocal(1))
  }

  @Test
  void testACONST_NULL() {
    interpreter.visitACONST_NULL(new ACONST_NULL(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(ObjectValue.NULL, frame.peek())
  }

  @Test
  void testALOAD() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal(0, obj)

    interpreter.visitALOAD(new ALOAD(0, 0, 0))
    assertEquals(obj, frame.peek())
  }

  @Test
  void testANEWARRAY() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitANEWARRAY(new ANEWARRAY(0, 0, "MyClass"))

    def obj = frame.peek()
    assertTrue(obj instanceof ObjectValue)
    ObjectValue o = (ObjectValue)obj
    assertEquals(1, o.fields.length)
  }

  @Test
  void testARETURN() {
    Frame frame = interpreter.getCurrentFrame()
    ObjectValue obj = new ObjectValue(1)
    frame.push(obj)
    interpreter.visitARETURN(new ARETURN(0, 0))
    assertEquals(obj, frame.ret)
  }

  @Test
  void testARRAYLENGTH() {
    Frame frame = interpreter.getCurrentFrame()
    ObjectValue obj = new ObjectValue(2)
    frame.push(obj)
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitARRAYLENGTH(new ARRAYLENGTH(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testD2F() {
    Frame frame = interpreter.getCurrentFrame()
    Value obj = new DoubleValue(2.0D)
    frame.push2(obj)
    interpreter.visitD2F(new D2F(0, 0))
    assertEquals(new FloatValue(2.0F), frame.peek())
  }

  @Test
  void testD2I() {
    Frame frame = interpreter.getCurrentFrame()
    Value obj = new DoubleValue(2.0D)
    frame.push2(obj)
    interpreter.visitD2I(new D2I(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testD2L() {
    Frame frame = interpreter.getCurrentFrame()
    Value obj = new DoubleValue(2.0D)
    frame.push2(obj)
    interpreter.visitD2L(new D2L(0, 0))
    assertEquals(new LongValue(2), frame.peek2())
  }

  @Test
  void testASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    ObjectValue obj = new ObjectValue(1)
    frame.push(obj)
    interpreter.visitASTORE(new ASTORE(0, 0, 0))
    assertEquals(obj, frame.getLocal(0))
  }

  @Test
  void testDADD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(1.0D))
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDADD(new DADD(0, 0))
    assertEquals(new DoubleValue(1.0D + 1.0D), frame.peek2())
  }

  @Test
  void testDALOAD() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    obj.setField(0, new DoubleValue(1.0D))
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitDALOAD(new DALOAD(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testDASTORE() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    obj.setField(0, new DoubleValue(0.0D))
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(0))
    frame.push2(new DoubleValue(2.0D))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitDASTORE(new DASTORE(0, 0))
    assertEquals(new DoubleValue(2.0D), obj.getField(0))
  }

  @Test
  void testDCMPG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    frame.push2(new DoubleValue(3.0D))
    interpreter.visitDCMPG(new DCMPG(0, 0))
    assertEquals(new IntValue(-1), frame.peek())
  }

  @Test
  void testDCMPL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    frame.push2(new DoubleValue(3.0D))
    interpreter.visitDCMPL(new DCMPL(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testDCONST_0() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitDCONST_0(new DCONST_0(0, 0))
    assertEquals(new DoubleValue(0.0D), frame.peek2())
  }

  @Test
  void testDCONST_1() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitDCONST_1(new DCONST_1(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testDDIV() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDDIV(new DDIV(0, 0))
    assertEquals(new DoubleValue(2.0D), frame.peek2())
  }

  @Test
  void testDLOAD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal2(0 , new DoubleValue(2.0D))
    interpreter.visitDLOAD(new DLOAD(0, 0, 0))
    assertEquals(new DoubleValue(2.0D), frame.peek2())
  }

  @Test
  void testDSTORE() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    interpreter.visitDSTORE(new DSTORE(0, 0, 0))
    assertEquals(new DoubleValue(2.0D), frame.getLocal2(0))
  }

  @Test
  void testDMUL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDMUL(new DMUL(0, 0))
    assertEquals(new DoubleValue(2.0D), frame.peek2())
  }

  @Test
  void testDNEG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    interpreter.visitDNEG(new DNEG(0, 0))
    assertEquals(new DoubleValue(-2.0D), frame.peek2())
  }

  @Test
  void testDREM() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(1.0D))
    frame.push2(new DoubleValue(2.0D))
    interpreter.visitDREM(new DREM(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testDRET() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    interpreter.visitDRETURN(new DRETURN(0, 0))
    assertEquals(new DoubleValue(2.0D), frame.ret)
  }

  @Test
  void testDSUB() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDSUB(new DSUB(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testDUP() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitDUP(new DUP(0, 0))
    assertEquals(new IntValue(1), frame.peek())
    frame.pop()
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testDUP2() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDUP2(new DUP2(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
    frame.pop2()
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testDUP2_X1() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDUP2_X1(new DUP2_X1(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.pop2())
    assertEquals(new IntValue(1), frame.pop())
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testSWAP() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(2))
    interpreter.visitSWAP(new SWAP(0, 0))
    assertEquals(new IntValue(1), frame.pop())
    assertEquals(new IntValue(2), frame.pop())
  }

  @Test
  void testDUP2_X2() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(2.0D))
    frame.push2(new DoubleValue(1.0D))
    interpreter.visitDUP2_X2(new DUP2_X2(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.pop2())
    assertEquals(new DoubleValue(2.0D), frame.pop2())
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testDUP_X1() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.visitDUP_X1(new DUP_X1(0, 0))
    assertEquals(new IntValue(1), frame.pop())
    assertEquals(new IntValue(2), frame.pop())
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testDUP_X2() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new DoubleValue(1.0D))
    frame.push(new IntValue(1))
    interpreter.visitDUP_X2(new DUP_X2(0, 0))
    assertEquals(new IntValue(1), frame.pop())
    assertEquals(new DoubleValue(1.0D), frame.pop2())
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testF2D() {
    Frame frame = interpreter.getCurrentFrame()
    Value obj = new FloatValue(2.0F)
    frame.push(obj)
    interpreter.visitF2D(new F2D(0, 0))
    assertEquals(new DoubleValue(2.0D), frame.peek2())
  }

  @Test
  void testF2I() {
    Frame frame = interpreter.getCurrentFrame()
    Value obj = new FloatValue(2.0F)
    frame.push(obj)
    interpreter.visitF2I(new F2I(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testF2L() {
    Frame frame = interpreter.getCurrentFrame()
    Value obj = new FloatValue(2.0F)
    frame.push(obj)
    interpreter.visitF2L(new F2L(0, 0))
    assertEquals(new LongValue(2), frame.peek2())
  }

  @Test
  void testFADD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(1.0F))
    frame.push(new FloatValue(1.0F))
    interpreter.visitFADD(new FADD(0, 0))
    assertEquals(new FloatValue(2.0F), frame.peek())
  }

  @Test
  void testFALOAD() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    obj.setField(0, new FloatValue(1.0F))
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitFALOAD(new FALOAD(0, 0))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testFASTORE() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    obj.setField(0, new FloatValue(0.0F))
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(0))
    frame.push(new FloatValue(2.0F))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitFASTORE(new FASTORE(0, 0))
    assertEquals(new FloatValue(2.0F), obj.getField(0))
  }

  @Test
  void testFCMPG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    frame.push(new FloatValue(3.0F))
    interpreter.visitFCMPG(new FCMPG(0, 0))
    assertEquals(new IntValue(-1), frame.peek())
  }

  @Test
  void testLCMPG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(3L))
    interpreter.visitLCMP(new LCMP(0, 0))
    assertEquals(new IntValue(-1), frame.peek())
  }

  @Test
  void testFCMPL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    frame.push(new FloatValue(3.0F))
    interpreter.visitFCMPL(new FCMPL(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testFCONST_0() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitFCONST_0(new FCONST_0(0, 0))
    assertEquals(new FloatValue(0.0F), frame.peek())
  }

  @Test
  void testFCONST_1() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitFCONST_1(new FCONST_1(0, 0))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testFCONST_2() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitFCONST_2(new FCONST_2(0, 0))
    assertEquals(new FloatValue(2.0F), frame.peek())
  }

  @Test
  void testFDIV() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    frame.push(new FloatValue(1.0F))
    interpreter.visitFDIV(new FDIV(0, 0))
    assertEquals(new FloatValue(2.0F), frame.peek())
  }

  @Test
  void testFLOAD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal(0 , new FloatValue(2.0F))
    interpreter.visitFLOAD(new FLOAD(0, 0, 0))
    assertEquals(new FloatValue(2.0F), frame.peek())
  }

  @Test
  void testFSTORE() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    interpreter.visitFSTORE(new FSTORE(0, 0, 0))
    assertEquals(new FloatValue(2.0F), frame.getLocal(0))
  }

  @Test
  void testFMUL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    frame.push(new FloatValue(1.0F))
    interpreter.visitFMUL(new FMUL(0, 0))
    assertEquals(new FloatValue(2.0F), frame.peek())
  }

  @Test
  void testFNEG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    interpreter.visitFNEG(new FNEG(0, 0))
    assertEquals(new FloatValue(-2.0F), frame.peek())
  }

  @Test
  void testFREM() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(1.0F))
    frame.push(new FloatValue(2.0F))
    interpreter.visitFREM(new FREM(0, 0))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testFRET() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    interpreter.visitFRETURN(new FRETURN(0, 0))
    assertEquals(new FloatValue(2.0F), frame.ret)
  }

  @Test
  void testFSUB() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(2.0F))
    frame.push(new FloatValue(1.0F))
    interpreter.visitFSUB(new FSUB(0, 0))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testGETFIELD() {
    when(classDepot.getFieldIndex("MyClass", "myField")).thenReturn(0)
    when(classDepot.nFields("MyClass")).thenReturn(1)
    int classIdx = classNames.get("MyClass")
    ObjectInfo oi = classNames.get(classIdx)
    int fIdx = oi.getIdx("myField", false)
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    obj.setField(0, new FloatValue(1.0F))
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitGETFIELD(new GETFIELD(0, 0, classIdx, fIdx, "F"))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testGETSTATIC() {
    when(classDepot.getStaticFieldIndex("MyClass", "myField")).thenReturn(0)
    when(classDepot.nStaticFields("MyClass")).thenReturn(1)
    int classIdx = classNames.get("MyClass")
    ObjectInfo oi = classNames.get(classIdx)
    int fIdx = oi.getIdx("myField", true)
    oi.setStaticField(fIdx, new FloatValue(1.0F))
    Frame frame = interpreter.getCurrentFrame()
    interpreter.setNext(new SPECIAL(0)) // exception handling
    
    interpreter.visitGETSTATIC(new GETSTATIC(0, 0, classIdx, fIdx, "F"))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testPUTFIELD() {
    when(classDepot.getFieldIndex("MyClass", "myField")).thenReturn(0)
    when(classDepot.nFields("MyClass")).thenReturn(1)
    int classIdx = classNames.get("MyClass")
    ObjectInfo oi = classNames.get(classIdx)
    int fIdx = oi.getIdx("myField", false)
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new FloatValue(1.0F))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitPUTFIELD(new PUTFIELD(0, 0, classIdx, fIdx, "F"))
    assertEquals(new FloatValue(1.0F), obj.getField(0))
  }

  @Test
  void testPUTSTATIC() {
    when(classDepot.getStaticFieldIndex("MyClass", "myField")).thenReturn(0)
    when(classDepot.nStaticFields("MyClass")).thenReturn(1)
    int classIdx = classNames.get("MyClass")
    ObjectInfo oi = classNames.get(classIdx)
    int fIdx = oi.getIdx("myField", true)
    oi.setStaticField(fIdx, new FloatValue(1.0F))
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new FloatValue(1.0F))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitPUTSTATIC(new PUTSTATIC(0, 0, classIdx, fIdx, "F"))
    assertEquals(new FloatValue(1.0F), oi.getStaticField(fIdx))
  }

  @Test
  void testIAND() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.visitIAND(new IAND(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIOR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.visitIOR(new IOR(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIXOR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.visitIXOR(new IXOR(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIDIV() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(0))
    interpreter.visitIDIV(new IDIV(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testIREM() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(3))
    interpreter.setNext(new SPECIAL(0))
    interpreter.visitIREM(new IREM(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testIRETURN() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    interpreter.visitIRETURN(new IRETURN(0, 0))
    assertEquals(new IntValue(2), frame.getRet())
  }

  @Test
  void testISHL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.visitISHL(new ISHL(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testISHR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.visitISHR(new ISHR(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIUSHR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.visitIUSHR(new IUSHR(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testLADD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    frame.push2(new LongValue(1L))
    interpreter.visitLADD(new LADD(0, 0))
    assertEquals(new LongValue(2L), frame.peek2())
  }

  @Test
  void testLSUB() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(1L))
    interpreter.visitLSUB(new LSUB(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testLAND() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    frame.push2(new LongValue(1L))
    interpreter.visitLAND(new LAND(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testLOR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    frame.push2(new LongValue(0L))
    interpreter.visitLOR(new LOR(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testLXOR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    frame.push2(new LongValue(0L))
    interpreter.visitLXOR(new LXOR(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testLMUL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(1L))
    interpreter.visitLMUL(new LMUL(0, 0))
    assertEquals(new LongValue(2L), frame.peek2())
  }

  @Test
  void testLNEG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitLNEG(new LNEG(0, 0))
    assertEquals(new LongValue(-1L), frame.peek2())
  }

  @Test
  void testLDIV() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(1L))
    interpreter.setNext(new SPECIAL(0))
    interpreter.visitLDIV(new LDIV(0, 0))
    assertEquals(new LongValue(2L), frame.peek2())
  }

  @Test
  void testLREM() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(3L))
    interpreter.setNext(new SPECIAL(0))
    interpreter.visitLREM(new LREM(0, 0))
    assertEquals(new LongValue(2L), frame.peek2())
  }

  @Test
  void testLRETURN() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    interpreter.visitLRETURN(new LRETURN(0, 0))
    assertEquals(new LongValue(2L), frame.getRet())
  }

  @Test
  void testLSHL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    frame.push2(new LongValue(1L))
    interpreter.visitLSHL(new LSHL(0, 0))
    assertEquals(new LongValue(2L), frame.peek2())
  }

  @Test
  void testLSHR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(1L))
    interpreter.visitLSHR(new LSHR(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testLUSHR() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    frame.push2(new LongValue(1L))
    interpreter.visitLUSHR(new LUSHR(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testLSTORE() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(2L))
    interpreter.visitLSTORE(new LSTORE(0, 0, 0))
    assertEquals(new LongValue(2L), frame.getLocal2(0))
  }

  @Test
  void testLLOAD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal2(0, new LongValue(2L))
    interpreter.visitLLOAD(new LLOAD(0, 0, 0))
    assertEquals(new LongValue(2L), frame.peek2())
  }

  @Test
  void testIINC() {
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal(0, new IntValue(0))
    interpreter.visitIINC(new IINC(0, 0, 0, 2))
    assertEquals(new IntValue(2), frame.getLocal(0))
  }

  @Test
  void testIMUL() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.visitIMUL(new IMUL(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testINEG() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitINEG(new INEG(0, 0))
    assertEquals(new IntValue(-1), frame.peek())
  }

  @Test
  void testI2B() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitI2B(new I2B(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testI2C() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitI2C(new I2C(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testI2S() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitI2S(new I2S(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testATHROW() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitATHROW(new ATHROW(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testI2F() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitI2F(new I2F(0, 0))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testI2L() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitI2L(new I2L(0, 0))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testI2D() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitI2D(new I2D(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testL2F() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1))
    interpreter.visitL2F(new L2F(0, 0))
    assertEquals(new FloatValue(1.0F), frame.peek())
  }

  @Test
  void testL2I() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitL2I(new L2I(0, 0))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testL2D() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitL2D(new L2D(0, 0))
    assertEquals(new DoubleValue(1.0D), frame.peek2())
  }

  @Test
  void testINVOKEVIRTUAL() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(1))
    interpreter.visitINVOKEVIRTUAL(new INVOKEVIRTUAL(0, 0, 
      "MyClass", "MyMethod", "(I)I"))
    Frame newFrame = interpreter.getCurrentFrame()
    assertEquals(obj, newFrame.getLocal(0))
    assertEquals(new IntValue(1), newFrame.getLocal(1))
    assertEquals(PlaceHolder.instance, newFrame.getRet())
  }

  @Test
  void testINVOKESTATIC() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitINVOKESTATIC(new INVOKESTATIC(0, 0, 
      "MyClass", "MyMethod", "(J)V"))
    Frame newFrame = interpreter.getCurrentFrame()
    assertEquals(new LongValue(1L), newFrame.getLocal(0))
  }


  @Test
  void testINVOKEINTERFACE() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(1))
    interpreter.visitINVOKEINTERFACE(new INVOKEINTERFACE(0, 0, 
      "MyClass", "MyMethod", "(I)I"))
    Frame newFrame = interpreter.getCurrentFrame()
    assertEquals(obj, newFrame.getLocal(0))
    assertEquals(new IntValue(1), newFrame.getLocal(1))
    assertEquals(PlaceHolder.instance, newFrame.getRet())
  }
  
  @Test
  void testINVOKESPECIAL() {
    ObjectValue obj = new ObjectValue(1)
    obj.setAddress(10)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(obj)
    frame.push(new IntValue(1))
    interpreter.visitINVOKESPECIAL(new INVOKESPECIAL(0, 0, 
      "MyClass", "<init>", "(I)V"))
    Frame newFrame = interpreter.getCurrentFrame()
    assertEquals(obj, newFrame.getLocal(0))
    assertEquals(new IntValue(1), newFrame.getLocal(1))
  }
  
  @Test
  void testMONITOR() {
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitMONITORENTER(new MONITORENTER(0, 0))
    interpreter.visitMONITOREXIT(new MONITOREXIT(0, 0))
  }

  @Test
  void testNEW() {
    when(classDepot.getFieldIndex("MyClass", "myField")).thenReturn(0)
    when(classDepot.nFields("MyClass")).thenReturn(1)
    int classIdx = classNames.get("MyClass")
    ObjectInfo oi = classNames.get(classIdx)
    interpreter.visitNEW(new NEW(0, 0, "MyClass", classIdx))

    assertTrue(interpreter.getCurrentFrame().peek() instanceof ObjectValue)
  }

  @Test
  void testNEWARRAY() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitNEWARRAY(new NEWARRAY(0, 0))

    def v = frame.peek()
    assertTrue(v instanceof ObjectValue)
    ObjectValue obj = (ObjectValue) v
    assertEquals(1, obj.getFields().length)
  }

  @Test
  void testMULTIANEWARRAY() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(2))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitMULTIANEWARRAY(new MULTIANEWARRAY(0, 0, "I", 2))

    def v = frame.peek()
    assertTrue(v instanceof ObjectValue)
    ObjectValue obj = (ObjectValue) v
    assertEquals(2, obj.getFields().length)
  }

  @Test
  void testINVOKEMETHOD_END() {
    Frame m = new Frame(1)
    m.setRet(new IntValue(1))
    interpreter.getStack().push(m)
    interpreter.visitINVOKEMETHOD_END(new INVOKEMETHOD_END())

    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testLDC_Object() {
    interpreter.visitLDC_Object(new LDC_Object(0, 0, 10))
    Frame frame = interpreter.getCurrentFrame()
    assertTrue(frame.peek() instanceof ObjectValue)
    assertTrue(frame.peek() != ObjectValue.NULL)
  }

  @Test
  void testINSTANCEOF() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new ObjectValue(1))
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitINSTANCEOF(new INSTANCEOF(0, 0, "SomeClass"))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testLDC_double() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitLDC_double(new LDC_double(0, 0, 0.0D))
    assertEquals(new DoubleValue(0.0D), frame.peek2())
  }

  @Test
  void testLDC_float() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitLDC_float(new LDC_float(0, 0, 0.0F))
    assertEquals(new FloatValue(0.0F), frame.peek())
  }

  @Test
  void testTABLESWITCH() {
    int[] tab = new int[1]
    tab[0] = 1
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitTABLESWITCH(new TABLESWITCH(0, 0, 0, 1, 1, tab))
  }

  @Test
  void testLOOKUP(){
    int[] x = new int[1]
    x[0] = 1
    int[] y = new int[1]
    y[0] = 2
    def insn = new LOOKUPSWITCH(0, 0, 1, x, y)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitLOOKUPSWITCH(insn)
  }

  @Test
  void testPOP() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitPOP(new POP(0, 0))
    assertEquals(0, frame.getStackSize())
  }

  @Test
  void testPOP2() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitPOP2(new POP2(0, 0))
    assertEquals(0, frame.getStackSize())
  }

  @Test
  void testSIPUSH() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitSIPUSH(new SIPUSH(0, 0, 1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testBIPUSH() {
    Frame frame = interpreter.getCurrentFrame()
    interpreter.visitBIPUSH(new BIPUSH(0, 0, 1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testINVOKEMETHOD_EXCEPTION() {
    Frame m = new Frame(1)
    interpreter.getStack().push(m)
    interpreter.visitINVOKEMETHOD_EXCEPTION(new INVOKEMETHOD_EXCEPTION())
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(PlaceHolder.instance, frame.peek())
  }
} 