/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import janala.logger.inst.*;
import org.objectweb.asm.Type;

import java.util.Stack;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 12:12 PM
 */
public class ConcreteInterpreter implements IVisitor {
    private Stack<Frame> stack;
    private Frame currentFrame;

    public ConcreteInterpreter() {
        stack = new Stack<Frame>();
        stack.add(currentFrame = new Frame());
    }

    public void visitAALOAD(AALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitAASTORE(AASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitACONST_NULL(ACONST_NULL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitALOAD(ALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitANEWARRAY(ANEWARRAY inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitARETURN(ARETURN inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitARRAYLENGTH(ARRAYLENGTH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitASTORE(ASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitATHROW(ATHROW inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitBALOAD(BALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitBASTORE(BASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitBIPUSH(BIPUSH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitCALOAD(CALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitCASTORE(CASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitCHECKCAST(CHECKCAST inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitD2F(D2F inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitD2I(D2I inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitD2L(D2L inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDADD(DADD inst) {
        currentFrame.push2((Double)currentFrame.pop2()+(Double)currentFrame.pop2());
    }

    public void visitDALOAD(DALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDASTORE(DASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDCMPG(DCMPG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDCMPL(DCMPL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDCONST_0(DCONST_0 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDCONST_1(DCONST_1 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDDIV(DDIV inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDLOAD(DLOAD inst) {
        currentFrame.push2(currentFrame.getLocal2(inst.var));
    }

    public void visitDMUL(DMUL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDNEG(DNEG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDREM(DREM inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDRETURN(DRETURN inst) {
        Object tmp = currentFrame.pop2();
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.push2(tmp);
    }

    public void visitDSTORE(DSTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDSUB(DSUB inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDUP(DUP inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDUP2(DUP2 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDUP2_X1(DUP2_X1 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDUP2_X2(DUP2_X2 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDUP_X1(DUP_X1 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDUP_X2(DUP_X2 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitF2D(F2D inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitF2I(F2I inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitF2L(F2L inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFADD(FADD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFALOAD(FALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFASTORE(FASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFCMPG(FCMPG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFCMPL(FCMPL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFCONST_0(FCONST_0 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFCONST_1(FCONST_1 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFCONST_2(FCONST_2 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFDIV(FDIV inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFLOAD(FLOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFMUL(FMUL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFNEG(FNEG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFREM(FREM inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFRETURN(FRETURN inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFSTORE(FSTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFSUB(FSUB inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETFIELD(GETFIELD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETSTATIC(GETSTATIC inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_Object(GETVALUE_Object inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_boolean(GETVALUE_boolean inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_byte(GETVALUE_byte inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_char(GETVALUE_char inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_double(GETVALUE_double inst) {
        if (!currentFrame.peek2().equals(inst.v)) {
            throw new RuntimeException("Failed to match "+currentFrame.peek2()+" and "+inst.v);
        }
    }

    public void visitGETVALUE_float(GETVALUE_float inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_int(GETVALUE_int inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_long(GETVALUE_long inst) {
        if (!currentFrame.peek2().equals(inst.v)) {
            throw new RuntimeException("Failed to match "+currentFrame.peek2()+" and "+inst.v);
        }
    }

    public void visitGETVALUE_short(GETVALUE_short inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_void(GETVALUE_void inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGOTO(GOTO inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitI2B(I2B inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitI2C(I2C inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitI2D(I2D inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitI2F(I2F inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitI2L(I2L inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitI2S(I2S inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIADD(IADD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIALOAD(IALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIAND(IAND inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIASTORE(IASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_0(ICONST_0 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_1(ICONST_1 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_2(ICONST_2 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_3(ICONST_3 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_4(ICONST_4 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_5(ICONST_5 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_M1(ICONST_M1 inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIDIV(IDIV inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFEQ(IFEQ inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFGE(IFGE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFGT(IFGT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFLE(IFLE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFLT(IFLT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFNE(IFNE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFNONNULL(IFNONNULL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIFNULL(IFNULL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ACMPEQ(IF_ACMPEQ inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ACMPNE(IF_ACMPNE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ICMPEQ(IF_ICMPEQ inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ICMPGE(IF_ICMPGE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ICMPGT(IF_ICMPGT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ICMPLE(IF_ICMPLE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ICMPLT(IF_ICMPLT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIF_ICMPNE(IF_ICMPNE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIINC(IINC inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitILOAD(ILOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIMUL(IMUL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitINEG(INEG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitINSTANCEOF(INSTANCEOF inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    private void setArgumentsAndNewFrame(String desc, boolean isInstance) {
        Type[] types = Type.getArgumentTypes(desc);
        Frame tmp;
        stack.push(tmp = new Frame());
        int len = types.length;
        Object[] tmpValues = new Object[len];
        for (int i = len-1; i>=0; i--) {
            if (types[i]==Type.DOUBLE_TYPE || types[i]==Type.LONG_TYPE) {
                tmpValues[i] = currentFrame.pop2();
            } else {
                tmpValues[i] = currentFrame.pop();
            }
        }
        if (isInstance) {
            tmp.addLocal(currentFrame.pop());
        }
        for (int i=0; i<len; i++) {
            if (types[i]==Type.DOUBLE_TYPE || types[i]==Type.LONG_TYPE) {
                tmp.addLocal2(tmpValues[i]);
            } else {
                tmp.addLocal(tmpValues[i]);
            }
        }
        currentFrame = tmp;

    }

    public void visitINVOKEINTERFACE(INVOKEINTERFACE inst) {
        setArgumentsAndNewFrame(inst.desc,true);
    }

    public void visitINVOKEMETHOD_EXCEPTION(INVOKEMETHOD_EXCEPTION inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitINVOKESPECIAL(INVOKESPECIAL inst) {
        setArgumentsAndNewFrame(inst.desc,true);
    }

    public void visitINVOKESTATIC(INVOKESTATIC inst) {
        setArgumentsAndNewFrame(inst.desc,false);
    }

    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL inst) {
        setArgumentsAndNewFrame(inst.desc,true);
    }

    public void visitIOR(IOR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIREM(IREM inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIRETURN(IRETURN inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitISHL(ISHL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitISHR(ISHR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitISTORE(ISTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitISUB(ISUB inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIUSHR(IUSHR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIXOR(IXOR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitJSR(JSR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitL2D(L2D inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitL2F(L2F inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitL2I(L2I inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLADD(LADD inst) {
        currentFrame.push2((Long)currentFrame.pop2()+(Long)currentFrame.pop2());
    }

    public void visitLALOAD(LALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLAND(LAND inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLASTORE(LASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLCMP(LCMP inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLCONST_0(LCONST_0 inst) {
        currentFrame.push2(new Long(0));
    }

    public void visitLCONST_1(LCONST_1 inst) {
        currentFrame.push2(new Long(1));
    }

    public void visitLDC_String(LDC_String inst) {
        currentFrame.push(inst.c);
    }

    public void visitLDC_double(LDC_double inst) {
        currentFrame.push2(inst.c);
    }

    public void visitLDC_float(LDC_float inst) {
        currentFrame.push(inst.c);
    }

    public void visitLDC_int(LDC_int inst) {
        currentFrame.push(inst.c);
    }

    public void visitLDC_long(LDC_long inst) {
        currentFrame.push2(inst.c);
    }

    public void visitLDIV(LDIV inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLLOAD(LLOAD inst) {
        currentFrame.push2(currentFrame.getLocal2(inst.var));
    }

    public void visitLMUL(LMUL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLNEG(LNEG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLOOKUPSWITCH(LOOKUPSWITCH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLOR(LOR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLREM(LREM inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLRETURN(LRETURN inst) {
        Object tmp = currentFrame.pop2();
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.push2(tmp);
    }

    public void visitLSHL(LSHL inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLSHR(LSHR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLSTORE(LSTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLSUB(LSUB inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLUSHR(LUSHR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLXOR(LXOR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitMONITORENTER(MONITORENTER inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitMONITOREXIT(MONITOREXIT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitMULTIANEWARRAY(MULTIANEWARRAY inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEW(NEW inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_BOOLEAN(NEWARRAY_BOOLEAN inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_BYTE(NEWARRAY_BYTE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_CHAR(NEWARRAY_CHAR inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_DOUBLE(NEWARRAY_DOUBLE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_FLOAT(NEWARRAY_FLOAT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_INT(NEWARRAY_INT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_LONG(NEWARRAY_LONG inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEWARRAY_SHORT(NEWARRAY_SHORT inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNOP(NOP inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitPOP(POP inst) {
        currentFrame.pop();
    }

    public void visitPOP2(POP2 inst) {
        currentFrame.pop2();
    }

    public void visitPUTFIELD(PUTFIELD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitPUTSTATIC(PUTSTATIC inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitRET(RET inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitRETURN(RETURN inst) {
        stack.pop();
        if (!stack.isEmpty())
            currentFrame = stack.peek();
        else
            currentFrame = null;
    }

    public void visitSALOAD(SALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitSASTORE(SASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitSIPUSH(SIPUSH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitSWAP(SWAP inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitTABLESWITCH(TABLESWITCH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }
}
