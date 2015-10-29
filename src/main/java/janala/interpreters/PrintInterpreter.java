package janala.interpreters;

import janala.logger.inst.*;

public class PrintInterpreter implements IVisitor {
  @Override
  public void visitAALOAD(AALOAD inst) {
    System.out.println(inst);
  }

  @Override
  public void visitAASTORE(AASTORE inst) {
    System.out.println(inst);
  }

  @Override
  public void visitACONST_NULL(ACONST_NULL inst) {
    System.out.println(inst);
  }

  @Override
  public void visitALOAD(ALOAD inst) {
    System.out.println(inst);
  }

  @Override
  public void visitANEWARRAY(ANEWARRAY inst) {
    System.out.println(inst);
  }

  @Override
  public void visitARETURN(ARETURN inst) {
    System.out.println(inst);
  }

  @Override
  public void visitARRAYLENGTH(ARRAYLENGTH inst) {
    System.out.println(inst);
  }

  @Override
  public void visitASTORE(ASTORE inst) {
    System.out.println(inst);
  }

  @Override
  public void visitATHROW(ATHROW inst) {
    System.out.println(inst);
  }

  @Override
  public void visitBALOAD(BALOAD inst) {
    System.out.println(inst);
  }

  @Override
  public void visitBASTORE(BASTORE inst) {
    System.out.println(inst);
  }

  @Override
  public void visitBIPUSH(BIPUSH inst) {
    System.out.println(inst);
  }

  @Override
  public void visitCALOAD(CALOAD inst) {
    System.out.println(inst);
  }

  @Override
  public void visitCASTORE(CASTORE inst) {
    System.out.println(inst);
  }

  @Override
  public void visitCHECKCAST(CHECKCAST inst) {
    System.out.println(inst);
  }

  @Override
  public void visitD2F(D2F inst) {
    System.out.println(inst);
  }

  @Override
  public void visitD2I(D2I inst) {
    System.out.println(inst);
  }

  @Override
  public void visitD2L(D2L inst) {
    System.out.println(inst);
  }

  @Override
  public void visitDADD(DADD inst) {
    System.out.println(inst);
  }

  public void visitDALOAD(DALOAD inst) {
    System.out.println(inst);
  }

  public void visitDASTORE(DASTORE inst) {
    System.out.println(inst);
  }

  public void visitDCMPG(DCMPG inst) {
    System.out.println(inst);
  }

  public void visitDCMPL(DCMPL inst) {
    System.out.println(inst);
  }

  public void visitDCONST_0(DCONST_0 inst) {
    System.out.println(inst);
  }

  public void visitDCONST_1(DCONST_1 inst) {
    System.out.println(inst);
  }

  public void visitDDIV(DDIV inst) {
    System.out.println(inst);
  }

  public void visitDLOAD(DLOAD inst) {
    System.out.println(inst);
  }

  public void visitDMUL(DMUL inst) {
    System.out.println(inst);
  }

  public void visitDNEG(DNEG inst) {
    System.out.println(inst);
  }

  public void visitDREM(DREM inst) {
    System.out.println(inst);
  }

  public void visitDRETURN(DRETURN inst) {
    System.out.println(inst);
  }

  public void visitDSTORE(DSTORE inst) {
    System.out.println(inst);
  }

  public void visitDSUB(DSUB inst) {
    System.out.println(inst);
  }

  public void visitDUP(DUP inst) {
    System.out.println(inst);
  }

  public void visitDUP2(DUP2 inst) {
    System.out.println(inst);
  }

  public void visitDUP2_X1(DUP2_X1 inst) {
    System.out.println(inst);
  }

  public void visitDUP2_X2(DUP2_X2 inst) {
    System.out.println(inst);
  }

  public void visitDUP_X1(DUP_X1 inst) {
    System.out.println(inst);
  }

  public void visitDUP_X2(DUP_X2 inst) {
    System.out.println(inst);
  }

