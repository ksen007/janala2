package janala.logger;
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.junit.Test
import org.junit.Before
import groovy.transform.CompileStatic

@CompileStatic
class DJVMTest {
  private Logger intp

  @Before
  void setup() {
    intp = mock(Logger.class)
    DJVM.setInterpreter(intp)
  }

  @Test
  void testLDC() {
    DJVM.LDC(0, 0, 1)
    verify(intp).LDC(0, 0, 1)

    DJVM.LDC(0, 0, 2L)
    verify(intp).LDC(0, 0, 2L)

    DJVM.LDC(0, 0, 0.1F)
    verify(intp).LDC(0, 0, 0.1F)

    DJVM.LDC(0, 0, 0.2D)
    verify(intp).LDC(0, 0, 0.2D)

    DJVM.LDC(0, 0, "a")
    verify(intp).LDC(0, 0, "a")

    DJVM.LDC(0, 1, new Integer(1))
    verify(intp).LDC(0, 1, new Integer(1))
  }

  @Test
  void testIINC() {
    DJVM.IINC(0, 0, 1, 1)
    verify(intp).IINC(0, 0, 1, 1)
  }

  @Test
  void testMULTIANEWARRAY() {
    DJVM.MULTIANEWARRAY(0, 0, "I", 2)
    verify(intp).MULTIANEWARRAY(0, 0, "I", 2)
  }

  @Test
  void testLOOKUPSWITCH() {
    int[] keys = new int[1]
    keys[0] = 1
    int[] labels = new int[1]
    labels[0] = 2
    DJVM.LOOKUPSWITCH(0, 0, 2, keys, labels)
    verify(intp).LOOKUPSWITCH(0, 0, 2, keys, labels)
  }

  @Test
  void testTABLESWITCH() {
    int[] labels = new int[1]
    labels[0] = 2
    DJVM.TABLESWITCH(0, 0, 0, 2, 1, labels)
    verify(intp).TABLESWITCH(0, 0, 0, 2, 1, labels)
  }

  @Test
  void testIF() {
    DJVM.IFEQ(0, 0, 1)
    verify(intp).IFEQ(0, 0, 1)

    DJVM.IFNE(0, 0, 1)
    verify(intp).IFNE(0, 0, 1)

    DJVM.IFLT(0, 0, 1)
    verify(intp).IFLT(0, 0, 1)

    DJVM.IFGE(0, 0, 1)
    verify(intp).IFGE(0, 0, 1)

    DJVM.IFGT(0, 0, 1)
    verify(intp).IFGT(0, 0, 1)

    DJVM.IFLE(0, 0, 1)
    verify(intp).IFLE(0, 0, 1)

    DJVM.IF_ICMPEQ(0, 0, 1)
    verify(intp).IF_ICMPEQ(0, 0, 1)

    DJVM.IF_ICMPNE(0, 0, 1)
    verify(intp).IF_ICMPNE(0, 0, 1)

    DJVM.IF_ICMPLT(0, 0, 1)
    verify(intp).IF_ICMPLT(0, 0, 1)

    DJVM.IF_ICMPGE(0, 0, 1)
    verify(intp).IF_ICMPGE(0, 0, 1)

    DJVM.IF_ICMPGT(0, 0, 1)
    verify(intp).IF_ICMPGT(0, 0, 1)

    DJVM.IF_ICMPLE(0, 0, 1)
    verify(intp).IF_ICMPLE(0, 0, 1)

    DJVM.IF_ACMPEQ(0, 0, 1)
    verify(intp).IF_ACMPEQ(0, 0, 1)

    DJVM.IF_ACMPNE(0, 0, 1)
    verify(intp).IF_ACMPNE(0, 0, 1)

    DJVM.IFNULL(0, 0, 1)
    verify(intp).IFNULL(0, 0, 1)

    DJVM.IFNONNULL(0, 0, 1)
    verify(intp).IFNONNULL(0, 0, 1)

    DJVM.GOTO(0, 0, 1)
    verify(intp).GOTO(0, 0, 1)

    DJVM.JSR(0, 0, 1)
    verify(intp).JSR(0, 0, 1)
  }

  @Test
  void testInvoke() {
    DJVM.INVOKEVIRTUAL(0, 0, "owner", "method", "()I")
    verify(intp).INVOKEVIRTUAL(0, 0, "owner", "method", "()I")

    DJVM.INVOKESTATIC(0, 0, "owner", "method", "()I")
    verify(intp).INVOKESTATIC(0, 0, "owner", "method", "()I")
    
    DJVM.INVOKESPECIAL(0, 0, "owner", "method", "()I")
    verify(intp).INVOKESPECIAL(0, 0, "owner", "method", "()I")
    
    DJVM.INVOKEINTERFACE(0, 0, "owner", "method", "()I")
    verify(intp).INVOKEINTERFACE(0, 0, "owner", "method", "()I")
  }

