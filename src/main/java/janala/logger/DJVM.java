package janala.logger;

import janala.config.Config;

public final class DJVM {
  private static Logger intp = Config.instance.getLogger();

  private DJVM() {} 

  // For testing purposes
  public static void setInterpreter(Logger logger) {
    intp = logger;
  }

  public static void LDC(int iid, int mid, int c) {
    intp.LDC(iid, mid, c);
  }

  public static void LDC(int iid, int mid, long c) {
    intp.LDC(iid, mid, c);
  }

  public static void LDC(int iid, int mid, float c) {
    intp.LDC(iid, mid, c);
  }
  
  public static void LDC(int iid, int mid, double c) {
    intp.LDC(iid, mid, c);
  }
  
  public static void LDC(int iid, int mid, String c) {
    intp.LDC(iid, mid, c);
  }

  public static void LDC(int iid, int mid, Object c) {
    intp.LDC(iid, mid, c);
  }

  public static void IINC(int iid, int mid, int var, int increment) {
    intp.IINC(iid, mid, var, increment);
  }

  public static void MULTIANEWARRAY(int iid, int mid, String desc, int dims) {
    intp.MULTIANEWARRAY(iid, mid, desc, dims);
  }

  public static void LOOKUPSWITCH(int iid, int mid, int dflt, int[] keys, int[] labels) {
    intp.LOOKUPSWITCH(iid, mid, dflt, keys, labels);
  }

  public static void TABLESWITCH(int iid, int mid, int min, int max, int dflt, int[] labels) {
    intp.TABLESWITCH(iid, mid, min, max, dflt, labels);
  }

  public static void IFEQ(int iid, int mid, int label) {
    intp.IFEQ(iid, mid, label);
  }

  public static void IFNE(int iid, int mid, int label) {
    intp.IFNE(iid, mid, label);
  }

  public static void IFLT(int iid, int mid, int label) {
    intp.IFLT(iid, mid, label);
  }

  public static void IFGE(int iid, int mid, int label) {
    intp.IFGE(iid, mid, label);
  }

  public static void IFGT(int iid, int mid, int label) {
    intp.IFGT(iid, mid, label);
  }

  public static void IFLE(int iid, int mid, int label) {
    intp.IFLE(iid, mid, label);
  }

  public static void IF_ICMPEQ(int iid, int mid, int label) {
    intp.IF_ICMPEQ(iid, mid, label);
  }

  public static void IF_ICMPNE(int iid, int mid, int label) {
    intp.IF_ICMPNE(iid, mid, label);
  }

  public static void IF_ICMPLT(int iid, int mid, int label) {
    intp.IF_ICMPLT(iid, mid, label);
  }

  public static void IF_ICMPGE(int iid, int mid, int label) {
    intp.IF_ICMPGE(iid, mid, label);
  }

  public static void IF_ICMPGT(int iid, int mid, int label) {
    intp.IF_ICMPGT(iid, mid, label);
  }

  public static void IF_ICMPLE(int iid, int mid, int label) {
    intp.IF_ICMPLE(iid, mid, label);
  }

  public static void IF_ACMPEQ(int iid, int mid, int label) {
    intp.IF_ACMPEQ(iid, mid, label);
  }

  public static void IF_ACMPNE(int iid, int mid, int label) {
    intp.IF_ACMPNE(iid, mid, label);
  }

  public static void GOTO(int iid, int mid, int label) {
    intp.GOTO(iid, mid, label);
  }

  public static void JSR(int iid, int mid, int label) {
    intp.JSR(iid, mid, label);
  }

  public static void IFNULL(int iid, int mid, int label) {
    intp.IFNULL(iid, mid, label);
  }

  public static void IFNONNULL(int iid, int mid, int label) {
    intp.IFNONNULL(iid, mid, label);
  }

  public static void INVOKEVIRTUAL(int iid, int mid, String owner, String name, String desc) {
    intp.INVOKEVIRTUAL(iid, mid, owner, name, desc);
  }

  public static void INVOKESPECIAL(int iid, int mid, String owner, String name, String desc) {
    intp.INVOKESPECIAL(iid, mid, owner, name, desc);
  }

  public static void INVOKESTATIC(int iid, int mid, String owner, String name, String desc) {
    intp.INVOKESTATIC(iid, mid, owner, name, desc);
  }

  public static void INVOKEINTERFACE(int iid, int mid, String owner, String name, String desc) {
    intp.INVOKEINTERFACE(iid, mid, owner, name, desc);
  }

