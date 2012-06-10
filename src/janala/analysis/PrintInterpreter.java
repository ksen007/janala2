package janala.analysis;

/**
 * Copyright (c) 2006-2010,
 * Koushik Sen    <ksen@cs.berkeley.edu>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p/>
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p/>
 * 3. The names of the contributors may not be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class PrintInterpreter implements Interpreter {

    public void LDC(int iid, int mid, int c) {
        System.out.println("LDC iid="+iid+" mid="+mid+" c="+c);
    }

    public void LDC(int iid, int mid, long c) {
        System.out.println("LDC iid="+iid+" mid="+mid+" c="+c);
    }

    public void LDC(int iid, int mid, float c) {
        System.out.println("LDC iid="+iid+" mid="+mid+" c="+c);
    }

    public void LDC(int iid, int mid, double c) {
        System.out.println("LDC iid="+iid+" mid="+mid+" c="+c);
    }

    public void LDC(int iid, int mid, String c) {
        System.out.println("LDC iid="+iid+" mid="+mid+" c="+c);
    }

    public void IINC(int iid, int mid, int var, int increment) {
        System.out.println("IINC iid="+iid+" mid="+mid+" var="+var+" increment="+increment);
    }

    public void MULTIANEWARRAY(int iid, int mid, String desc, int dims) {
        System.out.println("MULTIANEWARRAY iid="+iid+" mid="+mid+" desc="+desc+" dims="+dims);
    }

    public void LOOKUPSWITCH(int iid, int mid, int dflt, int[] keys, int[] labels) {
        System.out.println("LOOKUPSWITCH iid="+iid+" mid="+mid+" dflt="+dflt+" keys="+keys+" labels="+labels);
    }

    public void TABLESWITCH(int iid, int mid, int min, int max, int dflt, int[] labels) {
        System.out.println("TABLESWITCH iid="+iid+" mid="+mid+" min="+min+" max="+max+" dflt="+dflt+" labels="+labels);
    }

    public void IFEQ(int iid, int mid, int label) {
        System.out.println("IFEQ iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFNE(int iid, int mid, int label) {
        System.out.println("IFNE iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFLT(int iid, int mid, int label) {
        System.out.println("IFLT iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFGE(int iid, int mid, int label) {
        System.out.println("IFGE iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFGT(int iid, int mid, int label) {
        System.out.println("IFGT iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFLE(int iid, int mid, int label) {
        System.out.println("IFLE iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ICMPEQ(int iid, int mid, int label) {
        System.out.println("IF_ICMPEQ iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ICMPNE(int iid, int mid, int label) {
        System.out.println("IF_ICMPNE iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ICMPLT(int iid, int mid, int label) {
        System.out.println("IF_ICMPLT iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ICMPGE(int iid, int mid, int label) {
        System.out.println("IF_ICMPGE iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ICMPGT(int iid, int mid, int label) {
        System.out.println("IF_ICMPGT iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ICMPLE(int iid, int mid, int label) {
        System.out.println("IF_ICMPLE iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ACMPEQ(int iid, int mid, int label) {
        System.out.println("IF_ACMPEQ iid="+iid+" mid="+mid+" label="+label);
    }

    public void IF_ACMPNE(int iid, int mid, int label) {
        System.out.println("IF_ACMPNE iid="+iid+" mid="+mid+" label="+label);
    }

    public void GOTO(int iid, int mid, int label) {
        System.out.println("GOTO iid="+iid+" mid="+mid+" label="+label);
    }

    public void JSR(int iid, int mid, int label) {
        System.out.println("JSR iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFNULL(int iid, int mid, int label) {
        System.out.println("IFNULL iid="+iid+" mid="+mid+" label="+label);
    }

    public void IFNONNULL(int iid, int mid, int label) {
        System.out.println("IFNONNULL iid="+iid+" mid="+mid+" label="+label);
    }

    public void INVOKEVIRTUAL(int iid, int mid, String owner, String name, String desc) {
        System.out.println("INVOKEVIRTUAL iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc);
    }

    public void INVOKESPECIAL(int iid, int mid, String owner, String name, String desc) {
        System.out.println("INVOKESPECIAL iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc);
    }

    public void INVOKESTATIC(int iid, int mid, String owner, String name, String desc) {
        System.out.println("INVOKESTATIC iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc);
    }

    public void INVOKEINTERFACE(int iid, int mid, String owner, String name, String desc) {
        System.out.println("INVOKEINTERFACE iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc);
    }

    public void GETSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
//        ObjectInfo oi = ClassNames.instance.get(cIdx);
//        FieldInfo fi = oi.get(fIdx);
//        fi = fi.init();

        System.out.println("GETSTATIC iid="+iid+" mid="+mid+" cIdx="+cIdx+" fIdx="+fIdx+" desc="+desc);
    }

    public void PUTSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
//        ObjectInfo oi = ClassNames.instance.get(cIdx);
//        FieldInfo fi = oi.get(fIdx);
//        fi = fi.init();

        System.out.println("PUTSTATIC iid="+iid+" mid="+mid+" cIdx="+cIdx+" fIdx="+fIdx+" desc="+desc);
    }

    public void GETFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
//        ObjectInfo oi = ClassNames.instance.get(cIdx);
//        FieldInfo fi = oi.get(fIdx);
//        fi = fi.init();

        System.out.println("GETFIELD iid="+iid+" mid="+mid+" cIdx="+cIdx+" fIdx="+fIdx+" desc="+desc);
    }

    public void PUTFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
//        ObjectInfo oi = ClassNames.instance.get(cIdx);
//        FieldInfo fi = oi.get(fIdx);
//        fi = fi.init();

        System.out.println("PUTFIELD iid="+iid+" mid="+mid+" cIdx="+cIdx+" fIdx="+fIdx+" desc="+desc);
    }

    public void NEW(int iid, int mid, String type, int cIdx) {
//        ObjectInfo oi = ClassNames.instance.get(cIdx);
//        oi = oi.init();

        System.out.println("NEW iid="+iid+" mid="+mid+" cIdx="+cIdx);
    }

    public void ANEWARRAY(int iid, int mid, String type) {
        System.out.println("ANEWARRAY iid="+iid+" mid="+mid+" type="+type);
    }

    public void CHECKCAST(int iid, int mid, String type) {
        System.out.println("CHECKCAST iid="+iid+" mid="+mid+" type="+type);
    }

    public void INSTANCEOF(int iid, int mid, String type) {
        System.out.println("INSTANCEOF iid="+iid+" mid="+mid+" type="+type);
    }

    public void BIPUSH(int iid, int mid, int value) {
        System.out.println("BIPUSH iid="+iid+" mid="+mid+" value="+value);
    }

    public void SIPUSH(int iid, int mid, int value) {
        System.out.println("SIPUSH iid="+iid+" mid="+mid+" value="+value);
    }

    public void NEWARRAY_INT(int iid, int mid) {
        System.out.println("NEWARRAY_INT iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_BYTE(int iid, int mid) {
        System.out.println("NEWARRAY_BYTE iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_CHAR(int iid, int mid) {
        System.out.println("NEWARRAY_CHAR iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_LONG(int iid, int mid) {
        System.out.println("NEWARRAY_LONG iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_BOOLEAN(int iid, int mid) {
        System.out.println("NEWARRAY_BOOLEAN iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_DOUBLE(int iid, int mid) {
        System.out.println("NEWARRAY_DOUBLE iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_FLOAT(int iid, int mid) {
        System.out.println("NEWARRAY_FLOAT iid="+iid+" mid="+mid);
    }

    public void NEWARRAY_SHORT(int iid, int mid) {
        System.out.println("NEWARRAY_SHORT iid="+iid+" mid="+mid);
    }

    public void ILOAD(int iid, int mid, int var) {
        System.out.println("ILOAD iid="+iid+" mid="+mid+" var="+var);
    }

    public void LLOAD(int iid, int mid, int var) {
        System.out.println("LLOAD iid="+iid+" mid="+mid+" var="+var);
    }

    public void FLOAD(int iid, int mid, int var) {
        System.out.println("FLOAD iid="+iid+" mid="+mid+" var="+var);
    }

    public void DLOAD(int iid, int mid, int var) {
        System.out.println("DLOAD iid="+iid+" mid="+mid+" var="+var);
    }

    public void ALOAD(int iid, int mid, int var) {
        System.out.println("ALOAD iid="+iid+" mid="+mid+" var="+var);
    }

    public void ISTORE(int iid, int mid, int var) {
        System.out.println("ISTORE iid="+iid+" mid="+mid+" var="+var);
    }

    public void LSTORE(int iid, int mid, int var) {
        System.out.println("LSTORE iid="+iid+" mid="+mid+" var="+var);
    }

    public void FSTORE(int iid, int mid, int var) {
        System.out.println("FSTORE iid="+iid+" mid="+mid+" var="+var);
    }

    public void DSTORE(int iid, int mid, int var) {
        System.out.println("DSTORE iid="+iid+" mid="+mid+" var="+var);
    }

    public void ASTORE(int iid, int mid, int var) {
        System.out.println("ASTORE iid="+iid+" mid="+mid+" var="+var);
    }

    public void RET(int iid, int mid, int var) {
        System.out.println("RET iid="+iid+" mid="+mid+" var="+var);
    }

    public void NOP(int iid, int mid) {
        System.out.println("NOP iid="+iid+" mid="+mid);
    }

    public void ACONST_NULL(int iid, int mid) {
        System.out.println("ACONST_NULL iid="+iid+" mid="+mid);
    }

    public void ICONST_M1(int iid, int mid) {
        System.out.println("ICONST_M1 iid="+iid+" mid="+mid);
    }

    public void ICONST_0(int iid, int mid) {
        System.out.println("ICONST_0 iid="+iid+" mid="+mid);
    }

    public void ICONST_1(int iid, int mid) {
        System.out.println("ICONST_1 iid="+iid+" mid="+mid);
    }

    public void ICONST_2(int iid, int mid) {
        System.out.println("ICONST_2 iid="+iid+" mid="+mid);
    }

    public void ICONST_3(int iid, int mid) {
        System.out.println("ICONST_3 iid="+iid+" mid="+mid);
    }

    public void ICONST_4(int iid, int mid) {
        System.out.println("ICONST_4 iid="+iid+" mid="+mid);
    }

    public void ICONST_5(int iid, int mid) {
        System.out.println("ICONST_5 iid="+iid+" mid="+mid);
    }

    public void LCONST_0(int iid, int mid) {
        System.out.println("LCONST_0 iid="+iid+" mid="+mid);
    }

    public void LCONST_1(int iid, int mid) {
        System.out.println("LCONST_1 iid="+iid+" mid="+mid);
    }

    public void FCONST_0(int iid, int mid) {
        System.out.println("FCONST_0 iid="+iid+" mid="+mid);
    }

    public void FCONST_1(int iid, int mid) {
        System.out.println("FCONST_1 iid="+iid+" mid="+mid);
    }

    public void FCONST_2(int iid, int mid) {
        System.out.println("FCONST_2 iid="+iid+" mid="+mid);
    }

    public void DCONST_0(int iid, int mid) {
        System.out.println("DCONST_0 iid="+iid+" mid="+mid);
    }

    public void DCONST_1(int iid, int mid) {
        System.out.println("DCONST_1 iid="+iid+" mid="+mid);
    }

    public void IALOAD(int iid, int mid) {
        System.out.println("IALOAD iid="+iid+" mid="+mid);
    }

    public void LALOAD(int iid, int mid) {
        System.out.println("LALOAD iid="+iid+" mid="+mid);
    }

    public void FALOAD(int iid, int mid) {
        System.out.println("FALOAD iid="+iid+" mid="+mid);
    }

    public void DALOAD(int iid, int mid) {
        System.out.println("DALOAD iid="+iid+" mid="+mid);
    }

    public void AALOAD(int iid, int mid) {
        System.out.println("AALOAD iid="+iid+" mid="+mid);
    }

    public void BALOAD(int iid, int mid) {
        System.out.println("BALOAD iid="+iid+" mid="+mid);
    }

    public void CALOAD(int iid, int mid) {
        System.out.println("CALOAD iid="+iid+" mid="+mid);
    }

    public void SALOAD(int iid, int mid) {
        System.out.println("SALOAD iid="+iid+" mid="+mid);
    }

    public void IASTORE(int iid, int mid) {
        System.out.println("IASTORE iid="+iid+" mid="+mid);
    }

    public void LASTORE(int iid, int mid) {
        System.out.println("LASTORE iid="+iid+" mid="+mid);
    }

    public void FASTORE(int iid, int mid) {
        System.out.println("FASTORE iid="+iid+" mid="+mid);
    }

    public void DASTORE(int iid, int mid) {
        System.out.println("DASTORE iid="+iid+" mid="+mid);
    }

    public void AASTORE(int iid, int mid) {
        System.out.println("AASTORE iid="+iid+" mid="+mid);
    }

    public void BASTORE(int iid, int mid) {
        System.out.println("BASTORE iid="+iid+" mid="+mid);
    }

    public void CASTORE(int iid, int mid) {
        System.out.println("CASTORE iid="+iid+" mid="+mid);
    }

    public void SASTORE(int iid, int mid) {
        System.out.println("SASTORE iid="+iid+" mid="+mid);
    }

    public void POP(int iid, int mid) {
        System.out.println("POP iid="+iid+" mid="+mid);
    }

    public void POP2(int iid, int mid) {
        System.out.println("POP2 iid="+iid+" mid="+mid);
    }

    public void DUP(int iid, int mid) {
        System.out.println("DUP iid="+iid+" mid="+mid);
    }

    public void DUP_X1(int iid, int mid) {
        System.out.println("DUP_X1 iid="+iid+" mid="+mid);
    }

    public void DUP_X2(int iid, int mid) {
        System.out.println("DUP_X2 iid="+iid+" mid="+mid);
    }

    public void DUP2(int iid, int mid) {
        System.out.println("DUP2 iid="+iid+" mid="+mid);
    }

    public void DUP2_X1(int iid, int mid) {
        System.out.println("DUP2_X1 iid="+iid+" mid="+mid);
    }

    public void DUP2_X2(int iid, int mid) {
        System.out.println("DUP2_X2 iid="+iid+" mid="+mid);
    }

    public void SWAP(int iid, int mid) {
        System.out.println("SWAP iid="+iid+" mid="+mid);
    }

    public void IADD(int iid, int mid) {
        System.out.println("IADD iid="+iid+" mid="+mid);
    }

    public void LADD(int iid, int mid) {
        System.out.println("LADD iid="+iid+" mid="+mid);
    }

    public void FADD(int iid, int mid) {
        System.out.println("FADD iid="+iid+" mid="+mid);
    }

    public void DADD(int iid, int mid) {
        System.out.println("DADD iid="+iid+" mid="+mid);
    }

    public void ISUB(int iid, int mid) {
        System.out.println("ISUB iid="+iid+" mid="+mid);
    }

    public void LSUB(int iid, int mid) {
        System.out.println("LSUB iid="+iid+" mid="+mid);
    }

    public void FSUB(int iid, int mid) {
        System.out.println("FSUB iid="+iid+" mid="+mid);
    }

    public void DSUB(int iid, int mid) {
        System.out.println("DSUB iid="+iid+" mid="+mid);
    }

    public void IMUL(int iid, int mid) {
        System.out.println("IMUL iid="+iid+" mid="+mid);
    }

    public void LMUL(int iid, int mid) {
        System.out.println("LMUL iid="+iid+" mid="+mid);
    }

    public void FMUL(int iid, int mid) {
        System.out.println("FMUL iid="+iid+" mid="+mid);
    }

    public void DMUL(int iid, int mid) {
        System.out.println("DMUL iid="+iid+" mid="+mid);
    }

    public void IDIV(int iid, int mid) {
        System.out.println("IDIV iid="+iid+" mid="+mid);
    }

    public void LDIV(int iid, int mid) {
        System.out.println("LDIV iid="+iid+" mid="+mid);
    }

    public void FDIV(int iid, int mid) {
        System.out.println("FDIV iid="+iid+" mid="+mid);
    }

    public void DDIV(int iid, int mid) {
        System.out.println("DDIV iid="+iid+" mid="+mid);
    }

    public void IREM(int iid, int mid) {
        System.out.println("IREM iid="+iid+" mid="+mid);
    }

    public void LREM(int iid, int mid) {
        System.out.println("LREM iid="+iid+" mid="+mid);
    }

    public void FREM(int iid, int mid) {
        System.out.println("FREM iid="+iid+" mid="+mid);
    }

    public void DREM(int iid, int mid) {
        System.out.println("DREM iid="+iid+" mid="+mid);
    }

    public void INEG(int iid, int mid) {
        System.out.println("INEG iid="+iid+" mid="+mid);
    }

    public void LNEG(int iid, int mid) {
        System.out.println("LNEG iid="+iid+" mid="+mid);
    }

    public void FNEG(int iid, int mid) {
        System.out.println("FNEG iid="+iid+" mid="+mid);
    }

    public void DNEG(int iid, int mid) {
        System.out.println("DNEG iid="+iid+" mid="+mid);
    }

    public void ISHL(int iid, int mid) {
        System.out.println("ISHL iid="+iid+" mid="+mid);
    }

    public void LSHL(int iid, int mid) {
        System.out.println("LSHL iid="+iid+" mid="+mid);
    }

    public void ISHR(int iid, int mid) {
        System.out.println("ISHR iid="+iid+" mid="+mid);
    }

    public void LSHR(int iid, int mid) {
        System.out.println("LSHR iid="+iid+" mid="+mid);
    }

    public void IUSHR(int iid, int mid) {
        System.out.println("IUSHR iid="+iid+" mid="+mid);
    }

    public void LUSHR(int iid, int mid) {
        System.out.println("LUSHR iid="+iid+" mid="+mid);
    }

    public void IAND(int iid, int mid) {
        System.out.println("IAND iid="+iid+" mid="+mid);
    }

    public void LAND(int iid, int mid) {
        System.out.println("LAND iid="+iid+" mid="+mid);
    }

    public void IOR(int iid, int mid) {
        System.out.println("IOR iid="+iid+" mid="+mid);
    }

    public void LOR(int iid, int mid) {
        System.out.println("LOR iid="+iid+" mid="+mid);
    }

    public void IXOR(int iid, int mid) {
        System.out.println("IXOR iid="+iid+" mid="+mid);
    }

    public void LXOR(int iid, int mid) {
        System.out.println("LXOR iid="+iid+" mid="+mid);
    }

    public void I2L(int iid, int mid) {
        System.out.println("I2L iid="+iid+" mid="+mid);
    }

    public void I2F(int iid, int mid) {
        System.out.println("I2F iid="+iid+" mid="+mid);
    }

    public void I2D(int iid, int mid) {
        System.out.println("I2D iid="+iid+" mid="+mid);
    }

    public void L2I(int iid, int mid) {
        System.out.println("L2I iid="+iid+" mid="+mid);
    }

    public void L2F(int iid, int mid) {
        System.out.println("L2F iid="+iid+" mid="+mid);
    }

    public void L2D(int iid, int mid) {
        System.out.println("L2D iid="+iid+" mid="+mid);
    }

    public void F2I(int iid, int mid) {
        System.out.println("F2I iid="+iid+" mid="+mid);
    }

    public void F2L(int iid, int mid) {
        System.out.println("F2L iid="+iid+" mid="+mid);
    }

    public void F2D(int iid, int mid) {
        System.out.println("F2D iid="+iid+" mid="+mid);
    }

    public void D2I(int iid, int mid) {
        System.out.println("D2I iid="+iid+" mid="+mid);
    }

    public void D2L(int iid, int mid) {
        System.out.println("D2L iid="+iid+" mid="+mid);
    }

    public void D2F(int iid, int mid) {
        System.out.println("D2F iid="+iid+" mid="+mid);
    }

    public void I2B(int iid, int mid) {
        System.out.println("I2B iid="+iid+" mid="+mid);
    }

    public void I2C(int iid, int mid) {
        System.out.println("I2C iid="+iid+" mid="+mid);
    }

    public void I2S(int iid, int mid) {
        System.out.println("I2S iid="+iid+" mid="+mid);
    }

    public void LCMP(int iid, int mid) {
        System.out.println("LCMP iid="+iid+" mid="+mid);
    }

    public void FCMPL(int iid, int mid) {
        System.out.println("FCMPL iid="+iid+" mid="+mid);
    }

    public void FCMPG(int iid, int mid) {
        System.out.println("FCMPG iid="+iid+" mid="+mid);
    }

    public void DCMPL(int iid, int mid) {
        System.out.println("DCMPL iid="+iid+" mid="+mid);
    }

    public void DCMPG(int iid, int mid) {
        System.out.println("DCMPG iid="+iid+" mid="+mid);
    }

    public void IRETURN(int iid, int mid) {
        System.out.println("IRETURN iid="+iid+" mid="+mid);
    }

    public void LRETURN(int iid, int mid) {
        System.out.println("LRETURN iid="+iid+" mid="+mid);
    }

    public void FRETURN(int iid, int mid) {
        System.out.println("FRETURN iid="+iid+" mid="+mid);
    }

    public void DRETURN(int iid, int mid) {
        System.out.println("DRETURN iid="+iid+" mid="+mid);
    }

    public void ARETURN(int iid, int mid) {
        System.out.println("ARETURN iid="+iid+" mid="+mid);
    }

    public void RETURN(int iid, int mid) {
        System.out.println("RETURN iid="+iid+" mid="+mid);
    }

    public void ARRAYLENGTH(int iid, int mid) {
        System.out.println("ARRAYLENGTH iid="+iid+" mid="+mid);
    }

    public void ATHROW(int iid, int mid) {
        System.out.println("ATHROW iid="+iid+" mid="+mid);
    }

    public void MONITORENTER(int iid, int mid) {
        System.out.println("MONITORENTER iid="+iid+" mid="+mid);
    }

    public void MONITOREXIT(int iid, int mid) {
        System.out.println("MONITOREXIT iid="+iid+" mid="+mid);
    }

    public void GETFIELDORSTATIC_VALUE(double v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(long v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(Object v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(boolean v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(byte v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(char v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(float v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(int v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void GETFIELDORSTATIC_VALUE(short v) {
        System.out.println("GETFIELDORSTATIC_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(double v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(long v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(Object v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(boolean v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(byte v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(char v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(float v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(int v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE(short v) {
        System.out.println("INVOKEMETHOD_VALUE v="+v);
    }

    public void INVOKEMETHOD_VALUE() {
        System.out.println("INVOKEMETHOD_VALUE");
    }

    public void INVOKEMETHOD_EXCEPTION() {
        System.out.println("INVOKEMETHOD_EXCEPTION");
    }
}
