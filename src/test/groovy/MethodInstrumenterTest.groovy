package janala.instrument

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Label
import org.objectweb.asm.ClassReader
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.TryCatchBlockNode

import java.io.InputStream
import janala.config.Config
import janala.testing.MethodRecorder
import janala.logger.ClassNames
import janala.logger.ObjectInfo

import org.junit.Before
import org.junit.Test


import groovy.transform.CompileStatic

@CompileStatic
class MethodInstrumenterTest {
  private MethodRecorder recorder
  private SnoopInstructionMethodAdapter ma
  private Coverage coverage
  private GlobalStateForInstrumentation state
  private ClassNames classNames

  @Before
  void setup() {
    Coverage cov = Coverage.get()
    GlobalStateForInstrumentation.instance.reset()
    Config.instance.analysisClass = "MyAnalysisClass"

    recorder = new MethodRecorder()
    coverage = mock(Coverage.class)
    state = new GlobalStateForInstrumentation()
    classNames = ClassNames.getInstance()
    ma = new SnoopInstructionMethodAdapter(recorder.getVisitor(), false,
      coverage, state, classNames)

  }

  private void testInsn(int opcode, String name) {
    ma.visitInsn(opcode)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(II)V")
    ev.visitInsn(opcode)

    assertEquals(expected, recorder)
  }

  @Test
  void testNOP() {
    testInsn(Opcodes.NOP, "NOP")
  }

  @Test
  void testACONST_NULL() {
    testInsn(Opcodes.ACONST_NULL, "ACONST_NULL")
  }

  @Test
  void testICONST_M1() {
    testInsn(Opcodes.ICONST_M1, "ICONST_M1")
  }

  @Test
  void testICONST_0() {
    testInsn(Opcodes.ICONST_0, "ICONST_0")
  }

  @Test
  void testICONST_1() {
    testInsn(Opcodes.ICONST_1, "ICONST_1")
  }

  @Test
  void testICONST_2() {
    testInsn(Opcodes.ICONST_2, "ICONST_2")
  }

  @Test
  void testICONST_3() {
    testInsn(Opcodes.ICONST_3, "ICONST_3")
  }

  @Test
  void testICONST_4() {
    testInsn(Opcodes.ICONST_4, "ICONST_4")
  }

  @Test
  void testICONST_5() {
    testInsn(Opcodes.ICONST_5, "ICONST_5")
  }

  @Test
  void testLCONST_0() {
    testInsn(Opcodes.LCONST_0, "LCONST_0")
  }

  @Test
  void testLCONST_1() {
    testInsn(Opcodes.LCONST_1, "LCONST_1")
  }

  @Test
  void testFCONST_0() {
    testInsn(Opcodes.FCONST_0, "FCONST_0")
  }

  @Test
  void testFCONST_1() {
    testInsn(Opcodes.FCONST_1, "FCONST_1")
  }

  @Test
  void testFCONST_2() {
    testInsn(Opcodes.FCONST_2, "FCONST_2")
  }

  @Test
  void testDCONST_0() {
    testInsn(Opcodes.DCONST_0, "DCONST_0")
  }

  @Test
  void testDCONST_1() {
    testInsn(Opcodes.DCONST_1, "DCONST_1")
  }

  @Test
  void testPOP() {
    testInsn(Opcodes.POP, "POP")
  }

  @Test
  void testPOP2() {
    testInsn(Opcodes.POP2, "POP2")
  }

  @Test
  void testDUP() {
    testInsn(Opcodes.DUP, "DUP")
  }

  @Test
  void testDUP_X1() {
    testInsn(Opcodes.DUP_X1, "DUP_X1")
  }

  @Test
  void testDUP_X2() {
    testInsn(Opcodes.DUP_X2, "DUP_X2")
  }

  @Test
  void testDUP2() {
    testInsn(Opcodes.DUP2, "DUP2")
  }

  @Test
  void testDUP2_X1() {
    testInsn(Opcodes.DUP2_X1, "DUP2_X1")
  }

  @Test
  void testDUP2_X2() {
    testInsn(Opcodes.DUP2_X2, "DUP2_X2")
  }

  @Test
  void testSWAP() {
    testInsn(Opcodes.SWAP, "SWAP")
  }

  @Test
  void testIADD() {
    testInsn(Opcodes.IADD, "IADD")
  }