  public void visitF2D(F2D inst) {
    System.out.println(inst);
  }

  public void visitF2I(F2I inst) {
    System.out.println(inst);
  }

  public void visitF2L(F2L inst) {
    System.out.println(inst);
  }

  public void visitFADD(FADD inst) {
    System.out.println(inst);
  }

  public void visitFALOAD(FALOAD inst) {
    System.out.println(inst);
  }

  public void visitFASTORE(FASTORE inst) {
    System.out.println(inst);
  }

  public void visitFCMPG(FCMPG inst) {
    System.out.println(inst);
  }

  public void visitFCMPL(FCMPL inst) {
    System.out.println(inst);
  }

  public void visitFCONST_0(FCONST_0 inst) {
    System.out.println(inst);
  }

  public void visitFCONST_1(FCONST_1 inst) {
    System.out.println(inst);
  }

  public void visitFCONST_2(FCONST_2 inst) {
    System.out.println(inst);
  }

  public void visitFDIV(FDIV inst) {
    System.out.println(inst);
  }

  public void visitFLOAD(FLOAD inst) {
    System.out.println(inst);
  }

  public void visitFMUL(FMUL inst) {
    System.out.println(inst);
  }

  public void visitFNEG(FNEG inst) {
    System.out.println(inst);
  }

  public void visitFREM(FREM inst) {
    System.out.println(inst);
  }

  public void visitFRETURN(FRETURN inst) {
    System.out.println(inst);
  }

  public void visitFSTORE(FSTORE inst) {
    System.out.println(inst);
  }

  public void visitFSUB(FSUB inst) {
    System.out.println(inst);
  }

  public void visitGETFIELD(GETFIELD inst) {
    System.out.println(inst);
  }

