/*
 * Copyright (c) 2014, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger;

import janala.config.Config;
import janala.interpreters.ConcolicInterpreter;
import janala.logger.inst.*;
import janala.utils.MyLogger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/16/12
 * Time: 5:41 PM
 */


public class DirectConcolicExecution extends Thread implements Logger {
    Instruction inst, next;
    java.util.logging.Logger tester = MyLogger.getTestLogger(Config.mainClass + "." + Config.iteration);
    IVisitor intp = null;

    public DirectConcolicExecution() {
        intp = new ConcolicInterpreter(ClassNames.instance);
        Runtime.getRuntime().addShutdownHook(this);
    }

    @Override
    public void run() {
        execute(null);
        ((ConcolicInterpreter)intp).endExecution();
        MyLogger.checkLog(tester);
    }

    private void execute(Instruction insn) {
        if (Config.instance.printTrace)
            System.out.println(insn);


        if (inst == null) {
            inst = insn;
        } else {
            next = insn;
            intp.setNext(next);
            inst.visit(intp);
            inst = next;
        }
    }

    public void LDC(int iid, int mid, int c) {
        execute(new LDC_int(iid, mid, c));
    }

    public void LDC(int iid, int mid, long c) {
        execute(new LDC_long(iid, mid, c));
    }

    public void LDC(int iid, int mid, float c) {
        execute(new LDC_float(iid, mid, c));
    }

    public void LDC(int iid, int mid, double c) {
        execute(new LDC_double(iid, mid, c));
    }

    public void LDC(int iid, int mid, String c) {
        execute(new LDC_String(iid, mid, c, System.identityHashCode(c)));
    }

    public void LDC(int iid, int mid, Object c) {
        execute(new LDC_Object(iid, mid, System.identityHashCode(c)));
    }

    public void IINC(int iid, int mid, int var, int increment) {
        execute(new IINC(iid, mid, var, increment));
    }

    public void MULTIANEWARRAY(int iid, int mid, String desc, int dims) {
        execute(new MULTIANEWARRAY(iid, mid, desc, dims));
    }

    public void LOOKUPSWITCH(int iid, int mid, int dflt, int[] keys, int[] labels) {
        execute(new LOOKUPSWITCH(iid, mid, dflt, keys, labels));
    }

    public void TABLESWITCH(int iid, int mid, int min, int max, int dflt, int[] labels) {
        execute(new TABLESWITCH(iid, mid, min, max, dflt, labels));
    }

    public void IFEQ(int iid, int mid, int label) {
        execute(new IFEQ(iid, mid, label));
    }

    public void IFNE(int iid, int mid, int label) {
        execute(new IFNE(iid, mid, label));
    }

    public void IFLT(int iid, int mid, int label) {
        execute(new IFLT(iid, mid, label));
    }

    public void IFGE(int iid, int mid, int label) {
        execute(new IFGE(iid, mid, label));
    }

    public void IFGT(int iid, int mid, int label) {
        execute(new IFGT(iid, mid, label));
    }

    public void IFLE(int iid, int mid, int label) {
        execute(new IFLE(iid, mid, label));
    }

    public void IF_ICMPEQ(int iid, int mid, int label) {
        execute(new IF_ICMPEQ(iid, mid, label));
    }

    public void IF_ICMPNE(int iid, int mid, int label) {
        execute(new IF_ICMPNE(iid, mid, label));
    }

    public void IF_ICMPLT(int iid, int mid, int label) {
        execute(new IF_ICMPLT(iid, mid, label));
    }

    public void IF_ICMPGE(int iid, int mid, int label) {
        execute(new IF_ICMPGE(iid, mid, label));
    }

    public void IF_ICMPGT(int iid, int mid, int label) {
        execute(new IF_ICMPGT(iid, mid, label));
    }

    public void IF_ICMPLE(int iid, int mid, int label) {
        execute(new IF_ICMPLE(iid, mid, label));
    }