  @Test
  void testField() {
    DJVM.GETFIELD(0, 0, 1, 1, "I")
    verify(intp).GETFIELD(0, 0, 1, 1, "I")

    DJVM.PUTFIELD(0, 0, 1, 1, "I")
    verify(intp).PUTFIELD(0, 0, 1, 1, "I")

    DJVM.GETSTATIC(0, 0, 1, 1, "I")
    verify(intp).GETSTATIC(0, 0, 1, 1, "I")

    DJVM.PUTSTATIC(0, 0, 1, 1, "I")
    verify(intp).PUTSTATIC(0, 0, 1, 1, "I")
  }

  @Test
  void testNew() {
    DJVM.NEW(0, 0, "MyClass", 0)
    verify(intp).NEW(0, 0, "MyClass", 0)
  }

  @Test
  void testTypeInsn() {
    DJVM.ANEWARRAY(0, 0, "MyClass")
    verify(intp).ANEWARRAY(0, 0, "MyClass")

    DJVM.CHECKCAST(0, 0, "MyClass")
    verify(intp).CHECKCAST(0, 0, "MyClass")

    DJVM.INSTANCEOF(0, 0, "MyClass")
    verify(intp).INSTANCEOF(0, 0, "MyClass")
  }

  @Test
  void testVarInsn(){
    DJVM.BIPUSH(0, 0, 1)
    verify(intp).BIPUSH(0, 0, 1)

    DJVM.SIPUSH(0, 0, 1)
    verify(intp).SIPUSH(0, 0, 1)

    DJVM.ILOAD(0, 0, 1)
    verify(intp).ILOAD(0, 0, 1)

    DJVM.ISTORE(0, 0, 1)
    verify(intp).ISTORE(0, 0, 1)

    DJVM.LLOAD(0, 0, 1)
    verify(intp).LLOAD(0, 0, 1)

    DJVM.LSTORE(0, 0, 1)
    verify(intp).LSTORE(0, 0, 1)

    DJVM.FLOAD(0, 0, 1)
    verify(intp).FLOAD(0, 0, 1)

    DJVM.FSTORE(0, 0, 1)
    verify(intp).FSTORE(0, 0, 1)

    DJVM.DLOAD(0, 0, 1)
    verify(intp).DLOAD(0, 0, 1)

    DJVM.DSTORE(0, 0, 1)
    verify(intp).DSTORE(0, 0, 1)

    DJVM.ALOAD(0, 0, 1)
    verify(intp).ALOAD(0, 0, 1)

    DJVM.ASTORE(0, 0, 1)
    verify(intp).ASTORE(0, 0, 1)

    DJVM.RET(0, 0, 1)
    verify(intp).RET(0, 0, 1)
  }