  @Test
  void testLADD() {
    testInsn(Opcodes.LADD, "LADD")
  }

  @Test
  void testFADD() {
    testInsn(Opcodes.FADD, "FADD")
  }

  @Test
  void testDADD() {
    testInsn(Opcodes.DADD, "DADD")
  }

  @Test
  void testISUB() {
    testInsn(Opcodes.ISUB, "ISUB")
  }

  @Test
  void testLSUB() {
    testInsn(Opcodes.LSUB, "LSUB")
  }

  @Test
  void testFSUB() {
    testInsn(Opcodes.FSUB, "FSUB")
  }

  @Test
  void testDSUB() {
    testInsn(Opcodes.DSUB, "DSUB")
  }

  @Test
  void testIMUL() {
    testInsn(Opcodes.IMUL, "IMUL")
  }

  @Test
  void testLMUL() {
    testInsn(Opcodes.LMUL, "LMUL")
  }

  @Test
  void testFMUL() {
    testInsn(Opcodes.FMUL, "FMUL")
  }

  @Test
  void testDMUL() {
    testInsn(Opcodes.DMUL, "DMUL")
  }

  @Test
  void testFDIV() {
    testInsn(Opcodes.FDIV, "FDIV")
  }

  @Test
  void testDDIV() {
    testInsn(Opcodes.DDIV, "DDIV")
  }

  @Test
  void testFREM() {
    testInsn(Opcodes.FREM, "FREM")
  }

  @Test
  void testDREM() {
    testInsn(Opcodes.DREM, "DREM")
  }

  @Test
  void testINEG() {
    testInsn(Opcodes.INEG, "INEG")
  }

  @Test
  void testLNEG() {
    testInsn(Opcodes.LNEG, "LNEG")
  }

  @Test
  void testFNEG() {
    testInsn(Opcodes.FNEG, "FNEG")
  }

  @Test
  void testDNEG() {
    testInsn(Opcodes.DNEG, "DNEG")
  }

  @Test
  void testISHL() {
    testInsn(Opcodes.ISHL, "ISHL")
  }

  @Test
  void testISHR() {
    testInsn(Opcodes.ISHR, "ISHR")
  }

  @Test
  void testLSHL() {
    testInsn(Opcodes.LSHL, "LSHL")
  }

  @Test
  void testLSHR() {
    testInsn(Opcodes.LSHR, "LSHR")
  }

  @Test
  void testIUSHR() {
    testInsn(Opcodes.IUSHR, "IUSHR")
  }

  @Test
  void testLUSHR() {
    testInsn(Opcodes.LUSHR, "LUSHR")
  }

  @Test
  void testIAND() {
    testInsn(Opcodes.IAND, "IAND")
  }

  @Test
  void testLAND() {
    testInsn(Opcodes.LAND, "LAND")
  }

  @Test
  void testIOR() {
    testInsn(Opcodes.IOR, "IOR")
  }

  @Test
  void testLOR() {
    testInsn(Opcodes.LOR, "LOR")
  }

  @Test
  void testIXOR() {
    testInsn(Opcodes.IXOR, "IXOR")
  }

  @Test
  void testLXOR() {
    testInsn(Opcodes.LXOR, "LXOR")
  }

  @Test
  void testI2L() {
    testInsn(Opcodes.I2L, "I2L")
  }

  @Test
  void testI2F() {
    testInsn(Opcodes.I2F, "I2F")
  }

  @Test
  void testI2D() {
    testInsn(Opcodes.I2D, "I2D")
  }

  @Test
  void testL2I() {
    testInsn(Opcodes.L2I, "L2I")
  }

  @Test
  void testL2F() {
    testInsn(Opcodes.L2F, "L2F")
  }

  @Test
  void testL2D() {
    testInsn(Opcodes.L2D, "L2D")
  }

  @Test
  void testF2I() {
    testInsn(Opcodes.F2I, "F2I")
  }

  @Test
  void testF2L() {
    testInsn(Opcodes.F2L, "F2L")
  }

  @Test
  void testF2D() {
    testInsn(Opcodes.F2D, "F2D")
  }

  @Test
  void testD2I() {
    testInsn(Opcodes.D2I, "D2I")
  }

  @Test
  void testD2L() {
    testInsn(Opcodes.D2L, "D2L")
  }