  public void visitGETSTATIC(GETSTATIC inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_Object(GETVALUE_Object inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_boolean(GETVALUE_boolean inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_byte(GETVALUE_byte inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_char(GETVALUE_char inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_double(GETVALUE_double inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_float(GETVALUE_float inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_int(GETVALUE_int inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_long(GETVALUE_long inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_short(GETVALUE_short inst) {
    System.out.println(inst);
  }

  public void visitGETVALUE_void(GETVALUE_void inst) {
    System.out.println(inst);
  }

  public void visitGOTO(GOTO inst) {
    System.out.println(inst);
  }

  public void visitI2B(I2B inst) {
    System.out.println(inst);
  }

  public void visitI2C(I2C inst) {
    System.out.println(inst);
  }

  public void visitI2D(I2D inst) {
    System.out.println(inst);
  }

  public void visitI2F(I2F inst) {
    System.out.println(inst);
  }

  public void visitI2L(I2L inst) {
    System.out.println(inst);
  }

  public void visitI2S(I2S inst) {
    System.out.println(inst);
  }

  public void visitIADD(IADD inst) {
    System.out.println(inst);
  }

  public void visitIALOAD(IALOAD inst) {
    System.out.println(inst);
  }

  public void visitIAND(IAND inst) {
    System.out.println(inst);
  }

  public void visitIASTORE(IASTORE inst) {
    System.out.println(inst);
  }

  public void visitICONST_0(ICONST_0 inst) {
    System.out.println(inst);
  }

  public void visitICONST_1(ICONST_1 inst) {
    System.out.println(inst);
  }

  public void visitICONST_2(ICONST_2 inst) {
    System.out.println(inst);
  }

  public void visitICONST_3(ICONST_3 inst) {
    System.out.println(inst);
  }

  public void visitICONST_4(ICONST_4 inst) {
    System.out.println(inst);
  }

  public void visitICONST_5(ICONST_5 inst) {
    System.out.println(inst);
  }

  public void visitICONST_M1(ICONST_M1 inst) {
    System.out.println(inst);
  }

  public void visitIDIV(IDIV inst) {
    System.out.println(inst);
  }

  public void visitIFEQ(IFEQ inst) {
    System.out.println(inst);
  }

  public void visitIFGE(IFGE inst) {
    System.out.println(inst);
  }

  public void visitIFGT(IFGT inst) {
    System.out.println(inst);
  }

  public void visitIFLE(IFLE inst) {
    System.out.println(inst);
  }

  public void visitIFLT(IFLT inst) {
    System.out.println(inst);
  }

  public void visitIFNE(IFNE inst) {
    System.out.println(inst);
  }

  public void visitIFNONNULL(IFNONNULL inst) {
    System.out.println(inst);
  }

  public void visitIFNULL(IFNULL inst) {
    System.out.println(inst);
  }

  public void visitIF_ACMPEQ(IF_ACMPEQ inst) {
    System.out.println(inst);
  }

  public void visitIF_ACMPNE(IF_ACMPNE inst) {
    System.out.println(inst);
  }

  public void visitIF_ICMPEQ(IF_ICMPEQ inst) {
    System.out.println(inst);
  }

  public void visitIF_ICMPGE(IF_ICMPGE inst) {
    System.out.println(inst);
  }

  public void visitIF_ICMPGT(IF_ICMPGT inst) {
    System.out.println(inst);
  }

  public void visitIF_ICMPLE(IF_ICMPLE inst) {
    System.out.println(inst);
  }

  public void visitIF_ICMPLT(IF_ICMPLT inst) {
    System.out.println(inst);
  }

  public void visitIF_ICMPNE(IF_ICMPNE inst) {
    System.out.println(inst);
  }

  public void visitIINC(IINC inst) {
    System.out.println(inst);
  }

  public void visitILOAD(ILOAD inst) {
    System.out.println(inst);
  }

  public void visitIMUL(IMUL inst) {
    System.out.println(inst);
  }

  public void visitINEG(INEG inst) {
    System.out.println(inst);
  }

  public void visitINSTANCEOF(INSTANCEOF inst) {
    System.out.println(inst);
  }

  public void visitINVOKEINTERFACE(INVOKEINTERFACE inst) {
    System.out.println(inst);
  }

  public void visitINVOKEMETHOD_EXCEPTION(INVOKEMETHOD_EXCEPTION inst) {
    System.out.println(inst);
  }

  public void visitINVOKESPECIAL(INVOKESPECIAL inst) {
    System.out.println(inst);
  }

  public void visitINVOKESTATIC(INVOKESTATIC inst) {
    System.out.println(inst);
  }

  public void visitINVOKEVIRTUAL(INVOKEVIRTUAL inst) {
    System.out.println(inst);
  }

  public void visitIOR(IOR inst) {
    System.out.println(inst);
  }

  public void visitIREM(IREM inst) {
    System.out.println(inst);
  }

  public void visitIRETURN(IRETURN inst) {
    System.out.println(inst);
  }

  public void visitISHL(ISHL inst) {
    System.out.println(inst);
  }

  public void visitISHR(ISHR inst) {
    System.out.println(inst);
  }

  public void visitISTORE(ISTORE inst) {
    System.out.println(inst);
  }

  public void visitISUB(ISUB inst) {
    System.out.println(inst);
  }

  public void visitIUSHR(IUSHR inst) {
    System.out.println(inst);
  }

  public void visitIXOR(IXOR inst) {
    System.out.println(inst);
  }

  public void visitJSR(JSR inst) {
    System.out.println(inst);
  }

  public void visitL2D(L2D inst) {
    System.out.println(inst);
  }

  public void visitL2F(L2F inst) {
    System.out.println(inst);
  }

  public void visitL2I(L2I inst) {
    System.out.println(inst);
  }

  public void visitLADD(LADD inst) {
    System.out.println(inst);
  }

  public void visitLALOAD(LALOAD inst) {
    System.out.println(inst);
  }

  public void visitLAND(LAND inst) {
    System.out.println(inst);
  }

  public void visitLASTORE(LASTORE inst) {
    System.out.println(inst);
  }

  public void visitLCMP(LCMP inst) {
    System.out.println(inst);
  }

  public void visitLCONST_0(LCONST_0 inst) {
    System.out.println(inst);
  }

  public void visitLCONST_1(LCONST_1 inst) {
    System.out.println(inst);
  }

  public void visitLDC_String(LDC_String inst) {
    System.out.println(inst);
  }

  public void visitLDC_double(LDC_double inst) {
    System.out.println(inst);
  }

  public void visitLDC_float(LDC_float inst) {
    System.out.println(inst);
  }

  public void visitLDC_int(LDC_int inst) {
    System.out.println(inst);
  }

  public void visitLDC_long(LDC_long inst) {
    System.out.println(inst);
  }

  public void visitLDC_Object(LDC_Object inst) {
    System.out.println(inst);
  }

  public void visitLDIV(LDIV inst) {
    System.out.println(inst);
  }

  public void visitLLOAD(LLOAD inst) {
    System.out.println(inst);
  }

  public void visitLMUL(LMUL inst) {
    System.out.println(inst);
  }

  public void visitLNEG(LNEG inst) {
    System.out.println(inst);
  }

  public void visitLOOKUPSWITCH(LOOKUPSWITCH inst) {
    System.out.println(inst);
  }

  public void visitLOR(LOR inst) {
    System.out.println(inst);
  }

  public void visitLREM(LREM inst) {
    System.out.println(inst);
  }

  public void visitLRETURN(LRETURN inst) {
    System.out.println(inst);
  }

  public void visitLSHL(LSHL inst) {
    System.out.println(inst);
  }

  public void visitLSHR(LSHR inst) {
    System.out.println(inst);
  }

  public void visitLSTORE(LSTORE inst) {
    System.out.println(inst);
  }

  public void visitLSUB(LSUB inst) {
    System.out.println(inst);
  }

  public void visitLUSHR(LUSHR inst) {
    System.out.println(inst);
  }

  public void visitLXOR(LXOR inst) {
    System.out.println(inst);
  }

  public void visitMONITORENTER(MONITORENTER inst) {
    System.out.println(inst);
  }

  public void visitMONITOREXIT(MONITOREXIT inst) {
    System.out.println(inst);
  }

  public void visitMULTIANEWARRAY(MULTIANEWARRAY inst) {
    System.out.println(inst);
  }

  public void visitNEW(NEW inst) {
    System.out.println(inst);
  }

  public void visitNEWARRAY(NEWARRAY inst) {
    System.out.println(inst);
  }

  public void visitNOP(NOP inst) {
    System.out.println(inst);
  }

  public void visitPOP(POP inst) {
    System.out.println(inst);
  }

  public void visitPOP2(POP2 inst) {
    System.out.println(inst);
  }

  public void visitPUTFIELD(PUTFIELD inst) {
    System.out.println(inst);
  }

  public void visitPUTSTATIC(PUTSTATIC inst) {
    System.out.println(inst);
  }

  public void visitRET(RET inst) {
    System.out.println(inst);
  }

  public void visitRETURN(RETURN inst) {
    System.out.println(inst);
  }

  public void visitSALOAD(SALOAD inst) {
    System.out.println(inst);
  }

  public void visitSASTORE(SASTORE inst) {
    System.out.println(inst);
  }

  public void visitSIPUSH(SIPUSH inst) {
    System.out.println(inst);
  }

  public void visitSWAP(SWAP inst) {
    System.out.println(inst);
  }

  public void visitTABLESWITCH(TABLESWITCH inst) {
    System.out.println(inst);
  }

  public void visitINVOKEMETHOD_END(INVOKEMETHOD_END inst) {
    System.out.println(inst);
  }

  public void visitMAKE_SYMBOLIC(MAKE_SYMBOLIC inst) {
    System.out.println(inst);
  }

  public void visitSPECIAL(SPECIAL inst) {
    System.out.println(inst);
  }

  public void setNext(Instruction next) {
  }
}