  public static void GETSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
    intp.GETSTATIC(iid, mid, cIdx, fIdx, desc);
  }

  public static void PUTSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
    intp.PUTSTATIC(iid, mid, cIdx, fIdx, desc);
  }

  public static void GETFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
    intp.GETFIELD(iid, mid, cIdx, fIdx, desc);
  }

  public static void PUTFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
    intp.PUTFIELD(iid, mid, cIdx, fIdx, desc);
  }

  public static void NEW(int iid, int mid, String type, int cIdx) {
    intp.NEW(iid, mid, type, cIdx);
  }

  public static void ANEWARRAY(int iid, int mid, String type) {
    intp.ANEWARRAY(iid, mid, type);
  }

  public static void CHECKCAST(int iid, int mid, String type) {
    intp.CHECKCAST(iid, mid, type);
  }

  public static void INSTANCEOF(int iid, int mid, String type) {
    intp.INSTANCEOF(iid, mid, type);
  }

  public static void BIPUSH(int iid, int mid, int value) {
    intp.BIPUSH(iid, mid, value);
  }

  public static void SIPUSH(int iid, int mid, int value) {
    intp.SIPUSH(iid, mid, value);
  }

  public static void NEWARRAY(int iid, int mid) {
    intp.NEWARRAY(iid, mid);
  }

  public static void ILOAD(int iid, int mid, int var) {
    intp.ILOAD(iid, mid, var);
  }

  public static void LLOAD(int iid, int mid, int var) {
    intp.LLOAD(iid, mid, var);
  }

  public static void FLOAD(int iid, int mid, int var) {
    intp.FLOAD(iid, mid, var);
  }

  public static void DLOAD(int iid, int mid, int var) {
    intp.DLOAD(iid, mid, var);
  }

  public static void ALOAD(int iid, int mid, int var) {
    intp.ALOAD(iid, mid, var);
  }

  public static void ISTORE(int iid, int mid, int var) {
    intp.ISTORE(iid, mid, var);
  }

  public static void LSTORE(int iid, int mid, int var) {
    intp.LSTORE(iid, mid, var);
  }

  public static void FSTORE(int iid, int mid, int var) {
    intp.FSTORE(iid, mid, var);
  }

  public static void DSTORE(int iid, int mid, int var) {
    intp.DSTORE(iid, mid, var);
  }

  public static void ASTORE(int iid, int mid, int var) {
    intp.ASTORE(iid, mid, var);
  }

  public static void RET(int iid, int mid, int var) {
    intp.RET(iid, mid, var);
  }

  public static void NOP(int iid, int mid) {
    intp.NOP(iid, mid);
  }

  public static void ACONST_NULL(int iid, int mid) {
    intp.ACONST_NULL(iid, mid);
  }

  public static void ICONST_M1(int iid, int mid) {
    intp.ICONST_M1(iid, mid);
  }

  public static void ICONST_0(int iid, int mid) {
    intp.ICONST_0(iid, mid);
  }

  public static void ICONST_1(int iid, int mid) {
    intp.ICONST_1(iid, mid);
  }

  public static void ICONST_2(int iid, int mid) {
    intp.ICONST_2(iid, mid);
  }

  public static void ICONST_3(int iid, int mid) {
    intp.ICONST_3(iid, mid);
  }

  public static void ICONST_4(int iid, int mid) {
    intp.ICONST_4(iid, mid);
  }

  public static void ICONST_5(int iid, int mid) {
    intp.ICONST_5(iid, mid);
  }

  public static void LCONST_0(int iid, int mid) {
    intp.LCONST_0(iid, mid);
  }

  public static void LCONST_1(int iid, int mid) {
    intp.LCONST_1(iid, mid);
  }

  public static void FCONST_0(int iid, int mid) {
    intp.FCONST_0(iid, mid);
  }

  public static void FCONST_1(int iid, int mid) {
    intp.FCONST_1(iid, mid);
  }

  public static void FCONST_2(int iid, int mid) {
    intp.FCONST_2(iid, mid);
  }

  public static void DCONST_0(int iid, int mid) {
    intp.DCONST_0(iid, mid);
  }

  public static void DCONST_1(int iid, int mid) {
    intp.DCONST_1(iid, mid);
  }

  public static void IALOAD(int iid, int mid) {
    intp.IALOAD(iid, mid);
  }

  public static void LALOAD(int iid, int mid) {
    intp.LALOAD(iid, mid);
  }

  public static void FALOAD(int iid, int mid) {
    intp.FALOAD(iid, mid);
  }

  public static void DALOAD(int iid, int mid) {
    intp.DALOAD(iid, mid);
  }

  public static void AALOAD(int iid, int mid) {
    intp.AALOAD(iid, mid);
  }

  public static void BALOAD(int iid, int mid) {
    intp.BALOAD(iid, mid);
  }

  public static void CALOAD(int iid, int mid) {
    intp.CALOAD(iid, mid);
  }

  public static void SALOAD(int iid, int mid) {
    intp.SALOAD(iid, mid);
  }

  public static void IASTORE(int iid, int mid) {
    intp.IASTORE(iid, mid);
  }

  public static void LASTORE(int iid, int mid) {
    intp.LASTORE(iid, mid);
  }

  public static void FASTORE(int iid, int mid) {
    intp.FASTORE(iid, mid);
  }

  public static void DASTORE(int iid, int mid) {
    intp.DASTORE(iid, mid);
  }

  public static void AASTORE(int iid, int mid) {
    intp.AASTORE(iid, mid);
  }

  public static void BASTORE(int iid, int mid) {
    intp.BASTORE(iid, mid);
  }

  public static void CASTORE(int iid, int mid) {
    intp.CASTORE(iid, mid);
  }

  public static void SASTORE(int iid, int mid) {
    intp.SASTORE(iid, mid);
  }

  public static void POP(int iid, int mid) {
    intp.POP(iid, mid);
  }

  public static void POP2(int iid, int mid) {
    intp.POP2(iid, mid);
  }

  public static void DUP(int iid, int mid) {
    intp.DUP(iid, mid);
  }

  public static void DUP_X1(int iid, int mid) {
    intp.DUP_X1(iid, mid);
  }

  public static void DUP_X2(int iid, int mid) {
    intp.DUP_X2(iid, mid);
  }

  public static void DUP2(int iid, int mid) {
    intp.DUP2(iid, mid);
  }

  public static void DUP2_X1(int iid, int mid) {
    intp.DUP2_X1(iid, mid);
  }

  public static void DUP2_X2(int iid, int mid) {
    intp.DUP2_X2(iid, mid);
  }

  public static void SWAP(int iid, int mid) {
    intp.SWAP(iid, mid);
  }

  public static void IADD(int iid, int mid) {
    intp.IADD(iid, mid);
  }

  public static void LADD(int iid, int mid) {
    intp.LADD(iid, mid);
  }

  public static void FADD(int iid, int mid) {
    intp.FADD(iid, mid);
  }

  public static void DADD(int iid, int mid) {
    intp.DADD(iid, mid);
  }

  public static void ISUB(int iid, int mid) {
    intp.ISUB(iid, mid);
  }

  public static void LSUB(int iid, int mid) {
    intp.LSUB(iid, mid);
  }

  public static void FSUB(int iid, int mid) {
    intp.FSUB(iid, mid);
  }

  public static void DSUB(int iid, int mid) {
    intp.DSUB(iid, mid);
  }

  public static void IMUL(int iid, int mid) {
    intp.IMUL(iid, mid);
  }

  public static void LMUL(int iid, int mid) {
    intp.LMUL(iid, mid);
  }

  public static void FMUL(int iid, int mid) {
    intp.FMUL(iid, mid);
  }

  public static void DMUL(int iid, int mid) {
    intp.DMUL(iid, mid);
  }

  public static void IDIV(int iid, int mid) {
    intp.IDIV(iid, mid);
  }

  public static void LDIV(int iid, int mid) {
    intp.LDIV(iid, mid);
  }

  public static void FDIV(int iid, int mid) {
    intp.FDIV(iid, mid);
  }

  public static void DDIV(int iid, int mid) {
    intp.DDIV(iid, mid);
  }

  public static void IREM(int iid, int mid) {
    intp.IREM(iid, mid);
  }

  public static void LREM(int iid, int mid) {
    intp.LREM(iid, mid);
  }

  public static void FREM(int iid, int mid) {
    intp.FREM(iid, mid);
  }

  public static void DREM(int iid, int mid) {
    intp.DREM(iid, mid);
  }

  public static void INEG(int iid, int mid) {
    intp.INEG(iid, mid);
  }

  public static void LNEG(int iid, int mid) {
    intp.LNEG(iid, mid);
  }

  public static void FNEG(int iid, int mid) {
    intp.FNEG(iid, mid);
  }

  public static void DNEG(int iid, int mid) {
    intp.DNEG(iid, mid);
  }

  public static void ISHL(int iid, int mid) {
    intp.ISHL(iid, mid);
  }

  public static void LSHL(int iid, int mid) {
    intp.LSHL(iid, mid);
  }

  public static void ISHR(int iid, int mid) {
    intp.ISHR(iid, mid);
  }

  public static void LSHR(int iid, int mid) {
    intp.LSHR(iid, mid);
  }

  public static void IUSHR(int iid, int mid) {
    intp.IUSHR(iid, mid);
  }

  public static void LUSHR(int iid, int mid) {
    intp.LUSHR(iid, mid);
  }

  public static void IAND(int iid, int mid) {
    intp.IAND(iid, mid);
  }

  public static void LAND(int iid, int mid) {
    intp.LAND(iid, mid);
  }

  public static void IOR(int iid, int mid) {
    intp.IOR(iid, mid);
  }

  public static void LOR(int iid, int mid) {
    intp.LOR(iid, mid);
  }

  public static void IXOR(int iid, int mid) {
    intp.IXOR(iid, mid);
  }

  public static void LXOR(int iid, int mid) {
    intp.LXOR(iid, mid);
  }

  public static void I2L(int iid, int mid) {
    intp.I2L(iid, mid);
  }

  public static void I2F(int iid, int mid) {
    intp.I2F(iid, mid);
  }

  public static void I2D(int iid, int mid) {
    intp.I2D(iid, mid);
  }

  public static void L2I(int iid, int mid) {
    intp.L2I(iid, mid);
  }

  public static void L2F(int iid, int mid) {
    intp.L2F(iid, mid);
  }

  public static void L2D(int iid, int mid) {
    intp.L2D(iid, mid);
  }

  public static void F2I(int iid, int mid) {
    intp.F2I(iid, mid);
  }

  public static void F2L(int iid, int mid) {
    intp.F2L(iid, mid);
  }

  public static void F2D(int iid, int mid) {
    intp.F2D(iid, mid);
  }

  public static void D2I(int iid, int mid) {
    intp.D2I(iid, mid);
  }

  public static void D2L(int iid, int mid) {
    intp.D2L(iid, mid);
  }

  public static void D2F(int iid, int mid) {
    intp.D2F(iid, mid);
  }

  public static void I2B(int iid, int mid) {
    intp.I2B(iid, mid);
  }

  public static void I2C(int iid, int mid) {
    intp.I2C(iid, mid);
  }

  public static void I2S(int iid, int mid) {
    intp.I2S(iid, mid);
  }

  public static void LCMP(int iid, int mid) {
    intp.LCMP(iid, mid);
  }

  public static void FCMPL(int iid, int mid) {
    intp.FCMPL(iid, mid);
  }

  public static void FCMPG(int iid, int mid) {
    intp.FCMPG(iid, mid);
  }

  public static void DCMPL(int iid, int mid) {
    intp.DCMPL(iid, mid);
  }

  public static void DCMPG(int iid, int mid) {
    intp.DCMPG(iid, mid);
  }

  public static void IRETURN(int iid, int mid) {
    intp.IRETURN(iid, mid);
  }

  public static void LRETURN(int iid, int mid) {
    intp.LRETURN(iid, mid);
  }

  public static void FRETURN(int iid, int mid) {
    intp.FRETURN(iid, mid);
  }

  public static void DRETURN(int iid, int mid) {
    intp.DRETURN(iid, mid);
  }

  public static void ARETURN(int iid, int mid) {
    intp.ARETURN(iid, mid);
  }

  public static void RETURN(int iid, int mid) {
    intp.RETURN(iid, mid);
  }

  public static void ARRAYLENGTH(int iid, int mid) {
    intp.ARRAYLENGTH(iid, mid);
  }

  public static void ATHROW(int iid, int mid) {
    intp.ATHROW(iid, mid);
  }

  public static void MONITORENTER(int iid, int mid) {
    intp.MONITORENTER(iid, mid);
  }

  public static void MONITOREXIT(int iid, int mid) {
    intp.MONITOREXIT(iid, mid);
  }

  public static void GETVALUE_double(double v) {
    intp.GETVALUE_double(v);
  }

  public static void GETVALUE_long(long v) {
    intp.GETVALUE_long(v);
  }

  public static void GETVALUE_Object(Object v) {
    intp.GETVALUE_Object(v);
  }

  public static void GETVALUE_boolean(boolean v) {
    intp.GETVALUE_boolean(v);
  }

  public static void GETVALUE_byte(byte v) {
    intp.GETVALUE_byte(v);
  }

  public static void GETVALUE_char(char v) {
    intp.GETVALUE_char(v);
  }

  public static void GETVALUE_float(float v) {
    intp.GETVALUE_float(v);
  }

  public static void GETVALUE_int(int v) {
    intp.GETVALUE_int(v);
  }

  public static void GETVALUE_short(short v) {
    intp.GETVALUE_short(v);
  }

  public static void GETVALUE_void() {
    intp.GETVALUE_void();
  }

  public static void INVOKEMETHOD_EXCEPTION() {
    intp.INVOKEMETHOD_EXCEPTION();
  }

  public static void INVOKEMETHOD_END() {
    intp.INVOKEMETHOD_END();
  }

  public static void SPECIAL(int i) {
    intp.SPECIAL(i);
  }

  public static void MAKE_SYMBOLIC() {
    intp.MAKE_SYMBOLIC();
  }

  public static void flush() {
    intp.flush();
  }
}