  @Test
  void testD2F() {
    testInsn(Opcodes.D2F, "D2F")
  }

  @Test
  void testI2B() {
    testInsn(Opcodes.I2B, "I2B")
  }

  @Test
  void testI2C() {
    testInsn(Opcodes.I2C, "I2C")
  }

  @Test
  void testI2S() {
    testInsn(Opcodes.I2S, "I2S")
  }

  @Test
  void testLCMP() {
    testInsn(Opcodes.LCMP, "LCMP")
  }

  @Test
  void testFCMPL() {
    testInsn(Opcodes.FCMPL, "FCMPL")
  } 

  @Test
  void testFCMPG() {
    testInsn(Opcodes.FCMPG, "FCMPG")
  }  

  @Test
  void testDCMPL() {
    testInsn(Opcodes.DCMPL, "DCMPL")
  } 

  @Test
  void testDCMPG() {
    testInsn(Opcodes.DCMPG, "DCMPG")
  }  

  @Test
  void testIRETURN() {
    testInsn(Opcodes.IRETURN, "IRETURN")
  }

  @Test
  void testFRETURN() {
    testInsn(Opcodes.FRETURN, "FRETURN")
  }

  @Test
  void testLRETURN() {
    testInsn(Opcodes.LRETURN, "LRETURN")
  }

  @Test
  void testDRETURN() {
    testInsn(Opcodes.DRETURN, "DRETURN")
  }

  @Test
  void testRETURN() {
    testInsn(Opcodes.RETURN, "RETURN")
  }

  @Test
  void testARETURN() {
    testInsn(Opcodes.ARETURN, "ARETURN")
  }

  @Test
  void testATHROW() {
    testInsn(Opcodes.ATHROW, "ATHROW")
  }

  private void testInsnWithExceptionAndValue(int opcode, 
      String name, String type, String prefix) {
    ma.visitInsn(opcode)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(II)V")
    ev.visitInsn(opcode)

    Utils.addBipushInsn(ev, 0) // exception-free path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");
    Utils.addValueReadInsn(ev, type, prefix)

    assertEquals(expected, recorder)
  }

  @Test
  void testIALOAD() {
    testInsnWithExceptionAndValue(Opcodes.IALOAD, "IALOAD", "I", "GETVALUE_")
  }

  @Test
  void testLALOAD() {
    testInsnWithExceptionAndValue(Opcodes.LALOAD, "LALOAD", "J", "GETVALUE_")
  }

  @Test
  void testFALOAD() {
    testInsnWithExceptionAndValue(Opcodes.FALOAD, "FALOAD", "F", "GETVALUE_")
  }

  @Test
  void testDALOAD() {
    testInsnWithExceptionAndValue(Opcodes.DALOAD, "DALOAD", "D", "GETVALUE_")
  }

  @Test
  void testBALOAD() {
    testInsnWithExceptionAndValue(Opcodes.BALOAD, "BALOAD", "B", "GETVALUE_")
  }

  @Test
  void testCALOAD() {
    testInsnWithExceptionAndValue(Opcodes.CALOAD, "CALOAD", "C", "GETVALUE_")
  }

  @Test
  void testSALOAD() {
    testInsnWithExceptionAndValue(Opcodes.SALOAD, "SALOAD", "S", "GETVALUE_")
  }

  @Test
  void testAALOAD() {
    testInsnWithExceptionAndValue(Opcodes.AALOAD, "AALOAD", "Ljava/lang/Object;", "GETVALUE_")
  }

  @Test
  void testARRAYLENGTH() {
    testInsnWithExceptionAndValue(Opcodes.ARRAYLENGTH, 
      "ARRAYLENGTH", "I", "GETVALUE_")
  }

  private void testInsnWithException(int opcode, String name) {
    ma.visitInsn(opcode)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(II)V")
    ev.visitInsn(opcode)

    Utils.addBipushInsn(ev, 0) // exception-free path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");

    assertEquals(expected, recorder)
  }

  @Test
  void testIASTORE() {
    testInsnWithException(Opcodes.IASTORE, "IASTORE")
  }

  @Test
  void testLASTORE() {
    testInsnWithException(Opcodes.LASTORE, "LASTORE")
  }

  @Test
  void testFASTORE() {
    testInsnWithException(Opcodes.FASTORE, "FASTORE")
  }