  @Test
  void testAllInsn() {
    DJVM.NOP(0, 0)
    verify(intp).NOP(0, 0)

    DJVM.NEWARRAY(0, 0)
    verify(intp).NEWARRAY(0, 0)

    DJVM.ACONST_NULL(0, 0)
    verify(intp).ACONST_NULL(0, 0)

    DJVM.ICONST_M1(0, 0)
    verify(intp).ICONST_M1(0, 0)

    DJVM.ICONST_0(0, 0)
    verify(intp).ICONST_0(0, 0)

    DJVM.ICONST_1(0, 0)
    verify(intp).ICONST_1(0, 0)

    DJVM.ICONST_2(0, 0)
    verify(intp).ICONST_2(0, 0)

    DJVM.ICONST_3(0, 0)
    verify(intp).ICONST_3(0, 0)

    DJVM.ICONST_4(0, 0)
    verify(intp).ICONST_4(0, 0)

    DJVM.ICONST_5(0, 0)
    verify(intp).ICONST_5(0, 0)

    DJVM.LCONST_0(0, 0)
    verify(intp).LCONST_0(0, 0)

    DJVM.LCONST_1(0, 0)
    verify(intp).LCONST_1(0, 0)

    DJVM.FCONST_0(0, 0)
    verify(intp).FCONST_0(0, 0)

    DJVM.FCONST_1(0, 0)
    verify(intp).FCONST_1(0, 0)

    DJVM.FCONST_2(0, 0)
    verify(intp).FCONST_2(0, 0)

    DJVM.DCONST_0(0, 0)
    verify(intp).DCONST_0(0, 0)

    DJVM.DCONST_1(0, 0)
    verify(intp).DCONST_1(0, 0)

    DJVM.IALOAD(0, 0)
    verify(intp).IALOAD(0, 0)

    DJVM.IASTORE(0, 0)
    verify(intp).IASTORE(0, 0)

    DJVM.LALOAD(0, 0)
    verify(intp).LALOAD(0, 0)

    DJVM.LASTORE(0, 0)
    verify(intp).LASTORE(0, 0)

    DJVM.FALOAD(0, 0)
    verify(intp).FALOAD(0, 0)

    DJVM.FASTORE(0, 0)
    verify(intp).FASTORE(0, 0)

    DJVM.DALOAD(0, 0)
    verify(intp).DALOAD(0, 0)

    DJVM.DASTORE(0, 0)
    verify(intp).DASTORE(0, 0)

    DJVM.AALOAD(0, 0)
    verify(intp).AALOAD(0, 0)

    DJVM.AASTORE(0, 0)
    verify(intp).AASTORE(0, 0)

    DJVM.BALOAD(0, 0)
    verify(intp).BALOAD(0, 0)

    DJVM.BASTORE(0, 0)
    verify(intp).BASTORE(0, 0)

    DJVM.CALOAD(0, 0)
    verify(intp).CALOAD(0, 0)

    DJVM.CASTORE(0, 0)
    verify(intp).CASTORE(0, 0)

    DJVM.SALOAD(0, 0)
    verify(intp).SALOAD(0, 0)

    DJVM.SASTORE(0, 0)
    verify(intp).SASTORE(0, 0)

    DJVM.POP(0, 0)
    verify(intp).POP(0, 0)

    DJVM.POP2(0, 0)
    verify(intp).POP2(0, 0)

    DJVM.DUP(0, 0)
    verify(intp).DUP(0, 0)   

    DJVM.DUP_X1(0, 0)
    verify(intp).DUP_X1(0, 0)

    DJVM.DUP_X2(0, 0)
    verify(intp).DUP_X2(0, 0)

    DJVM.DUP2(0, 0)
    verify(intp).DUP2(0, 0)

    DJVM.DUP2_X1(0, 0)
    verify(intp).DUP2_X1(0, 0)

    DJVM.DUP2_X2(0, 0)
    verify(intp).DUP2_X2(0, 0)

    DJVM.SWAP(0, 0)
    verify(intp).SWAP(0, 0)        
  }

  @Test
  void testMathInsn() {
    DJVM.IADD(0, 0)
    verify(intp).IADD(0, 0)

    DJVM.LADD(0, 0)
    verify(intp).LADD(0, 0)

    DJVM.FADD(0, 0)
    verify(intp).FADD(0, 0)

    DJVM.DADD(0, 0)
    verify(intp).DADD(0, 0)

    DJVM.ISUB(0, 0)
    verify(intp).ISUB(0, 0)

    DJVM.LSUB(0, 0)
    verify(intp).LSUB(0, 0)

    DJVM.FSUB(0, 0)
    verify(intp).FSUB(0, 0)

    DJVM.DSUB(0, 0)
    verify(intp).DSUB(0, 0)

    DJVM.IMUL(0, 0)
    verify(intp).IMUL(0, 0)

    DJVM.FMUL(0, 0)
    verify(intp).FMUL(0, 0)

    DJVM.LMUL(0, 0)
    verify(intp).LMUL(0, 0)

    DJVM.DMUL(0, 0)
    verify(intp).DMUL(0, 0)

    DJVM.IDIV(0, 0)
    verify(intp).IDIV(0, 0)

    DJVM.FDIV(0, 0)
    verify(intp).FDIV(0, 0)

    DJVM.LDIV(0, 0)
    verify(intp).LDIV(0, 0)

    DJVM.DDIV(0, 0)
    verify(intp).DDIV(0, 0)

    DJVM.IREM(0, 0)
    verify(intp).IREM(0, 0)

    DJVM.LREM(0, 0)
    verify(intp).LREM(0, 0)

    DJVM.FREM(0, 0)
    verify(intp).FREM(0, 0)

    DJVM.DREM(0, 0)
    verify(intp).DREM(0, 0)

    DJVM.INEG(0, 0)
    verify(intp).INEG(0, 0)

    DJVM.LNEG(0, 0)
    verify(intp).LNEG(0, 0)

    DJVM.FNEG(0, 0)
    verify(intp).FNEG(0, 0)

    DJVM.DNEG(0, 0)
    verify(intp).DNEG(0, 0)

    DJVM.ISHL(0, 0)
    verify(intp).ISHL(0, 0)

    DJVM.LSHL(0, 0)
    verify(intp).LSHL(0, 0)

    DJVM.ISHR(0, 0)
    verify(intp).ISHR(0, 0)

    DJVM.LSHR(0, 0)
    verify(intp).LSHR(0, 0)

    DJVM.IUSHR(0, 0)
    verify(intp).IUSHR(0, 0)

    DJVM.LUSHR(0, 0)
    verify(intp).LUSHR(0, 0)
  }