    public void IF_ACMPEQ(int iid, int mid, int label) {
        execute(new IF_ACMPEQ(iid, mid, label));
    }

    public void IF_ACMPNE(int iid, int mid, int label) {
        execute(new IF_ACMPNE(iid, mid, label));
    }

    public void GOTO(int iid, int mid, int label) {
        execute(new GOTO(iid, mid, label));
    }

    public void JSR(int iid, int mid, int label) {
        execute(new JSR(iid, mid, label));
    }

    public void IFNULL(int iid, int mid, int label) {
        execute(new IFNULL(iid, mid, label));
    }

    public void IFNONNULL(int iid, int mid, int label) {
        execute(new IFNONNULL(iid, mid, label));
    }

    public void INVOKEVIRTUAL(int iid, int mid, String owner, String name, String desc) {
        execute(new INVOKEVIRTUAL(iid, mid, owner, name, desc));
    }

    public void INVOKESPECIAL(int iid, int mid, String owner, String name, String desc) {
        execute(new INVOKESPECIAL(iid, mid, owner, name, desc));
    }

    public void INVOKESTATIC(int iid, int mid, String owner, String name, String desc) {
        execute(new INVOKESTATIC(iid, mid, owner, name, desc));
    }

    public void INVOKEINTERFACE(int iid, int mid, String owner, String name, String desc) {
        execute(new INVOKEINTERFACE(iid, mid, owner, name, desc));
    }

    public void GETSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
execute(new GETSTATIC(iid, mid, cIdx, fIdx, desc));
    }

    public void PUTSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
execute(new PUTSTATIC(iid, mid, cIdx, fIdx, desc));
    }

    public void GETFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
execute(new GETFIELD(iid, mid, cIdx, fIdx, desc));
    }

    public void PUTFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