  @Test
  void testDASTORE() {
    testInsnWithException(Opcodes.DASTORE, "DASTORE")
  }

  @Test
  void testBASTORE() {
    testInsnWithException(Opcodes.BASTORE, "BASTORE")
  }

  @Test
  void testCASTORE() {
    testInsnWithException(Opcodes.CASTORE, "CASTORE")
  }

  @Test
  void testSASTORE() {
    testInsnWithException(Opcodes.SASTORE, "SASTORE")
  }

  @Test
  void testAASTORE() {
    testInsnWithException(Opcodes.AASTORE, "AASTORE")
  }

  @Test
  void testIDIV() {
    testInsnWithException(Opcodes.IDIV, "IDIV")
  }

  @Test
  void testLDIV() {
    testInsnWithException(Opcodes.LDIV, "LDIV")
  }

  @Test
  void testIREM() {
    testInsnWithException(Opcodes.IREM, "IREM")
  }

  @Test
  void testLREM() {
    testInsnWithException(Opcodes.LREM, "LREM")
  }

  @Test
  void testMONITORENTER() {
    testInsnWithException(Opcodes.MONITORENTER, "MONITORENTER")
  }

  @Test
  void testMONITOREXIT() {
    testInsnWithException(Opcodes.MONITOREXIT, "MONITOREXIT")
  }

  private void testJumpInsn(int opcode, String name, Label label) {
    ma.visitJumpInsn(opcode, label)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    int iid = state.getId()
    int mid = state.getMid()
    Utils.addBipushInsn(ev, iid)
    Utils.addBipushInsn(ev, mid)
    Utils.addBipushInsn(ev, System.identityHashCode(label))

    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(III)V")
    ev.visitJumpInsn(opcode, label)

    Utils.addBipushInsn(ev, 1) // default path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");
 
    assertEquals(expected, recorder)
  }

  private void testUnconditionalJumpInsn(int opcode, String name, Label label) {
    ma.visitJumpInsn(opcode, label)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    int iid = state.getId()
    int mid = state.getMid()
    Utils.addBipushInsn(ev, iid)
    Utils.addBipushInsn(ev, mid)
    Utils.addBipushInsn(ev, System.identityHashCode(label))

    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(III)V")
    ev.visitJumpInsn(opcode, label)

    assertEquals(expected, recorder)
  }

  @Test
  void testIFEQ() {
    testJumpInsn(Opcodes.IFEQ, "IFEQ", new Label())
  }

  @Test
  void testIFNE() {
    testJumpInsn(Opcodes.IFNE, "IFNE", new Label())
  }

  @Test
  void testIFLT() {
    testJumpInsn(Opcodes.IFLT, "IFLT", new Label())
  }

  @Test
  void testIFGE() {
    testJumpInsn(Opcodes.IFGE, "IFGE", new Label())
  }

  @Test
  void testIFGT() {
    testJumpInsn(Opcodes.IFGT, "IFGT", new Label())
  }

  @Test
  void testIFLE() {
    testJumpInsn(Opcodes.IFLE, "IFLE", new Label())
  }  

  @Test
  void testIF_ICMPEQ() {
    testJumpInsn(Opcodes.IF_ICMPEQ, "IF_ICMPEQ", new Label())
  }

  @Test
  void testIF_ICMPNE() {
    testJumpInsn(Opcodes.IF_ICMPNE, "IF_ICMPNE", new Label())
  }  

  @Test
  void testIF_ICMPLT() {
    testJumpInsn(Opcodes.IF_ICMPLT, "IF_ICMPLT", new Label())
  }

  @Test
  void testIF_ICMPGE() {
    testJumpInsn(Opcodes.IF_ICMPGE, "IF_ICMPGE", new Label())
  }

  @Test
  void testIF_ICMPLE() {
    testJumpInsn(Opcodes.IF_ICMPLE, "IF_ICMPLE", new Label())
  }

  @Test
  void testIF_ICMPGT() {
    testJumpInsn(Opcodes.IF_ICMPGT, "IF_ICMPGT", new Label())
  }

  @Test
  void testIF_ACMPEQ() {
    testJumpInsn(Opcodes.IF_ACMPEQ, "IF_ACMPEQ", new Label())
  }

