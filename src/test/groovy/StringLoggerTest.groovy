package janala.logger

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.junit.Assert.assertTrue

import org.junit.Before
import org.junit.Test
import groovy.transform.CompileStatic

@CompileStatic
class StringLoggerTest {
  private StringLogger logger

  @Before
  void setup() {
    logger = new StringLogger()
  }

  @Test
  void testLDC1() {
    logger.LDC(0, 0, 1)
    assertTrue(logger.log.contains("LDC"))
  }


  @Test
  void testLDC2() {
    logger.LDC(0, 0, 2L)
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC3(){
    logger.LDC(0, 0, 0.1F)
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC4() {
    logger.LDC(0, 0, 0.2D)
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC5() {
    logger.LDC(0, 0, "a")
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC6() {
    logger.LDC(0, 1, new Integer(1))
    assertTrue(logger.log.contains("LDC"))  
  }

  @Test
  void testIINC() {
    logger.IINC(0, 0, 1, 1)
    assertTrue(logger.log.contains("IINC"))
  }

  @Test
  void testMULTIANEWARRAY() {
    logger.MULTIANEWARRAY(0, 0, "I", 2)
    assertTrue(logger.log.contains("MULTIANEWARRAY"))
  }

  @Test
  void testLOOKUPSWITCH() {
    int[] keys = new int[1]
    keys[0] = 1
    int[] labels = new int[1]
    labels[0] = 2
    logger.LOOKUPSWITCH(0, 0, 2, keys, labels)
    assertTrue(logger.log.contains("LOOKUPSWITCH"))
  }

  @Test
  void testTABLESWITCH() {
    int[] labels = new int[1]
    labels[0] = 2
    logger.TABLESWITCH(0, 0, 0, 2, 1, labels)
    assertTrue(logger.log.contains("TABLESWITCH"))
  }

  @Test
  void testIFEQ() {
    logger.IFEQ(0, 0, 1)
    assertTrue(logger.log.contains("IFEQ"))
  }

  @Test
  void testIFNE() {
    logger.IFNE(0, 0, 1)
    assertTrue(logger.log.contains("IFNE"))
  }

  @Test
  void testIFLT(){
    logger.IFLT(0, 0, 1)
    assertTrue(logger.log.contains("IFLT"))
  }

  @Test
  void testIFGE() {
    logger.IFGE(0, 0, 1)
    assertTrue(logger.log.contains("IFGE"))
  }

  @Test
  void testIFGT() {
    logger.IFGT(0, 0, 1)
    assertTrue(logger.log.contains("IFGT"))
  }

  @Test
  void testIFLE() {
    logger.IFLE(0, 0, 1)
    assertTrue(logger.log.contains("IFLE"))
  }

  @Test
  void testIF_ICMPEQ() {
    logger.IF_ICMPEQ(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPEQ"))
  }

  @Test
  void testIF_ICMPNE() {
    logger.IF_ICMPNE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPNE"))
  }

  @Test
  void testIF_ICMPLT() {
    logger.IF_ICMPLT(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPLT"))
  }

  @Test
  void testIF_ICMPGE() {
    logger.IF_ICMPGE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPGE"))
  }

  @Test
  void testIF_ICMPGT() {
    logger.IF_ICMPGT(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPGT"))
  }

  @Test
  void testIF_ICMPLE() {
    logger.IF_ICMPLE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPLE"))
  }

  @Test
  void testIF_ACMPEQ() {
    logger.IF_ACMPEQ(0, 0, 1)
    assertTrue(logger.log.contains("IF_ACMPEQ"))
  }

  @Test
  void testIF_ACMPNE() {
    logger.IF_ACMPNE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ACMPNE"))
  }

  @Test
  void testIFNULL() {
    logger.IFNULL(0, 0, 1)
    assertTrue(logger.log.contains("IFNULL"))
  }

  @Test
  void testIFNONNULL() {
    logger.IFNONNULL(0, 0, 1)
    assertTrue(logger.log.contains("IFNONNULL"))
  }

  @Test
  void testGOTO() {
    logger.GOTO(0, 0, 1)
    assertTrue(logger.log.contains("GOTO"))
  }

  @Test
  void testJSR() {
    logger.JSR(0, 0, 1)
    assertTrue(logger.log.contains("JSR"))
  }

  @Test
  void testInvokeVirtual() {
    logger.INVOKEVIRTUAL(0, 0, "owner", "method", "()I")
    assertTrue(logger.log.contains("INVOKEVIRTUAL"))
  }

  @Test
  void testInvokeStatic() {
    logger.INVOKESTATIC(0, 0, "owner", "method", "()I")
    assertTrue(logger.log.contains("INVOKESTATIC"))
  }

  @Test
  void testInvokeSpecial() {
    logger.INVOKESPECIAL(0, 0, "owner", "method", "()I")
    assertTrue(logger.log.contains("INVOKESPECIAL"))
  }

  @Test
  void testInvokeInterface() {  
    logger.INVOKEINTERFACE(0, 0, "owner", "method", "()I")
    assertTrue(logger.log.contains("INVOKEINTERFACE"))
  }

  @Test
  void testField() {
    logger.GETFIELD(0, 0, 1, 1, "I")
    assertTrue(logger.log.contains("GETFIELD"))
  }

  @Test
  void testPutField() {
    logger.PUTFIELD(0, 0, 1, 1, "I")
    assertTrue(logger.log.contains("PUTFIELD"))
  }

  @Test
  void testGetStatic() {
    logger.GETSTATIC(0, 0, 1, 1, "I")
    assertTrue(logger.log.contains("GETSTATIC"))
  }

  @Test
  void testPutStatic() {
    logger.PUTSTATIC(0, 0, 1, 1, "I")
    assertTrue(logger.log.contains("PUTSTATIC"))
  }

  @Test
  void testNew() {
    logger.NEW(0, 0, "MyClass", 0)
    assertTrue(logger.log.contains("NEW"))
  }

  @Test
  void testNewArray() {
    logger.ANEWARRAY(0, 0, "MyClass")
    assertTrue(logger.log.contains("ANEWARRAY"))
  }

  @Test
  void testCheckCast() {
    logger.CHECKCAST(0, 0, "MyClass")
    assertTrue(logger.log.contains("CHECKCAST"))
  }

  @Test
  void testInstanceOf() {
    logger.INSTANCEOF(0, 0, "MyClass")
    assertTrue(logger.log.contains("INSTANCEOF"))
  }

  @Test
  void testBiPush(){
    logger.BIPUSH(0, 0, 1)
    assertTrue(logger.log.contains("BIPUSH"))
  }

  @Test
  void testSIPush() {
    logger.SIPUSH(0, 0, 1)
    assertTrue(logger.log.contains("SIPUSH"))
  }

  @Test
  void testILoad() {
    logger.ILOAD(0, 0, 1)
    assertTrue(logger.log.contains("ILOAD"))
  }

  @Test
  void testIStore() {
    logger.ISTORE(0, 0, 1)
    assertTrue(logger.log.contains("ISTORE"))
  }

  @Test
  void testLLoad() {
    logger.LLOAD(0, 0, 1)
    assertTrue(logger.log.contains("LLOAD"))
  }

  @Test
  void testLStore() {
    logger.LSTORE(0, 0, 1)
    assertTrue(logger.log.contains("STORE"))
  }

  @Test
  void testFLoad() {
    logger.FLOAD(0, 0, 1)
    assertTrue(logger.log.contains("FLOAD"))
  }

  @Test
  void testFStore() {
    logger.FSTORE(0, 0, 1)
    assertTrue(logger.log.contains("FSTORE"))
  }

  @Test
  void testDLoad() {
    logger.DLOAD(0, 0, 1)
    assertTrue(logger.log.contains("DLOAD"))
  }

  @Test
  void testDStore() {
    logger.DSTORE(0, 0, 1)
    assertTrue(logger.log.contains("DSTORE"))
  }

  @Test
  void testALoad() {
    logger.ALOAD(0, 0, 1)
    assertTrue(logger.log.contains("ALOAD"))
  }

  @Test
  void testAStore() {
    logger.ASTORE(0, 0, 1)
    assertTrue(logger.log.contains("ASTORE"))
  }

  @Test
  void testRet() {
    logger.RET(0, 0, 1)
    assertTrue(logger.log.contains("RET"))
  }


  @Test
  void testNOP() {
    logger.NOP(0, 0)
    assertTrue(logger.log.contains("NOP"))
  }

  @Test
  void testNEWARRAY() {
    logger.NEWARRAY(0, 0)
    assertTrue(logger.log.contains("NEWARRAY"))
  }

  @Test
  void testACONST_NULL() {
    logger.ACONST_NULL(0, 0)
    assertTrue(logger.log.contains("ACONST_NULL"))
  }

  @Test
  void testICONST_M1() {
    logger.ICONST_M1(0, 0)
    assertTrue(logger.log.contains("ICONST_M1"))
  }

  @Test
  void testICONST_0() {
    logger.ICONST_0(0, 0)
    assertTrue(logger.log.contains("ICONST_0"))
  }

  @Test
  void testICONST_1() {
    logger.ICONST_1(0, 0)
    assertTrue(logger.log.contains("ICONST_1"))
  }

  @Test
  void testICONST_2() {
    logger.ICONST_2(0, 0)
    assertTrue(logger.log.contains("ICONST_2"))
  }

  @Test
  void testICONST_3() {
    logger.ICONST_3(0, 0)
    assertTrue(logger.log.contains("ICONST_3"))
  }

  @Test
  void testICONST_4() {
    logger.ICONST_4(0, 0)
    assertTrue(logger.log.contains("ICONST_4"))
  }

  @Test
  void testICONST_5() {
    logger.ICONST_5(0, 0)
    assertTrue(logger.log.contains("ICONST_5"))
  }

  @Test
  void testLCONST_0() {
    logger.LCONST_0(0, 0)
    assertTrue(logger.log.contains("LCONST_0"))
  }

  @Test
  void testLCONST_1() {
    logger.LCONST_1(0, 0)
    assertTrue(logger.log.contains("LCONST_1"))
  }

  @Test
  void testFCONST_0() {
    logger.FCONST_0(0, 0)
    assertTrue(logger.log.contains("FCONST_0"))
  }

  @Test
  void testFCONST_1() {
    logger.FCONST_1(0, 0)
    assertTrue(logger.log.contains("FCONST_1"))
  }

  @Test
  void testFCONST_2() {
    logger.FCONST_2(0, 0)
    assertTrue(logger.log.contains("FCONST_2"))
  }

  @Test
  void testDCONST_0() {
    logger.DCONST_0(0, 0)
    assertTrue(logger.log.contains("DCONST_0"))
  }

  @Test
  void testDCONST_1() {
    logger.DCONST_1(0, 0)
    assertTrue(logger.log.contains("DCONST_1"))
  }

  @Test
  void testIALOAD() {
    logger.IALOAD(0, 0)
    assertTrue(logger.log.contains("IALOAD"))
  }

  @Test
  void testIASTORE() {
    logger.IASTORE(0, 0)
    assertTrue(logger.log.contains("IASTORE"))
  }

  @Test
  void testLALOAD() {
    logger.LALOAD(0, 0)
    assertTrue(logger.log.contains("LALOAD"))
  }

  @Test
  void testLASTORE() {
    logger.LASTORE(0, 0)
    assertTrue(logger.log.contains("LASTORE"))
  }

  @Test
  void testFALOAD() {
    logger.FALOAD(0, 0)
    assertTrue(logger.log.contains("FALOAD"))
  }

  @Test
  void testFASTORE() {
    logger.FASTORE(0, 0)
    assertTrue(logger.log.contains("FASTORE"))
  }

  @Test
  void testDALOAD() {
    logger.DALOAD(0, 0)
    assertTrue(logger.log.contains("DALOAD"))
  }

  @Test
  void testDASTORE() {
    logger.DASTORE(0, 0)
    assertTrue(logger.log.contains("DASTORE"))
  }

  @Test
  void testAALOAD() {
    logger.AALOAD(0, 0)
    assertTrue(logger.log.contains("AALOAD"))
  }

  @Test
  void testAASTORE() {
    logger.AASTORE(0, 0)
    assertTrue(logger.log.contains("AASTORE"))
  }

  @Test
  void testBALOAD() {
    logger.BALOAD(0, 0)
    assertTrue(logger.log.contains("BALOAD"))
  }

  @Test
  void testBASTORE() {
    logger.BASTORE(0, 0)
    assertTrue(logger.log.contains("BASTORE"))
  }

  @Test
  void testCALOAD() {
    logger.CALOAD(0, 0)
    assertTrue(logger.log.contains("CALOAD"))
  }

  @Test
  void testCASTORE() {
    logger.CASTORE(0, 0)
    assertTrue(logger.log.contains("CASTORE"))
  }

  @Test
  void testSALOAD() {
    logger.SALOAD(0, 0)
    assertTrue(logger.log.contains("SALOAD"))
  }

  @Test
  void testSASTORE() {
    logger.SASTORE(0, 0)
    assertTrue(logger.log.contains("SASTORE"))
  }

  @Test
  void testPOP() {
    logger.POP(0, 0)
    assertTrue(logger.log.contains("POP"))
  }

  @Test
  void testPOP2() {
    logger.POP2(0, 0)
    assertTrue(logger.log.contains("POP2"))
  }

  @Test
  void testDUP() {
    logger.DUP(0, 0)
    assertTrue(logger.log.contains("DUP"))
  }

  @Test
  void testDUP_X1() {
    logger.DUP_X1(0, 0)
    assertTrue(logger.log.contains("DUP_X1"))
  }

  @Test
  void testDUP_X2() {
    logger.DUP_X2(0, 0)
    assertTrue(logger.log.contains("DUP_X2"))
  }

  @Test
  void testDUP2() {
    logger.DUP2(0, 0)
    assertTrue(logger.log.contains("DUP2"))
  }

  @Test
  void testDUP2_X1() {
    logger.DUP2_X1(0, 0)
    assertTrue(logger.log.contains("DUP2_X1"))
  }

  @Test
  void testDUP2_X2() {
    logger.DUP2_X2(0, 0)
    assertTrue(logger.log.contains("DUP2_X2"))
  }

  @Test
  void testSWAP() {
    logger.SWAP(0, 0)
    assertTrue(logger.log.contains("SWAP"))
  }

  @Test
  void testIAdd() {
    logger.IADD(0, 0)
    assertTrue(logger.log.contains("IADD"))
  }

  @Test
  void testLAdd() {
    logger.LADD(0, 0)
    assertTrue(logger.log.contains("LADD"))
  }

  @Test
  void testFAdd() {
    logger.FADD(0, 0)
    assertTrue(logger.log.contains("FADD"))
  }

  @Test
  void testDadd() {
    logger.DADD(0, 0)
    assertTrue(logger.log.contains("DADD"))
  }

  @Test
  void testISub() {
    logger.ISUB(0, 0)
    assertTrue(logger.log.contains("ISUB"))
  }

  @Test
  void testLSub() {
    logger.LSUB(0, 0)
    assertTrue(logger.log.contains("LSUB"))
  }

  @Test
  void testFSub() {
    logger.FSUB(0, 0)
    assertTrue(logger.log.contains("FSUB"))
  }

  @Test
  void testDSub() {
    logger.DSUB(0, 0)
    assertTrue(logger.log.contains("DSUB"))
  }

  @Test
  void testIMul() {
    logger.IMUL(0, 0)
    assertTrue(logger.log.contains("IMUL"))
  }

  @Test
  void testFMul() {
    logger.FMUL(0, 0)
    assertTrue(logger.log.contains("FMUL"))
  }

  @Test
  void testLMul() {
    logger.LMUL(0, 0)
    assertTrue(logger.log.contains("LMUL"))
  }

  @Test
  void testDMul() {
    logger.DMUL(0, 0)
    assertTrue(logger.log.contains("DMUL"))
  }

  @Test
  void testIDiv() {
    logger.IDIV(0, 0)
    assertTrue(logger.log.contains("IDIV"))
  }

  @Test
  void testFDiv() {
    logger.FDIV(0, 0)
    assertTrue(logger.log.contains("FDIV"))
  }

  @Test
  void testLDiv() {
    logger.LDIV(0, 0)
    assertTrue(logger.log.contains("LDIV"))
  }

  @Test
  void testDDiv() {
    logger.DDIV(0, 0)
    assertTrue(logger.log.contains("DDIV"))
  }

  @Test
  void testIRem() {
    logger.IREM(0, 0)
    assertTrue(logger.log.contains("IREM"))
  }

  @Test
  void testLRem() {
    logger.LREM(0, 0)
    assertTrue(logger.log.contains("LREM"))
  }

  @Test
  void testFRem() {
    logger.FREM(0, 0)
    assertTrue(logger.log.contains("FREM"))
  }

  @Test
  void testDRem() {
    logger.DREM(0, 0)
    assertTrue(logger.log.contains("DREM"))
  }

  @Test
  void testINeg() {
    logger.INEG(0, 0)
    assertTrue(logger.log.contains("INEG"))
  }

  @Test
  void testLNeg() {
    logger.LNEG(0, 0)
    assertTrue(logger.log.contains("LNEG"))
  }

  @Test
  void testFNeg() {
    logger.FNEG(0, 0)
    assertTrue(logger.log.contains("FNEG"))
  }

  @Test
  void testDNeg() {
    logger.DNEG(0, 0)
    assertTrue(logger.log.contains("DNEG"))
  }

  @Test
  void testIShl() {
    logger.ISHL(0, 0)
    assertTrue(logger.log.contains("ISHL"))
  }

  @Test
  void testLShl() {
    logger.LSHL(0, 0)
    assertTrue(logger.log.contains("LSHL"))
  }

  @Test
  void testIShr() {
    logger.ISHR(0, 0)
    assertTrue(logger.log.contains("ISHR"))
  }

  @Test
  void testLShr() {
    logger.LSHR(0, 0)
    assertTrue(logger.log.contains("LSHR"))
  }

  @Test
  void testIUshr() {
    logger.IUSHR(0, 0)
    assertTrue(logger.log.contains("IUSHR"))
  }

  @Test
  void testLUshr() {
    logger.LUSHR(0, 0)
    assertTrue(logger.log.contains("LUSHR"))
  }

  @Test
  void testIAND() {
    logger.IAND(0, 0)
    assertTrue(logger.log.contains("IAND"))
  }

  @Test
  void testLAnd() {
    logger.LAND(0, 0)
    assertTrue(logger.log.contains("LAND"))
  }

  @Test
  void testIOr() {
    logger.IOR(0, 0)
    assertTrue(logger.log.contains("IOR"))
  }

  @Test
  void testLOr() {
    logger.LOR(0, 0)
    assertTrue(logger.log.contains("LOR"))
  }

  @Test
  void testIXor() {
    logger.IXOR(0, 0)
    assertTrue(logger.log.contains("IXOR"))
  }

  @Test
  void testLXor() {
    logger.LXOR(0, 0)
    assertTrue(logger.log.contains("LXOR"))
  }

  @Test
  void testI2L() {
    logger.I2L(0, 0)
    assertTrue(logger.log.contains("I2L"))
  }

  @Test
  void testI2F() {
    logger.I2F(0, 0)
    assertTrue(logger.log.contains("I2F"))
  }

  @Test
  void testI2D() {
    logger.I2D(0, 0)
    assertTrue(logger.log.contains("I2D"))
  }

  @Test
  void testL2I() {
    logger.L2I(0, 0)
    assertTrue(logger.log.contains("L2I"))
  }

  @Test
  void testL2F() {
    logger.L2F(0, 0)
    assertTrue(logger.log.contains("L2F"))
  }

  @Test
  void testL2D() {
    logger.L2D(0, 0)
    assertTrue(logger.log.contains("L2D"))
  }

  @Test
  void testF2I() {
    logger.F2I(0, 0)
    assertTrue(logger.log.contains("F2I"))
  }

  @Test
  void testF2L() {
    logger.F2L(0, 0)
    assertTrue(logger.log.contains("F2L"))
  }

  @Test
  void testF2D() {
    logger.F2D(0, 0)
    assertTrue(logger.log.contains("F2D"))
  }

  @Test
  void testD2I() {
    logger.D2I(0, 0)
    assertTrue(logger.log.contains("D2I"))
  }

  @Test
  void testD2L() {
    logger.D2L(0, 0)
    assertTrue(logger.log.contains("D2L"))
  }

  @Test
  void testD2F() {
    logger.D2F(0, 0)
    assertTrue(logger.log.contains("D2F"))
  }

  @Test
  void testI2C() {
    logger.I2C(0, 0)
    assertTrue(logger.log.contains("I2C"))
  }

  @Test
  void testI2B() {
    logger.I2B(0, 0)
    assertTrue(logger.log.contains("I2B"))
  }

  @Test
  void testI2S() {
    logger.I2S(0, 0)
    assertTrue(logger.log.contains("I2S"))
  }

  @Test
  void testLCMP() {
    logger.LCMP(0, 0)
    assertTrue(logger.log.contains("LCMP"))
  }

  @Test
  void testFCMPL() {
    logger.FCMPL(0, 0)
    assertTrue(logger.log.contains("FCMPL"))
  }

  @Test
  void testFCMPG() {
    logger.FCMPG(0, 0)
    assertTrue(logger.log.contains("FCMPG"))
  }

  @Test
  void testDCMPL() {
    logger.DCMPL(0, 0)
    assertTrue(logger.log.contains("DCMPL"))
  }

  @Test
  void testDCMPG() {
    logger.DCMPG(0, 0)
    assertTrue(logger.log.contains("DCMPG"))
  }

  @Test
  void testIReturn() {
    logger.IRETURN(0, 0)
    assertTrue(logger.log.contains("IRETURN"))
  }

  @Test
  void testLReturn() {
    logger.LRETURN(0, 0)
    assertTrue(logger.log.contains("LRETURN"))
  }

  @Test
  void testFReturn() {
    logger.FRETURN(0, 0)
    assertTrue(logger.log.contains("FRETURN"))
  }

  @Test
  void testDReturn() {
    logger.DRETURN(0, 0)
    assertTrue(logger.log.contains("DRETURN"))
  }

  @Test
  void testAReturn() {
    logger.ARETURN(0, 0)
    assertTrue(logger.log.contains("ARETURN"))
  }

  @Test
  void testReturn() {
    logger.RETURN(0, 0)
    assertTrue(logger.log.contains("RETURN"))
  }

  @Test
  void testArrayLength() {
    logger.ARRAYLENGTH(0, 0)
    assertTrue(logger.log.contains("ARRAYLENGTH"))
  }

  @Test
  void testAThrow() {
    logger.ATHROW(0, 0)
    assertTrue(logger.log.contains("ATHROW"))
  }

  @Test
  void testMonitor() {
    logger.MONITORENTER(0, 0)
    assertTrue(logger.log.contains("MONITORENTER"))
  }

  @Test
  void testMonitor2() {
    logger.MONITOREXIT(0, 0)
    assertTrue(logger.log.contains("MONITOREXIT"))
  }

  @Test
  void testGetValue_double() {
    logger.GETVALUE_double(0.0D)
    assertTrue(logger.log.contains("GETVALUE_double"))
  }

  @Test
  void testGetValue_long() {
    logger.GETVALUE_long(0L)
    assertTrue(logger.log.contains("GETVALUE_long"))
  }

  @Test
  void testGetValue_floag() {
    logger.GETVALUE_float(0.0F)
    assertTrue(logger.log.contains("GETVALUE_float"))
  }

  @Test
  void testGetValue_int() {
    logger.GETVALUE_int(0)
    assertTrue(logger.log.contains("GETVALUE_int"))
  }

  @Test
  void testGetValue_byte() {
    logger.GETVALUE_byte((byte)0)
    assertTrue(logger.log.contains("GETVALUE_byte"))
  }

  @Test
  void testGetValue_char() {
    logger.GETVALUE_char((char)0)
    assertTrue(logger.log.contains("GETVALUE_char"))
  }

  @Test
  void testGetValue_boolean() {
    logger.GETVALUE_boolean(true)
    assertTrue(logger.log.contains("GETVALUE_boolean"))
  }

  @Test
  void testGetValue_object() {
    logger.GETVALUE_Object("a")
    assertTrue(logger.log.contains("GETVALUE_Object"))
  }

  @Test
  void testGetValue_short() {
    logger.GETVALUE_short((short)0)
    assertTrue(logger.log.contains("GETVALUE_short"))
  }

  @Test
  void testGetValue_void() {
    logger.GETVALUE_void()
    assertTrue(logger.log.contains("GETVALUE_void"))
  }

  @Test
  void testMethod_1() {
    logger.INVOKEMETHOD_EXCEPTION()
    assertTrue(logger.log.contains("INVOKEMETHOD_EXCEPTION"))
  }

  @Test
  void testMedthod_2() {
    logger.INVOKEMETHOD_END()
    assertTrue(logger.log.contains("INVOKEMETHOD_END"))
  }

  @Test
  void testMakeSymbolic() {
    logger.MAKE_SYMBOLIC()
    assertTrue(logger.log.contains("MAKE_SYMBOLIC"))
  }

  @Test
  void testSpecial() {
    logger.SPECIAL(0)
    assertTrue(logger.log.contains("SPECIAL"))
  }
}