execute(new PUTFIELD(iid, mid, cIdx, fIdx, desc));
    }

    public void NEW(int iid, int mid, String type, int cIdx) {
execute(new NEW(iid, mid, type, cIdx));
    }

    public void ANEWARRAY(int iid, int mid, String type) {
        execute(new ANEWARRAY(iid, mid, type));
    }

    public void CHECKCAST(int iid, int mid, String type) {
        execute(new CHECKCAST(iid, mid, type));
    }

    public void INSTANCEOF(int iid, int mid, String type) {
        execute(new INSTANCEOF(iid, mid, type));
    }

    public void BIPUSH(int iid, int mid, int value) {
        execute(new BIPUSH(iid, mid, value));
    }

    public void SIPUSH(int iid, int mid, int value) {
        execute(new SIPUSH(iid, mid, value));
    }

    public void NEWARRAY(int iid, int mid) {
        execute(new NEWARRAY(iid, mid));
    }

    public void ILOAD(int iid, int mid, int var) {
        execute(new ILOAD(iid, mid, var));
    }

    public void LLOAD(int iid, int mid, int var) {
        execute(new LLOAD(iid, mid, var));
    }

    public void FLOAD(int iid, int mid, int var) {
        execute(new FLOAD(iid, mid, var));
    }

    public void DLOAD(int iid, int mid, int var) {
        execute(new DLOAD(iid, mid, var));
    }

    public void ALOAD(int iid, int mid, int var) {
        execute(new ALOAD(iid, mid, var));
    }

    public void ISTORE(int iid, int mid, int var) {
        execute(new ISTORE(iid, mid, var));
    }

    public void LSTORE(int iid, int mid, int var) {
        execute(new LSTORE(iid, mid, var));
    }

    public void FSTORE(int iid, int mid, int var) {
        execute(new FSTORE(iid, mid, var));
    }

    public void DSTORE(int iid, int mid, int var) {
        execute(new DSTORE(iid, mid, var));
    }

    public void ASTORE(int iid, int mid, int var) {
        execute(new ASTORE(iid, mid, var));
    }

    public void RET(int iid, int mid, int var) {
        execute(new RET(iid, mid, var));
    }

    public void NOP(int iid, int mid) {
        execute(new NOP(iid, mid));
    }

    public void ACONST_NULL(int iid, int mid) {
        execute(new ACONST_NULL(iid, mid));
    }

    public void ICONST_M1(int iid, int mid) {
        execute(new ICONST_M1(iid, mid));
    }

    public void ICONST_0(int iid, int mid) {
        execute(new ICONST_0(iid, mid));
    }

    public void ICONST_1(int iid, int mid) {
        execute(new ICONST_1(iid, mid));
    }

    public void ICONST_2(int iid, int mid) {
        execute(new ICONST_2(iid, mid));
    }

    public void ICONST_3(int iid, int mid) {
        execute(new ICONST_3(iid, mid));
    }

    public void ICONST_4(int iid, int mid) {
        execute(new ICONST_4(iid, mid));
    }

    public void ICONST_5(int iid, int mid) {
        execute(new ICONST_5(iid, mid));
    }

    public void LCONST_0(int iid, int mid) {
        execute(new LCONST_0(iid, mid));
    }

    public void LCONST_1(int iid, int mid) {
        execute(new LCONST_1(iid, mid));
    }

    public void FCONST_0(int iid, int mid) {
        execute(new FCONST_0(iid, mid));
    }

    public void FCONST_1(int iid, int mid) {
        execute(new FCONST_1(iid, mid));
    }

    public void FCONST_2(int iid, int mid) {
        execute(new FCONST_2(iid, mid));
    }

    public void DCONST_0(int iid, int mid) {
        execute(new DCONST_0(iid, mid));
    }

    public void DCONST_1(int iid, int mid) {
        execute(new DCONST_1(iid, mid));
    }

    public void IALOAD(int iid, int mid) {
        execute(new IALOAD(iid, mid));
    }

    public void LALOAD(int iid, int mid) {
        execute(new LALOAD(iid, mid));
    }

    public void FALOAD(int iid, int mid) {
        execute(new FALOAD(iid, mid));
    }

    public void DALOAD(int iid, int mid) {
        execute(new DALOAD(iid, mid));
    }

    public void AALOAD(int iid, int mid) {
        execute(new AALOAD(iid, mid));
    }

    public void BALOAD(int iid, int mid) {
        execute(new BALOAD(iid, mid));
    }

    public void CALOAD(int iid, int mid) {
        execute(new CALOAD(iid, mid));
    }

    public void SALOAD(int iid, int mid) {
        execute(new SALOAD(iid, mid));
    }

    public void IASTORE(int iid, int mid) {
        execute(new IASTORE(iid, mid));
    }

    public void LASTORE(int iid, int mid) {
        execute(new LASTORE(iid, mid));
    }

    public void FASTORE(int iid, int mid) {
        execute(new FASTORE(iid, mid));
    }

    public void DASTORE(int iid, int mid) {
        execute(new DASTORE(iid, mid));
    }

    public void AASTORE(int iid, int mid) {
        execute(new AASTORE(iid, mid));
    }

    public void BASTORE(int iid, int mid) {
        execute(new BASTORE(iid, mid));
    }

    public void CASTORE(int iid, int mid) {
        execute(new CASTORE(iid, mid));
    }

    public void SASTORE(int iid, int mid) {
        execute(new SASTORE(iid, mid));
    }

    public void POP(int iid, int mid) {
        execute(new POP(iid, mid));
    }

    public void POP2(int iid, int mid) {
        execute(new POP2(iid, mid));
    }

    public void DUP(int iid, int mid) {
        execute(new DUP(iid, mid));
    }

    public void DUP_X1(int iid, int mid) {
        execute(new DUP_X1(iid, mid));
    }

    public void DUP_X2(int iid, int mid) {
        execute(new DUP_X2(iid, mid));
    }

    public void DUP2(int iid, int mid) {
        execute(new DUP2(iid, mid));
    }

    public void DUP2_X1(int iid, int mid) {
        execute(new DUP2_X1(iid, mid));
    }

    public void DUP2_X2(int iid, int mid) {
        execute(new DUP2_X2(iid, mid));
    }

    public void SWAP(int iid, int mid) {
        execute(new SWAP(iid, mid));
    }

    public void IADD(int iid, int mid) {
        execute(new IADD(iid, mid));
    }

    public void LADD(int iid, int mid) {
        execute(new LADD(iid, mid));
    }

    public void FADD(int iid, int mid) {
        execute(new FADD(iid, mid));
    }

    public void DADD(int iid, int mid) {
        execute(new DADD(iid, mid));
    }

    public void ISUB(int iid, int mid) {
        execute(new ISUB(iid, mid));
    }

    public void LSUB(int iid, int mid) {
        execute(new LSUB(iid, mid));
    }

    public void FSUB(int iid, int mid) {
        execute(new FSUB(iid, mid));
    }

    public void DSUB(int iid, int mid) {
        execute(new DSUB(iid, mid));
    }

    public void IMUL(int iid, int mid) {
        execute(new IMUL(iid, mid));
    }

    public void LMUL(int iid, int mid) {
        execute(new LMUL(iid, mid));
    }

    public void FMUL(int iid, int mid) {
        execute(new FMUL(iid, mid));
    }

    public void DMUL(int iid, int mid) {
        execute(new DMUL(iid, mid));
    }

    public void IDIV(int iid, int mid) {
        execute(new IDIV(iid, mid));
    }

    public void LDIV(int iid, int mid) {
        execute(new LDIV(iid, mid));
    }

    public void FDIV(int iid, int mid) {
        execute(new FDIV(iid, mid));
    }

    public void DDIV(int iid, int mid) {
        execute(new DDIV(iid, mid));
    }

    public void IREM(int iid, int mid) {
        execute(new IREM(iid, mid));
    }

    public void LREM(int iid, int mid) {
        execute(new LREM(iid, mid));
    }

    public void FREM(int iid, int mid) {
        execute(new FREM(iid, mid));
    }

    public void DREM(int iid, int mid) {
        execute(new DREM(iid, mid));
    }

    public void INEG(int iid, int mid) {
        execute(new INEG(iid, mid));
    }

    public void LNEG(int iid, int mid) {
        execute(new LNEG(iid, mid));
    }

    public void FNEG(int iid, int mid) {
        execute(new FNEG(iid, mid));
    }

    public void DNEG(int iid, int mid) {
        execute(new DNEG(iid, mid));
    }

    public void ISHL(int iid, int mid) {
        execute(new ISHL(iid, mid));
    }

    public void LSHL(int iid, int mid) {
        execute(new LSHL(iid, mid));
    }

    public void ISHR(int iid, int mid) {
        execute(new ISHR(iid, mid));
    }

    public void LSHR(int iid, int mid) {
        execute(new LSHR(iid, mid));
    }

    public void IUSHR(int iid, int mid) {
        execute(new IUSHR(iid, mid));
    }

    public void LUSHR(int iid, int mid) {
        execute(new LUSHR(iid, mid));
    }

    public void IAND(int iid, int mid) {
        execute(new IAND(iid, mid));
    }

    public void LAND(int iid, int mid) {
        execute(new LAND(iid, mid));
    }

    public void IOR(int iid, int mid) {
        execute(new IOR(iid, mid));
    }

    public void LOR(int iid, int mid) {
        execute(new LOR(iid, mid));
    }

    public void IXOR(int iid, int mid) {
        execute(new IXOR(iid, mid));
    }

    public void LXOR(int iid, int mid) {
        execute(new LXOR(iid, mid));
    }

    public void I2L(int iid, int mid) {
        execute(new I2L(iid, mid));
    }

    public void I2F(int iid, int mid) {
        execute(new I2F(iid, mid));
    }

    public void I2D(int iid, int mid) {
        execute(new I2D(iid, mid));
    }

    public void L2I(int iid, int mid) {
        execute(new L2I(iid, mid));
    }

    public void L2F(int iid, int mid) {
        execute(new L2F(iid, mid));
    }

    public void L2D(int iid, int mid) {
        execute(new L2D(iid, mid));
    }

    public void F2I(int iid, int mid) {
        execute(new F2I(iid, mid));
    }

    public void F2L(int iid, int mid) {
        execute(new F2L(iid, mid));
    }

    public void F2D(int iid, int mid) {
        execute(new F2D(iid, mid));
    }

    public void D2I(int iid, int mid) {
        execute(new D2I(iid, mid));
    }

    public void D2L(int iid, int mid) {
        execute(new D2L(iid, mid));
    }

    public void D2F(int iid, int mid) {
        execute(new D2F(iid, mid));
    }

    public void I2B(int iid, int mid) {
        execute(new I2B(iid, mid));
    }

    public void I2C(int iid, int mid) {
        execute(new I2C(iid, mid));
    }

    public void I2S(int iid, int mid) {
        execute(new I2S(iid, mid));
    }

    public void LCMP(int iid, int mid) {
        execute(new LCMP(iid, mid));
    }

    public void FCMPL(int iid, int mid) {
        execute(new FCMPL(iid, mid));
    }

    public void FCMPG(int iid, int mid) {
        execute(new FCMPG(iid, mid));
    }

    public void DCMPL(int iid, int mid) {
        execute(new DCMPL(iid, mid));
    }

    public void DCMPG(int iid, int mid) {
        execute(new DCMPG(iid, mid));
    }

    public void IRETURN(int iid, int mid) {
        execute(new IRETURN(iid, mid));
    }

    public void LRETURN(int iid, int mid) {
        execute(new LRETURN(iid, mid));
    }

    public void FRETURN(int iid, int mid) {
        execute(new FRETURN(iid, mid));
    }

    public void DRETURN(int iid, int mid) {
        execute(new DRETURN(iid, mid));
    }

    public void ARETURN(int iid, int mid) {
        execute(new ARETURN(iid, mid));
    }

    public void RETURN(int iid, int mid) {
        execute(new RETURN(iid, mid));
    }

    public void ARRAYLENGTH(int iid, int mid) {
        execute(new ARRAYLENGTH(iid, mid));
    }

    public void ATHROW(int iid, int mid) {
        execute(new ATHROW(iid, mid));
    }

    public void MONITORENTER(int iid, int mid) {
        execute(new MONITORENTER(iid, mid));
    }

    public void MONITOREXIT(int iid, int mid) {
        execute(new MONITOREXIT(iid, mid));
    }

    public void GETVALUE_double (double v) {
        execute(new GETVALUE_double(v));
    }

    public void GETVALUE_long (long v) {
        execute(new GETVALUE_long(v));
    }

    public void GETVALUE_Object (Object v) {
        boolean isString = v instanceof String;
        execute(new GETVALUE_Object(System.identityHashCode(v), isString ? ((String) v) : null, isString));
    }

    public void GETVALUE_boolean (boolean v) {
        execute(new GETVALUE_boolean(v));
    }

    public void GETVALUE_byte (byte v) {
        execute(new GETVALUE_byte(v));
    }

    public void GETVALUE_char (char v) {
        execute(new GETVALUE_char(v));
    }

    public void GETVALUE_float (float v) {
        execute(new GETVALUE_float(v));
    }

    public void GETVALUE_int (int v) {
        execute(new GETVALUE_int(v));
    }

    public void GETVALUE_short (short v) {
        execute(new GETVALUE_short(v));
    }

    public void GETVALUE_void() {
        execute(new GETVALUE_void());
    }

    public void INVOKEMETHOD_EXCEPTION() {
        execute(new INVOKEMETHOD_EXCEPTION());
    }

    public void INVOKEMETHOD_END() {
        execute(new INVOKEMETHOD_END());
    }

    public void MAKE_SYMBOLIC() {
        execute(new MAKE_SYMBOLIC());
    }

    public void SPECIAL(int i) {
        execute(new SPECIAL(i));
    }


}
