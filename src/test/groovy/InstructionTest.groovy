package janala.logger.inst

import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.junit.Before
import org.junit.Test
import janala.interpreters.PrintInterpreter

import groovy.transform.CompileStatic

@CompileStatic
class InstrutionTest {
  private IVisitor visitor
  private IVisitor printer

  private testToString(Object o) {
    String s = o.toString()
    assertTrue(s.contains(o.getClass().getSimpleName()))
  }

  @Before
  void setup() {
    visitor = mock(IVisitor.class)
    printer = new PrintInterpreter()
  }

  @Test
  void testAALOAD() {
    def insn = new AALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitAALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testAASTORE() {
    def insn = new AASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitAASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testACONST_NULL() {
    def insn = new ACONST_NULL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitACONST_NULL(insn)
    insn.visit(printer)
  }

  @Test
  void testALOAD() {
    def insn = new ALOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testANEWARRAY() {
    def insn = new ANEWARRAY(0, 0, "java/lang/String")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitANEWARRAY(insn)
    insn.visit(printer)
  }

  @Test
  void testARETURN() {
    def insn = new ARETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitARETURN(insn)
    insn.visit(printer)
  }

  @Test
  void testARRAYLENGTH() {
    def insn = new ARRAYLENGTH(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitARRAYLENGTH(insn)
    insn.visit(printer)
  }

  @Test
  void testASTORE() {
    def insn = new ASTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testATHROW() {
    def insn = new ATHROW(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitATHROW(insn)
    insn.visit(printer)
  }

  @Test
  void testBALOAD() {
    def insn = new BALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitBALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testBASTORE() {
    def insn = new BASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitBASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testBIPUSH() {
    def insn = new BIPUSH(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitBIPUSH(insn)
    insn.visit(printer)
  }

  @Test
  void testCALOAD() {
    def insn = new CALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitCALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testCASTORE() {
    def insn = new CASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitCASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testCHECKCAST() {
    def insn = new CHECKCAST(0, 0, "java/lang/String")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitCHECKCAST(insn)
    insn.visit(printer)
  }
  
  @Test
  void testD2F() {
    def insn = new D2F(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitD2F(insn)
    insn.visit(printer)
  }

  @Test
  void testD2I() {
    def insn = new D2I(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitD2I(insn)
    insn.visit(printer)
  }

  @Test
  void testD2L() {
    def insn = new D2L(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitD2L(insn)
    insn.visit(printer)
  }
   
  @Test
  void testDADD() {
    def insn = new DADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDADD(insn)
    insn.visit(printer)
  }
   
  @Test
  void testDALOAD() {
    def insn = new DALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testDASTORE() {
    def insn = new DASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testDCMPG() {
    def insn = new DCMPG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCMPG(insn)
    insn.visit(printer)
  }

  @Test
  void testDCMPL() {
    def insn = new DCMPL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCMPL(insn)
    insn.visit(printer)
  }

  @Test
  void testDCONST_0() {
    def insn = new DCONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCONST_0(insn)
    insn.visit(printer)
  }
      
  @Test
  void testDCONST_1() {
    def insn = new DCONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCONST_1(insn)
    insn.visit(printer)
  }

  @Test
  void testDDIV() {
    def insn = new DDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDDIV(insn)
    insn.visit(printer)
  }
  
  @Test
  void testDLOAD() {
    def insn = new DLOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDLOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testDMUL() {
    def insn = new DMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDMUL(insn)
    insn.visit(printer)
  }

  @Test
  void testDNEG() {
    def insn = new DNEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDNEG(insn)
    insn.visit(printer)
  }

  @Test
  void testDREM() {
    def insn = new DREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDREM(insn)
    insn.visit(printer)
  }

  @Test
  void testDRETURN() {
    def insn = new DRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDRETURN(insn)
    insn.visit(printer)
  }

  @Test
  void testDSTORE() {
    def insn = new DSTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDSTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testDSUB() {
    def insn = new DSUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDSUB(insn)
    insn.visit(printer)
  }

  @Test
  void testDUP() {
    def insn = new DUP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP(insn)
    insn.visit(printer)
  }

  @Test
  void testDUP2() {
    def insn = new DUP2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP2(insn)
    insn.visit(printer)
  }

  @Test
  void testDUP2_X1() {
    def insn = new DUP2_X1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP2_X1(insn)
    insn.visit(printer)
  }

  @Test
  void testDUP2_X2() {
    def insn = new DUP2_X2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP2_X2(insn)
    insn.visit(printer)
  }

  @Test
  void testDUP_X1() {
    def insn = new DUP_X1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP_X1(insn)
    insn.visit(printer)
  }

  @Test
  void testDUP_X2() {
    def insn = new DUP_X2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP_X2(insn)
    insn.visit(printer)
  }

  @Test
  void testF2D() {
    def insn = new F2D(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitF2D(insn)
    insn.visit(printer)
  }

  @Test
  void testF2I() {
    def insn = new F2I(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitF2I(insn)
    insn.visit(printer)
  }

  @Test
  void testF2L() {
    def insn = new F2L(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitF2L(insn)
    insn.visit(printer)
  }

  @Test
  void testFADD() {
    def insn = new FADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFADD(insn)
    insn.visit(printer)
  }

  @Test
  void testFALOAD() {
    def insn = new FALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testFASTORE() {
    def insn = new FASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testFCMPG() {
    def insn = new FCMPG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCMPG(insn)
    insn.visit(printer)
  }

  @Test
  void testFCMPL() {
    def insn = new FCMPL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCMPL(insn)
    insn.visit(printer)
  }

  @Test
  void testFCONST_0() {
    def insn = new FCONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCONST_0(insn)
    insn.visit(printer)
  }

  @Test
  void testFCONST_1() {
    def insn = new FCONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCONST_1(insn)
    insn.visit(printer)
  }

  @Test
  void testFCONST_2() {
    def insn = new FCONST_2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCONST_2(insn)
    insn.visit(printer)
  }

  @Test
  void testFDIV() {
    def insn = new FDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFDIV(insn)
    insn.visit(printer)
  }

  @Test
  void testFLOAD() {
    def insn = new FLOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFLOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testFSTORE() {
    def insn = new FSTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFSTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testFMUL() {
    def insn = new FMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFMUL(insn)
    insn.visit(printer)
  }

  @Test
  void testFNEG() {
    def insn = new FNEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFNEG(insn)
    insn.visit(printer)
  }

  @Test
  void testFREM() {
    def insn = new FREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFREM(insn)
    insn.visit(printer)
  }

  @Test
  void testFRETURN() {
    def insn = new FRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFRETURN(insn)
    insn.visit(printer)
  }

  @Test
  void testFSUB() {
    def insn = new FSUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFSUB(insn)
    insn.visit(printer)
  }

  @Test
  void testGETFIELD() {
    def insn = new GETFIELD(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETFIELD(insn)
    insn.visit(printer)
  }

  @Test
  void testGETSTATIC() {
    def insn = new GETSTATIC(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETSTATIC(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_Object() {
    def insn = new GETVALUE_Object(0, "a", true)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_Object(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_boolean() {
    def insn = new GETVALUE_boolean(false)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_boolean(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_byte() {
    def insn = new GETVALUE_byte((byte)1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_byte(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_char() {
    def insn = new GETVALUE_char((char)1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_char(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_double() {
    def insn = new GETVALUE_double(1.0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_double(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_float() {
    def insn = new GETVALUE_float(1.0F)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_float(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_int() {
    def insn = new GETVALUE_int(1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_int(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_long() {
    def insn = new GETVALUE_long(1L)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_long(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_short() {
    def insn = new GETVALUE_short((short)1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_short(insn)
    insn.visit(printer)
  }

  @Test
  void testGETVALUE_void() {
    def insn = new GETVALUE_void()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_void(insn)
    insn.visit(printer)
  }

  @Test
  void testGOTO() {
    def insn = new GOTO(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGOTO(insn)
    insn.visit(printer)
  }

  @Test
  void testI2B() {
    def insn = new I2B(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2B(insn)
    insn.visit(printer)
  }

  @Test
  void testI2C() {
    def insn = new I2C(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2C(insn)
    insn.visit(printer)
  }

  @Test
  void testI2D() {
    def insn = new I2D(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2D(insn)
    insn.visit(printer)
  }

  @Test
  void testI2F() {
    def insn = new I2F(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2F(insn)
    insn.visit(printer)
  }

  @Test
  void testI2L() {
    def insn = new I2L(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2L(insn)
    insn.visit(printer)
  }

  @Test
  void testI2S() {
    def insn = new I2S(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2S(insn)
    insn.visit(printer)
  }

  @Test
  void testIADD() {
    def insn = new IADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIADD(insn)
    insn.visit(printer)
  } 

  @Test
  void testIALOAD() {
    def insn = new IALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testIAND() {
    def insn = new IAND(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIAND(insn)
    insn.visit(printer)
  }

  @Test
  void testIASTORE() {
    def insn = new IASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_0() {
    def insn = new ICONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_0(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_1() {
    def insn = new ICONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_1(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_2() {
    def insn = new ICONST_2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_2(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_3() {
    def insn = new ICONST_3(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_3(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_4() {
    def insn = new ICONST_4(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_4(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_5() {
    def insn = new ICONST_5(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_5(insn)
    insn.visit(printer)
  }

  @Test
  void testICONST_M1() {
    def insn = new ICONST_M1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_M1(insn)
    insn.visit(printer)
  }

  @Test
  void testIDIV() {
    def insn = new IDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIDIV(insn)
    insn.visit(printer)
  }

  @Test
  void testIFEQ() {
    def insn = new IFEQ(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFEQ(insn)
    insn.visit(printer)
  }

  @Test
  void testIFGE() {
    def insn = new IFGE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFGE(insn)
    insn.visit(printer)
  }

  @Test
  void testIFGT() {
    def insn = new IFGT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFGT(insn)
    insn.visit(printer)
  }

  @Test
  void testIFLE() {
    def insn = new IFLE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFLE(insn)
    insn.visit(printer)
  }

  @Test
  void testIFLT() {
    def insn = new IFLT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFLT(insn)
    insn.visit(printer)
  }

  @Test
  void testIFNE() {
    def insn = new IFNE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFNE(insn)
    insn.visit(printer)
  }

  @Test
  void testIFNONNULL() {
    def insn = new IFNONNULL(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFNONNULL(insn)
    insn.visit(printer)
  }

  @Test
  void testIFNULL() {
    def insn = new IFNULL(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFNULL(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ACMPEQ() {
    def insn = new IF_ACMPEQ(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ACMPEQ(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ACMPNE() {
    def insn = new IF_ACMPNE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ACMPNE(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ICMPEQ() {
    def insn = new IF_ICMPEQ(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPEQ(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ICMPGE() {
    def insn = new IF_ICMPGE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPGE(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ICMPGT() {
    def insn = new IF_ICMPGT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPGT(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ICMPLE() {
    def insn = new IF_ICMPLE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPLE(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ICMPLT() {
    def insn = new IF_ICMPLT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPLT(insn)
    insn.visit(printer)
  }

  @Test
  void testIF_ICMPNE() {
    def insn = new IF_ICMPNE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPNE(insn)
    insn.visit(printer)
  }

  @Test
  void testIINC() {
    def insn = new IINC(0, 0, 1, 2)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIINC(insn)
    insn.visit(printer)
  }

  @Test
  void testILOAD() {
    def insn = new ILOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitILOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testIMUL() {
    def insn = new IMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIMUL(insn)
    insn.visit(printer)
  }

  @Test
  void testINEG() {
    def insn = new INEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINEG(insn)
    insn.visit(printer)
  }

  @Test
  void testINSTANCEOF() {
    def insn = new INSTANCEOF(0, 0, "java/lang/String")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINSTANCEOF(insn)
    insn.visit(printer)
  }

  @Test
  void testINVOKEINTERFACE() {
    def insn = new INVOKEINTERFACE(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEINTERFACE(insn)
    insn.visit(printer)
  }

  @Test
  void testINVOKEMETHOD_END() {
    def insn = new INVOKEMETHOD_END()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEMETHOD_END(insn)
    insn.visit(printer)
  }

  @Test
  void testINVOKEMETHOD_EXCEPTION() {
    def insn = new INVOKEMETHOD_EXCEPTION()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEMETHOD_EXCEPTION(insn)
    insn.visit(printer)
  }

  @Test
  void testINVOKESPECIAL() {
    def insn = new INVOKESPECIAL(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKESPECIAL(insn)
    insn.visit(printer)
  }

  @Test
  void testINVOKESTATIC() {
    def insn = new INVOKESTATIC(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKESTATIC(insn)
    insn.visit(printer)
  }

  @Test
  void testINVOKEVIRTUAL() {
    def insn = new INVOKEVIRTUAL(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEVIRTUAL(insn)
    insn.visit(printer)
  }

  @Test
  void testIOR() {
    def insn = new IOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIOR(insn)
    insn.visit(printer)
  }

  @Test
  void testIREM() {
    def insn = new IREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIREM(insn)
    insn.visit(printer)
  }

  @Test
  void testIRETURN() {
    def insn = new IRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIRETURN(insn)
    insn.visit(printer)
  }

  @Test
  void testISHL() {
    def insn = new ISHL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISHL(insn)
    insn.visit(printer)
  }

  @Test
  void testISHR() {
    def insn = new ISHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISHR(insn)
    insn.visit(printer)
  }

  @Test
  void testISTORE() {
    def insn = new ISTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testISUB() {
    def insn = new ISUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISUB(insn)
    insn.visit(printer)
  }

  @Test
  void testIUSHR() {
    def insn = new IUSHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIUSHR(insn)
    insn.visit(printer)
  }

  @Test
  void testIXOR() {
    def insn = new IXOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIXOR(insn)
    insn.visit(printer)
  }

  @Test
  void testJSR() {
    def insn = new JSR(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitJSR(insn)
    insn.visit(printer)
  }

  @Test
  void testL2D() {
    def insn = new L2D(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitL2D(insn)
    insn.visit(printer)
  }

  @Test
  void testL2F() {
    def insn = new L2F(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitL2F(insn)
    insn.visit(printer)
  }

  @Test
  void testL2I() {
    def insn = new L2I(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitL2I(insn)
    insn.visit(printer)
  }

  @Test
  void testLADD() {
    def insn = new LADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLADD(insn)
    insn.visit(printer)
  }

  @Test
  void testLALOAD() {
    def insn = new LALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testLAND() {
    def insn = new LAND(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLAND(insn)
    insn.visit(printer)
  }

  @Test
  void testLASTORE() {
    def insn = new LASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testLCMP() {
    def insn = new LCMP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLCMP(insn)
    insn.visit(printer)
  }

  @Test
  void testLCONST_0() {
    def insn = new LCONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLCONST_0(insn)
    insn.visit(printer)
  }

  @Test
  void testLCONST_1() {
    def insn = new LCONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLCONST_1(insn)
    insn.visit(printer)
  }

  @Test
  void testLDC_Object() {
    def insn = new LDC_Object(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_Object(insn)
    insn.visit(printer)
  }

  @Test
  void testLDC_String() {
    def insn = new LDC_String(0, 0, "a", 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_String(insn)
    insn.visit(printer)
  }

  @Test
  void testLDC_double() {
    def insn = new LDC_double(0, 0, 1.0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_double(insn)
    insn.visit(printer)
  }

  @Test
  void testLDC_float() {
    def insn = new LDC_float(0, 0, 1.0F)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_float(insn)
    insn.visit(printer)
  }

  @Test
  void testLDC_int() {
    def insn = new LDC_int(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_int(insn)
    insn.visit(printer)
  }

  @Test
  void testLDC_long() {
    def insn = new LDC_long(0, 0, 1L)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_long(insn)
    insn.visit(printer)
  }

  @Test
  void testLDIV() {
    def insn = new LDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDIV(insn)
    insn.visit(printer)
  }

  @Test
  void testLLOAD() {
    def insn = new LLOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLLOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testLMUL() {
    def insn = new LMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLMUL(insn)
    insn.visit(printer)
  }

  @Test
  void testLNEG() {
    def insn = new LNEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLNEG(insn)
    insn.visit(printer)
  }

  @Test
  void testLOOKUPSWITCH() {
    int[] x = new int[1]
    x[0] = 1
    int[] y = new int[1]
    y[0] = 2
    def insn = new LOOKUPSWITCH(0, 0, 1, x, y)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLOOKUPSWITCH(insn)
    insn.visit(printer)
  }

  @Test
  void testLOR() {
    def insn = new LOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLOR(insn)
    insn.visit(printer)
  }

  @Test
  void testLREM() {
    def insn = new LREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLREM(insn)
    insn.visit(printer)
  }

  @Test
  void testLRETURN() {
    def insn = new LRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLRETURN(insn)
    insn.visit(printer)
  }

  @Test
  void testLSHL() {
    def insn = new LSHL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSHL(insn)
    insn.visit(printer)
  }

  @Test
  void testLSHR() {
    def insn = new LSHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSHR(insn)
    insn.visit(printer)
  }

  @Test
  void testLSTORE() {
    def insn = new LSTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testLSUB() {
    def insn = new LSUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSUB(insn)
    insn.visit(printer)
  }

  @Test
  void testLUSHR() {
    def insn = new LUSHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLUSHR(insn)
    insn.visit(printer)
  }

  @Test
  void testLXOR() {
    def insn = new LXOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLXOR(insn)
    insn.visit(printer)
  }

  @Test
  void testMAKE_SYMBOLIC() {
    def insn = new MAKE_SYMBOLIC()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMAKE_SYMBOLIC(insn)
    insn.visit(printer)
  }

  @Test
  void testMONITORENTER() {
    def insn = new MONITORENTER(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMONITORENTER(insn)
    insn.visit(printer)
  }

  @Test
  void testMONITOREXIT() {
    def insn = new MONITOREXIT(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMONITOREXIT(insn)
    insn.visit(printer)
  }

  @Test
  void testMULTIANEWARRAY() {
    def insn = new MULTIANEWARRAY(0, 0, "I", 2)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMULTIANEWARRAY(insn)
    insn.visit(printer)
  }

  @Test
  void testNEW() {
    def insn = new NEW(0, 0, "java/lang/Integer", 2)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitNEW(insn)
    insn.visit(printer)
  }

  @Test
  void testNEWARRAY() {
    def insn = new NEWARRAY(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitNEWARRAY(insn)
    insn.visit(printer)
  }

  @Test
  void testNOP() {
    def insn = new NOP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitNOP(insn)
    insn.visit(printer)
  }

  @Test
  void testPOP() {
    def insn = new POP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPOP(insn)
    insn.visit(printer)
  }

  @Test
  void testPOP2() {
    def insn = new POP2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPOP2(insn)
    insn.visit(printer)
  }

  @Test
  void testPUTFIELD() {
    def insn = new PUTFIELD(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPUTFIELD(insn)
    insn.visit(printer)
  }

  @Test void testPUTSTATIC() {
    def insn = new PUTSTATIC(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPUTSTATIC(insn)
    insn.visit(printer)
  }

  @Test
  void testRET() {
    def insn = new RET(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitRET(insn)
    insn.visit(printer)
  }

  @Test
  void testRETURN() {
    def insn = new RETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitRETURN(insn)
    insn.visit(printer)
  }

  @Test
  void testSALOAD() {
    def insn = new SALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSALOAD(insn)
    insn.visit(printer)
  }

  @Test
  void testSASTORE() {
    def insn = new SASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSASTORE(insn)
    insn.visit(printer)
  }

  @Test
  void testSIPUSH() {
    def insn = new SIPUSH(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSIPUSH(insn)
    insn.visit(printer)
  }

  @Test
  void testSPECIAL() {
    def insn = new SPECIAL(0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSPECIAL(insn)
    insn.visit(printer)
  }

  @Test
  void testSWAP() {
    def insn = new SWAP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSWAP(insn)
    insn.visit(printer)
  }

  @Test
  void testTABLESWITCH() {
    int[] tab = new int[1]
    tab[0] = 1
    def insn = new TABLESWITCH(0, 0, 0, 1, 1, tab)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitTABLESWITCH(insn)
    insn.visit(printer)
  }
}