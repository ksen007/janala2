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
        currentFrame.push(new IntValue(inst.value));
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
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push(d1.D2F());
    }

    public void visitD2I(D2I inst) {
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push(d1.D2I());
    }

    public void visitD2L(D2L inst) {
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.D2L());
    }

    public void visitDADD(DADD inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.DADD(d2));
    }

    public void visitDALOAD(DALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDASTORE(DASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitDCMPG(DCMPG inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push(d1.DCMPG(d2));
    }

    public void visitDCMPL(DCMPL inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push(d1.DCMPL(d2));
    }

    public void visitDCONST_0(DCONST_0 inst) {
        currentFrame.push2(new DoubleValue(0.0));
    }

    public void visitDCONST_1(DCONST_1 inst) {
        currentFrame.push2(new DoubleValue(1.0));
    }

    public void visitDDIV(DDIV inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.DDIV(d2));
    }

    public void visitDLOAD(DLOAD inst) {
        currentFrame.push2(currentFrame.getLocal2(inst.var));
    }

    public void visitDMUL(DMUL inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.DMUL(d2));
    }

    public void visitDNEG(DNEG inst) {
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.DNEG());
    }

    public void visitDREM(DREM inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.DREM(d2));
    }

    public void visitDRETURN(DRETURN inst) {
        DoubleValue tmp = (DoubleValue)currentFrame.pop2();
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.push2(tmp);
    }

    public void visitDSTORE(DSTORE inst) {
        currentFrame.setLocal2(inst.var,currentFrame.pop2());
    }

    public void visitDSUB(DSUB inst) {
        DoubleValue d2 = (DoubleValue)currentFrame.pop2();
        DoubleValue d1 = (DoubleValue)currentFrame.pop2();
        currentFrame.push2(d1.DSUB(d2));
    }

    public void visitDUP(DUP inst) {
        currentFrame.push(currentFrame.peek());
    }

    public void visitDUP2(DUP2 inst) {
        currentFrame.push(currentFrame.peek2());
        currentFrame.push(currentFrame.peek2());
    }

    public void visitDUP2_X1(DUP2_X1 inst) {
        currentFrame.push(currentFrame.peek3());
        currentFrame.push(currentFrame.peek3());
    }

    public void visitDUP2_X2(DUP2_X2 inst) {
        currentFrame.push(currentFrame.peek4());
        currentFrame.push(currentFrame.peek4());
    }

    public void visitDUP_X1(DUP_X1 inst) {
        currentFrame.push(currentFrame.peek2());
    }

    public void visitDUP_X2(DUP_X2 inst) {
        currentFrame.push(currentFrame.peek3());
    }

    public void visitF2D(F2D inst) {
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push2(f1.F2D());
    }

    public void visitF2I(F2I inst) {
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.F2I());
    }

    public void visitF2L(F2L inst) {
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push2(f1.F2L());
    }

    public void visitFADD(FADD inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FADD(f2));
    }

    public void visitFALOAD(FALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFASTORE(FASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitFCMPG(FCMPG inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FCMPG(f2));
    }

    public void visitFCMPL(FCMPL inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FCMPL(f2));
    }

    public void visitFCONST_0(FCONST_0 inst) {
        currentFrame.push(new FloatValue(0.0f));
    }

    public void visitFCONST_1(FCONST_1 inst) {
        currentFrame.push(new FloatValue(1.0f));
    }

    public void visitFCONST_2(FCONST_2 inst) {
        currentFrame.push(new FloatValue(2.0f));
    }

    public void visitFDIV(FDIV inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FDIV(f2));
    }

    public void visitFLOAD(FLOAD inst) {
        currentFrame.push(currentFrame.getLocal(inst.var));
    }

    public void visitFMUL(FMUL inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FMUL(f2));
    }

    public void visitFNEG(FNEG inst) {
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FNEG());
    }

    public void visitFREM(FREM inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FREM(f2));
    }

    public void visitFRETURN(FRETURN inst) {
        FloatValue tmp = (FloatValue)currentFrame.pop();
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.push(tmp);
    }

    public void visitFSTORE(FSTORE inst) {
        currentFrame.setLocal(inst.var, currentFrame.pop());
    }

    public void visitFSUB(FSUB inst) {
        FloatValue f2 = (FloatValue)currentFrame.pop();
        FloatValue f1 = (FloatValue)currentFrame.pop();
        currentFrame.push(f1.FSUB(f2));
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
        if (((DoubleValue)currentFrame.peek2()).concrete != inst.v) {
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
        if (((LongValue)currentFrame.peek2()).concrete != inst.v) {
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
    }

    public void visitI2B(I2B inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.I2B());
    }

    public void visitI2C(I2C inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.I2C());
    }

    public void visitI2D(I2D inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push2(i1.I2D());
    }

    public void visitI2F(I2F inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.I2F());
    }

    public void visitI2L(I2L inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push2(i1.I2L());
    }

    public void visitI2S(I2S inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.I2S());
    }

    public void visitIADD(IADD inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IADD(i2));
    }

    public void visitIALOAD(IALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitIAND(IAND inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IAND(i2));
    }

    public void visitIASTORE(IASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitICONST_0(ICONST_0 inst) {
        currentFrame.push(new IntValue(0));
    }

    public void visitICONST_1(ICONST_1 inst) {
        currentFrame.push(new IntValue(1));
    }

    public void visitICONST_2(ICONST_2 inst) {
        currentFrame.push(new IntValue(2));
    }

    public void visitICONST_3(ICONST_3 inst) {
        currentFrame.push(new IntValue(3));
    }

    public void visitICONST_4(ICONST_4 inst) {
        currentFrame.push(new IntValue(4));
    }

    public void visitICONST_5(ICONST_5 inst) {
        currentFrame.push(new IntValue(5));
    }

    public void visitICONST_M1(ICONST_M1 inst) {
        currentFrame.push(new IntValue(-1));
    }

    public void visitIDIV(IDIV inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IDIV(i2));
    }

    public void visitIFEQ(IFEQ inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IFEQ();
        System.out.println(result);
    }

    public void visitIFGE(IFGE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IFGE();
        System.out.println(result);
    }

    public void visitIFGT(IFGT inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IFGT();
        System.out.println(result);
    }

    public void visitIFLE(IFLE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IFLE();
        System.out.println(result);
    }

    public void visitIFLT(IFLT inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IFLT();
        System.out.println(result);
    }

    public void visitIFNE(IFNE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IFNE();
        System.out.println(result);
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
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IF_ICMPEQ(i2);
        System.out.println(result);
    }

    public void visitIF_ICMPGE(IF_ICMPGE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IF_ICMPGE(i2);
        System.out.println(result);
    }

    public void visitIF_ICMPGT(IF_ICMPGT inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IF_ICMPGT(i2);
        System.out.println(result);
    }

    public void visitIF_ICMPLE(IF_ICMPLE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IF_ICMPLE(i2);
        System.out.println(result);
    }

    public void visitIF_ICMPLT(IF_ICMPLT inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IF_ICMPLT(i2);
        System.out.println(result);
    }

    public void visitIF_ICMPNE(IF_ICMPNE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        boolean result = i1.IF_ICMPNE(i2);
        System.out.println(result);
    }

    public void visitIINC(IINC inst) {
        IntValue i1 = (IntValue)currentFrame.getLocal(inst.var);
        currentFrame.setLocal(inst.var,i1.IINC(inst.increment));
    }

    public void visitILOAD(ILOAD inst) {
        currentFrame.push(currentFrame.getLocal(inst.var));
    }

    public void visitIMUL(IMUL inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IMUL(i2));
    }

    public void visitINEG(INEG inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.INEG());
    }

    public void visitINSTANCEOF(INSTANCEOF inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    private void setArgumentsAndNewFrame(String desc, boolean isInstance) {
        Type[] types = Type.getArgumentTypes(desc);
        Frame tmp;
        stack.push(tmp = new Frame());
        int len = types.length;
        Value[] tmpValues = new Value[len];
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
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IOR(i2));
    }

    public void visitIREM(IREM inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IREM(i2));
    }

    public void visitIRETURN(IRETURN inst) {
        IntValue tmp = (IntValue)currentFrame.pop();
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.push(tmp);
    }

    public void visitISHL(ISHL inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.ISHL(i2));
    }

    public void visitISHR(ISHR inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.ISHR(i2));
    }

    public void visitISTORE(ISTORE inst) {
        currentFrame.setLocal(inst.var, currentFrame.pop());
    }

    public void visitISUB(ISUB inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.ISUB(i2));
    }

    public void visitIUSHR(IUSHR inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IUSHR(i2));
    }

    public void visitIXOR(IXOR inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IXOR(i2));
    }

    public void visitJSR(JSR inst) {
    }

    public void visitL2D(L2D inst) {
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.L2D());
    }

    public void visitL2F(L2F inst) {
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push(i1.L2F());
    }

    public void visitL2I(L2I inst) {
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push(i1.L2I());
    }

    public void visitLADD(LADD inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LADD(i2));
    }

    public void visitLALOAD(LALOAD inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLAND(LAND inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LAND(i2));
    }

    public void visitLASTORE(LASTORE inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLCMP(LCMP inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push(i1.LCMP(i2));
    }

    public void visitLCONST_0(LCONST_0 inst) {
        currentFrame.push2(new LongValue(0));
    }

    public void visitLCONST_1(LCONST_1 inst) {
        currentFrame.push2(new LongValue(1));
    }

    public void visitLDC_String(LDC_String inst) {
        currentFrame.push(new StringValue(inst.c));
    }

    public void visitLDC_double(LDC_double inst) {
        currentFrame.push2(new DoubleValue(inst.c));
    }

    public void visitLDC_float(LDC_float inst) {
        currentFrame.push(new FloatValue(inst.c));
    }

    public void visitLDC_int(LDC_int inst) {
        currentFrame.push(new IntValue(inst.c));
    }

    public void visitLDC_long(LDC_long inst) {
        currentFrame.push2(new LongValue(inst.c));
    }

    public void visitLDIV(LDIV inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LDIV(i2));
    }

    public void visitLLOAD(LLOAD inst) {
        currentFrame.push2(currentFrame.getLocal2(inst.var));
    }

    public void visitLMUL(LMUL inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LMUL(i2));
    }

    public void visitLNEG(LNEG inst) {
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LNEG());
    }

    public void visitLOOKUPSWITCH(LOOKUPSWITCH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLOR(LOR inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LOR(i2));
    }

    public void visitLREM(LREM inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LREM(i2));
    }

    public void visitLRETURN(LRETURN inst) {
        LongValue tmp = (LongValue)currentFrame.pop2();
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.push2(tmp);
    }

    public void visitLSHL(LSHL inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LSHL(i2));
    }

    public void visitLSHR(LSHR inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LSHR(i2));
    }

    public void visitLSTORE(LSTORE inst) {
        currentFrame.setLocal2(inst.var,currentFrame.pop2());
    }

    public void visitLSUB(LSUB inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LSUB(i2));
    }

    public void visitLUSHR(LUSHR inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LUSHR(i2));
    }

    public void visitLXOR(LXOR inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LXOR(i2));
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
        currentFrame.push(new IntValue(inst.value));
    }

    public void visitSWAP(SWAP inst) {
        Value v1 = currentFrame.pop();
        Value v2 = currentFrame.pop();
        currentFrame.push(v1);
        currentFrame.push(v2);
    }

    public void visitTABLESWITCH(TABLESWITCH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }
}