  @Test
  void testIF_ACMPNE() {
    testJumpInsn(Opcodes.IF_ACMPNE, "IF_ACMPNE", new Label())
  }

  @Test
  void testIFNULL() {
    testJumpInsn(Opcodes.IFNULL, "IFNULL", new Label())
  }

  @Test
  void testIFNONNULL() {
    testJumpInsn(Opcodes.IFNONNULL, "IFNONNULL", new Label())
  }

  @Test
  void testGOTO() {
    testUnconditionalJumpInsn(Opcodes.GOTO, "GOTO", new Label())
  }

  @Test
  void testJSR() {
    testUnconditionalJumpInsn(Opcodes.JSR, "JSR", new Label())
  }

  private void testMethodInsn(int opcode, String name, String owner, String method, String desc) {
    ma.visitMethodInsn(opcode, owner, method, desc)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    
    int iid = state.getId()
    int mid = state.getMid()
    Utils.addBipushInsn(ev, iid)
    Utils.addBipushInsn(ev, mid)

    ev.visitLdcInsn(owner)
    ev.visitLdcInsn(method)
    ev.visitLdcInsn(desc)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name,
      "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")

    Label begin = new Label() // Try-catch block around call
    Label handler = new Label()
    Label end = new Label()
    ev.visitLabel(begin)
    ev.visitMethodInsn(opcode, owner, method, desc)
    ev.visitJumpInsn(Opcodes.GOTO, end)