  @Test
  void testLogicInsn() {
    DJVM.IAND(0, 0)
    verify(intp).IAND(0, 0)

    DJVM.LAND(0, 0)
    verify(intp).LAND(0, 0)

    DJVM.IOR(0, 0)
    verify(intp).IOR(0, 0)

    DJVM.LOR(0, 0)
    verify(intp).LOR(0, 0)

    DJVM.IXOR(0, 0)
    verify(intp).IXOR(0, 0)

    DJVM.LXOR(0, 0)
    verify(intp).LXOR(0, 0)
  }

  @Test
  void testNumberInsn() {
    DJVM.I2L(0, 0)
    verify(intp).I2L(0, 0)

    DJVM.I2F(0, 0)
    verify(intp).I2F(0, 0)

    DJVM.I2D(0, 0)
    verify(intp).I2D(0, 0)

    DJVM.L2I(0, 0)
    verify(intp).L2I(0, 0)

    DJVM.L2F(0, 0)
    verify(intp).L2F(0, 0)

    DJVM.L2D(0, 0)
    verify(intp).L2D(0, 0)

    DJVM.F2I(0, 0)
    verify(intp).F2I(0, 0)

    DJVM.F2L(0, 0)
    verify(intp).F2L(0, 0)

    DJVM.F2D(0, 0)
    verify(intp).F2D(0, 0)

    DJVM.D2I(0, 0)
    verify(intp).D2I(0, 0)

    DJVM.D2L(0, 0)
    verify(intp).D2L(0, 0)

    DJVM.D2F(0, 0)
    verify(intp).D2F(0, 0)

    DJVM.I2C(0, 0)
    verify(intp).I2C(0, 0)

    DJVM.I2B(0, 0)
    verify(intp).I2B(0, 0)

    DJVM.I2S(0, 0)
    verify(intp).I2S(0, 0)
  }

  @Test
  void testOtherInsn() {
    DJVM.LCMP(0, 0)
    verify(intp).LCMP(0, 0)

    DJVM.FCMPL(0, 0)
    verify(intp).FCMPL(0, 0)

    DJVM.FCMPG(0, 0)
    verify(intp).FCMPG(0, 0)

    DJVM.DCMPL(0, 0)
    verify(intp).DCMPL(0, 0)

    DJVM.DCMPG(0, 0)
    verify(intp).DCMPG(0, 0)

    DJVM.IRETURN(0, 0)
    verify(intp).IRETURN(0, 0)

    DJVM.LRETURN(0, 0)
    verify(intp).LRETURN(0, 0)

    DJVM.FRETURN(0, 0)
    verify(intp).FRETURN(0, 0)

    DJVM.DRETURN(0, 0)
    verify(intp).DRETURN(0, 0)

    DJVM.ARETURN(0, 0)
    verify(intp).ARETURN(0, 0)

    DJVM.RETURN(0, 0)
    verify(intp).RETURN(0, 0)

    DJVM.ARRAYLENGTH(0, 0)
    verify(intp).ARRAYLENGTH(0, 0)

    DJVM.ATHROW(0, 0)
    verify(intp).ATHROW(0, 0)

    DJVM.MONITORENTER(0, 0)
    verify(intp).MONITORENTER(0, 0)

    DJVM.MONITOREXIT(0, 0)
    verify(intp).MONITOREXIT(0, 0)
  }

  @Test
  void testGETVALUE() {
    DJVM.GETVALUE_double(0.0D)
    verify(intp).GETVALUE_double(0.0D)

    DJVM.GETVALUE_long(0L)
    verify(intp).GETVALUE_long(0L)

    DJVM.GETVALUE_float(0.0F)
    verify(intp).GETVALUE_float(0.0F)

    DJVM.GETVALUE_int(0)
    verify(intp).GETVALUE_int(0)

    DJVM.GETVALUE_byte((byte)0)
    verify(intp).GETVALUE_byte((byte)0)

    DJVM.GETVALUE_char((char)0)
    verify(intp).GETVALUE_char((char)0)

    DJVM.GETVALUE_boolean(true)
    verify(intp).GETVALUE_boolean(true)

    DJVM.GETVALUE_Object("a")
    verify(intp).GETVALUE_Object("a")

    DJVM.GETVALUE_short((short)0)
    verify(intp).GETVALUE_short((short)0)

    DJVM.GETVALUE_void()
    verify(intp).GETVALUE_void()
  }

  @Test
  void testSpecials() {
    DJVM.INVOKEMETHOD_EXCEPTION()
    verify(intp).INVOKEMETHOD_EXCEPTION()

    DJVM.INVOKEMETHOD_END()
    verify(intp).INVOKEMETHOD_END()

    DJVM.MAKE_SYMBOLIC()
    verify(intp).MAKE_SYMBOLIC()

    DJVM.SPECIAL(0)
    verify(intp).SPECIAL(0)
  }
}