    ev.visitLabel(handler)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, Config.instance.analysisClass, 
      "INVOKEMETHOD_EXCEPTION", "()V")
    ev.visitInsn(Opcodes.ATHROW)

    ev.visitLabel(end)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, Config.instance.analysisClass, 
      "INVOKEMETHOD_END", "()V")
    Utils.addValueReadInsn(ev, desc, "GETVALUE_")
 
    assertEquals(expected, recorder)
  }  

  @Test
  void testINVOKEVIRTUAL() {
    testMethodInsn(Opcodes.INVOKEVIRTUAL, "INVOKEVIRTUAL", "class", "method", "(I)I")
  }

  @Test
  void testINVOKESTATIC() {
    testMethodInsn(Opcodes.INVOKESTATIC, "INVOKESTATIC", "class", "method", "(I)I")
  }

  @Test
  void testINVOKESPECIAL() {
    testMethodInsn(Opcodes.INVOKESPECIAL, "INVOKESPECIAL", "class", "method", "(I)I")
  }

  @Test
  void testINVOKEINTERFACE() {
    testMethodInsn(Opcodes.INVOKEINTERFACE, "INVOKEINTERFACE", "class", "method", "(I)I")
  }

  private void testTypeInsn(int opcode, String name, String type, boolean getValue) {
    ma.visitTypeInsn(opcode, type)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    int iid = state.getId()
    int mid = state.getMid()
    Utils.addBipushInsn(ev, iid)
    Utils.addBipushInsn(ev, mid)
    ev.visitLdcInsn(type)
    
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(IILjava/lang/String;)V")
    ev.visitTypeInsn(opcode, type)

    Utils.addBipushInsn(ev, 0) // exception-free path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");
 
    if (getValue) {
      // special case for getting integer value.
      Utils.addValueReadInsn(ev, "I", "GETVALUE_")
    }
    assertEquals(expected, recorder)
  }

  @Test
  void testANEWARRAY() {
    testTypeInsn(Opcodes.ANEWARRAY, "ANEWARRAY", "java/lang/String", false)
  }

  @Test
  void testCHECKCAST() {
    testTypeInsn(Opcodes.CHECKCAST, "CHECKCAST", "java/lang/String", false)
  }

  @Test
  void testNEW() {
    def type = "java/lang/String"
    def name = "NEW"
    def opcode  = Opcodes.NEW
    ma.visitTypeInsn(Opcodes.NEW, type)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    int iid = state.getId()
    int mid = state.getMid()
    Utils.addBipushInsn(ev, iid)
    Utils.addBipushInsn(ev, mid)
    ev.visitLdcInsn(type)
    int cIdx = classNames.get(type);
    Utils.addBipushInsn(ev, cIdx);
    
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(IILjava/lang/String;I)V")
    ev.visitTypeInsn(opcode, type)

    Utils.addBipushInsn(ev, 0) // exception-free path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");

    assertEquals(expected, recorder)
  }

  @Test
  void testINSTANCEOF() {
    testTypeInsn(Opcodes.INSTANCEOF, "INSTANCEOF", "java/lang/String", true)
  }

  private void testVarLoad(int opcode, String name, String type) {
    ma.visitVarInsn(opcode, 1)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(III)V")
    ev.visitVarInsn(opcode, 1)
    Utils.addValueReadInsn(ev, type, "GETVALUE_")

    assertEquals(expected, recorder)    
  }

  @Test
  void testILOAD() {
    testVarLoad(Opcodes.ILOAD, "ILOAD", "I")
  }

  @Test
  void testLLOAD() {
    testVarLoad(Opcodes.LLOAD, "LLOAD", "J")
  }

  @Test
  void testFLOAD() {
    testVarLoad(Opcodes.FLOAD, "FLOAD", "F")
  }

  @Test
  void testDLOAD() {
    testVarLoad(Opcodes.DLOAD, "DLOAD", "D")
  }

  @Test
  void testALOAD() {
    testVarLoad(Opcodes.ALOAD, "ALOAD", "Ljava/lang/Object;")
  }

  private void testVarStore(int opcode, String name) {
    ma.visitVarInsn(opcode, 1)

    MethodRecorder expected = new MethodRecorder()
    MethodVisitor ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(III)V")
    ev.visitVarInsn(opcode, 1)

    assertEquals(expected, recorder)
  }

  @Test
  void testISTORE() {
    testVarStore(Opcodes.ISTORE, "ISTORE")
  }
  
  @Test
  void testLSTORE() {
    testVarStore(Opcodes.LSTORE, "LSTORE")
  }

  @Test
  void testFSTORE() {
    testVarStore(Opcodes.FSTORE, "FSTORE")
  }

  @Test
  void testDSTORE() {
    testVarStore(Opcodes.DSTORE, "DSTORE")
  }

  @Test
  void testASTORE() {
    testVarStore(Opcodes.ASTORE, "ASTORE")
  }

  @Test
  void testRET() {
    testVarStore(Opcodes.RET, "RET")
  }

  private void testIntInsn(int opcode, String name, int operand) {
    ma.visitIntInsn(opcode, operand)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    Utils.addBipushInsn(ev, operand)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, name, "(III)V", false)    
    ev.visitIntInsn(opcode, operand)

    assertEquals(expected, recorder)
  }

  @Test
  void testBIPUSH() {
    testIntInsn(Opcodes.BIPUSH, "BIPUSH", 1)
  }

  @Test
  void testSIPUSH() {
    testIntInsn(Opcodes.SIPUSH, "SIPUSH", 1)
  }

  @Test
  void testNEWARRAY() {
    ma.visitIntInsn(Opcodes.NEWARRAY, 1)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "NEWARRAY", "(II)V", false)    
    ev.visitIntInsn(Opcodes.NEWARRAY, 1)
    Utils.addSpecialInsn(ev, 0)

    assertEquals(expected, recorder)
  }

  private void testLdcForType(Object obj, String type) {
    ma.visitLdcInsn(obj)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    ev.visitLdcInsn(obj)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "LDC", "(II"+ type + ")V", false)
    ev.visitLdcInsn(obj)

    assertEquals(expected, recorder)
  }

  @Test
  void testLdcForInt() {
    testLdcForType(new Integer(1), "I");
  }

  @Test
  void testLdcForLong() {
    testLdcForType(new Long(1L), "J");
  }

  @Test
  void testLdcForFloat() {
    testLdcForType(new Float(1.0F), "F");
  }

  @Test
  void testLdcForDouble() {
    testLdcForType(new Double(1.0), "D");
  }

  @Test
  void testLdcForString() {
    testLdcForType("a", "Ljava/lang/String;");
  }

  @Test
  void testLdcForArray() {
    testLdcForType([1], "Ljava/lang/Object;");
  }

  private testFieldInsn(int opcode, String name, String owner, String field, 
    String desc, boolean isStatic, boolean isGet) {
    ma.visitFieldInsn(opcode, owner, field, desc)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    
    int cIdx = classNames.get(owner)
    Utils.addBipushInsn(ev, cIdx)
    ObjectInfo tmp = classNames.get(cIdx)
    int fIdx = tmp.getIdx(field, isStatic)
    Utils.addBipushInsn(ev, fIdx)
    ev.visitLdcInsn(desc)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, Config.instance.analysisClass, 
      name, "(IIIILjava/lang/String;)V", false)
    ev.visitFieldInsn(opcode, owner, field, desc)

    Utils.addSpecialInsn(ev, 0)
    if (isGet) {
      Utils.addValueReadInsn(ev, desc, "GETVALUE_")
    }

    assertEquals(expected, recorder)
  }

  @Test
  void testGETSTATIC() {
     testFieldInsn(Opcodes.GETSTATIC, "GETSTATIC", "janala/interpreters/TestClass", "c",
      "I", true, true)
  }

  @Test
  void testPUTSTATIC() {
     testFieldInsn(Opcodes.PUTSTATIC, "PUTSTATIC", "janala/interpreters/TestClass", "c",
      "I", true, false)
  }

  @Test
  void testGETFIELD() {
     testFieldInsn(Opcodes.GETFIELD, "GETFIELD", "janala/interpreters/TestClass", "a",
      "I", false, true)
  }

  @Test
  void testPUTFIELD() {
     testFieldInsn(Opcodes.PUTFIELD, "PUTFIELD", "janala/interpreters/TestClass", "a",
      "I", false, false)
  }

  @Test
  void testIincInsn() {
    ma.visitIincInsn(1, 1)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    Utils.addBipushInsn(ev, 1)
    Utils.addBipushInsn(ev, 1)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, Config.instance.analysisClass, 
      "IINC", "(IIII)V", false)
    ev.visitIincInsn(1, 1)

    assertEquals(expected, recorder)
  }

  @Test
  void testTableSwitchInsn() {
    Label defaultLabel = new Label()
    Label[] labels = new Label[1]
    labels[0] = new Label()
    ma.visitTableSwitchInsn(0, 0, defaultLabel, labels)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()

    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    Utils.addBipushInsn(ev, 0)
    Utils.addBipushInsn(ev, 0)
    Utils.addBipushInsn(ev, System.identityHashCode(defaultLabel))
    Utils.addBipushInsn(ev, 1)
    ev.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT);
    ev.visitInsn(Opcodes.DUP)
    Utils.addBipushInsn(ev, 0)

    Utils.addBipushInsn(ev, System.identityHashCode(labels[0])); 
    ev.visitInsn(Opcodes.IASTORE);
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "TABLESWITCH", "(IIIII[I)V", false);
    ev.visitTableSwitchInsn(0, 0, defaultLabel, labels);

    assertEquals(expected, recorder)
  }

  @Test
  void testLookupSwitchInsn() {
    Label defaultLabel = new Label()
    Label[] labels = new Label[1]
    labels[0] = new Label()
    int[] keys = new int[1]
    keys[0] = 0
    ma.visitLookupSwitchInsn(defaultLabel, keys, labels)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()

    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    Utils.addBipushInsn(ev, System.identityHashCode(defaultLabel))
    Utils.addBipushInsn(ev, 1)
    ev.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT)
    ev.visitInsn(Opcodes.DUP)
    Utils.addBipushInsn(ev, 0)
    Utils.addBipushInsn(ev, 0)
    ev.visitInsn(Opcodes.IASTORE)

    Utils.addBipushInsn(ev, 1)
    ev.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT)
    ev.visitInsn(Opcodes.DUP)
    Utils.addBipushInsn(ev, 0)
    Utils.addBipushInsn(ev, System.identityHashCode(labels[0]))
    ev.visitInsn(Opcodes.IASTORE)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, "LOOKUPSWITCH", "(III[I[I)V", false);
    ev.visitLookupSwitchInsn(defaultLabel, keys, labels);

    assertEquals(expected, recorder)
  }

  @Test
  void testMultiNewArray() {
    ma.visitMultiANewArrayInsn("I", 2)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    Utils.addBipushInsn(ev, state.getId())
    Utils.addBipushInsn(ev, state.getMid())
    ev.visitLdcInsn("I")
    Utils.addBipushInsn(ev, 2)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, "MULTIANEWARRAY", "(IILjava/lang/String;I)V", false)
    ev.visitMultiANewArrayInsn("I", 2)
    Utils.addSpecialInsn(ev, 0)

    assertEquals(expected, recorder)
  }
}
