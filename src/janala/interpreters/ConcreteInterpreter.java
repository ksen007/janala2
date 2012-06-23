/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import gnu.trove.map.hash.TIntObjectHashMap;
import janala.logger.ClassNames;
import janala.logger.FieldInfo;
import janala.logger.ObjectInfo;
import janala.logger.inst.*;
import janala.solvers.ChocoSolver;
import janala.solvers.History;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 12:12 PM
 */
public class ConcreteInterpreter implements IVisitor {
    private Stack<Frame> stack;
    private Frame currentFrame;
    private ClassNames cnames;
    private TIntObjectHashMap<Value> objects;
    private int symbol = 1;
    private History history;
    private ArrayList<Value> inputs;

    public ConcreteInterpreter(ClassNames cnames) {
        stack = new Stack<Frame>();
        stack.add(currentFrame = new Frame(0));
        this.cnames = cnames;
        objects = new TIntObjectHashMap<Value>();
        history = History.readHistory(new ChocoSolver());
        inputs = new ArrayList<Value>();
    }

    public void endExecution() {
        history.solveAndSave(inputs);
    }

    public void visitAALOAD(AALOAD inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(ref.getField(i1.concrete,currentFrame));
    }

    public void visitAASTORE(AASTORE inst) {
        Value value = currentFrame.pop();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
    }

    public void visitACONST_NULL(ACONST_NULL inst) {
        currentFrame.push(new ObjectValue(0,0));
    }

    public void visitALOAD(ALOAD inst) {
        currentFrame.push(currentFrame.getLocal(inst.var));
    }

    public void visitANEWARRAY(ANEWARRAY inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue tmp = new ObjectValue(i1.concrete);
        currentFrame.push(tmp);
    }

    public void visitARETURN(ARETURN inst) {
        currentFrame.ret = currentFrame.pop();
    }

    public void visitARRAYLENGTH(ARRAYLENGTH inst) {
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(new IntValue(ref.concrete.length));
    }

    public void visitASTORE(ASTORE inst) {
        currentFrame.setLocal(inst.var, currentFrame.pop());
    }

    public void visitATHROW(ATHROW inst) {
        // keep the stack unchanged
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitBALOAD(BALOAD inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(ref.getField(i1.concrete,currentFrame));
    }

    public void visitBASTORE(BASTORE inst) {
        Value value = currentFrame.pop();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
    }

    public void visitBIPUSH(BIPUSH inst) {
        currentFrame.push(new IntValue(inst.value));
    }

    public void visitCALOAD(CALOAD inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(ref.getField(i1.concrete,currentFrame));
    }

    public void visitCASTORE(CASTORE inst) {
        Value value = currentFrame.pop();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
    }

    public void visitCHECKCAST(CHECKCAST inst) {
        // do nothing
        // stack remains unchanged
//        throw new RuntimeException("Unimplemented instruction "+inst);
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
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push2(ref.getField(i1.concrete,currentFrame));
    }

    public void visitDASTORE(DASTORE inst) {
        Value value = currentFrame.pop2();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
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
        currentFrame.ret = currentFrame.pop2();
    }

    public void visitDSTORE(DSTORE inst) {
        currentFrame.setLocal2(inst.var, currentFrame.pop2());
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
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(ref.getField(i1.concrete,currentFrame));
    }

    public void visitFASTORE(FASTORE inst) {
        Value value = currentFrame.pop();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
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
        currentFrame.ret = currentFrame.pop();
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
        ObjectInfo oi = cnames.get(inst.cIdx);
        FieldInfo fi = oi.get(inst.fIdx,false);
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
            currentFrame.push2(ref.getField(fi.fieldId,currentFrame));
        } else {
            currentFrame.push(ref.getField(fi.fieldId,currentFrame));
        }
    }

    public void visitGETSTATIC(GETSTATIC inst) {
        ObjectInfo oi = cnames.get(inst.cIdx);
        FieldInfo fi = oi.get(inst.fIdx,true);
        if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
            currentFrame.push2(oi.getStaticField(fi.fieldId));
        } else {
            currentFrame.push(oi.getStaticField(fi.fieldId));
        }
    }

    public void visitGETVALUE_Object(GETVALUE_Object inst) {
        Value peek = currentFrame.peek();
        if (peek == PlaceHolder.instance) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            currentFrame.push(new ObjectValue(100 /*ignored */,inst.v));
        } else if (((ObjectValue)peek).address == -1) {
            if (inst.v == 0) {
                System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
                currentFrame.pop();
                currentFrame.push(ObjectValue.NULL);
            } else {
                ((ObjectValue)peek).setAddress(inst.v);
                objects.put(inst.v,peek);
            }
        } else if (((ObjectValue)peek).address != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            Value tmp = objects.get(inst.v);
            if (tmp!=null) {
                currentFrame.push(tmp);
            } else if (inst.v==0) {
                currentFrame.push(ObjectValue.NULL);
            } else {
                currentFrame.push(tmp = new ObjectValue(100,inst.v));
                objects.put(inst.v,tmp);
            }
        }
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_boolean(GETVALUE_boolean inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != (inst.v?1:0)) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            
            currentFrame.push(new IntValue(inst.v?1:0));
        }
    }

    public void visitGETVALUE_byte(GETVALUE_byte inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_char(GETVALUE_char inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_double(GETVALUE_double inst) {
        if (currentFrame.peek2()==PlaceHolder.instance || ((DoubleValue)currentFrame.peek2()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek2()+" and "+inst.v);
            currentFrame.pop2();
            currentFrame.push2(new DoubleValue(inst.v));
        }
    }

    public void visitGETVALUE_float(GETVALUE_float inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((FloatValue)currentFrame.peek()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            currentFrame.push(new FloatValue(inst.v));
        }
    }

    public void visitGETVALUE_int(GETVALUE_int inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek()+" and "+inst.v);
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_long(GETVALUE_long inst) {
        if (currentFrame.peek2()==PlaceHolder.instance || ((LongValue)currentFrame.peek2()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek2()+" and "+inst.v);
            currentFrame.pop2();
            currentFrame.push2(new LongValue(inst.v));
        }
    }

    public void visitGETVALUE_short(GETVALUE_short inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            System.out.println("** Failed to match "+currentFrame.peek2()+" and "+inst.v);
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_void(GETVALUE_void inst) {
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
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(ref.getField(i1.concrete,currentFrame));
    }

    public void visitIAND(IAND inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IAND(i2));
    }

    public void visitIASTORE(IASTORE inst) {
        Value value = currentFrame.pop();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
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
        try {
            currentFrame.push(i1.IDIV(i2));
        } catch (Exception e) {
            System.err.println("User Exception in IDIV");
            e.printStackTrace();
            currentFrame.clear();
            currentFrame.push(PlaceHolder.instance);
        }
    }

    public void visitIFEQ(IFEQ inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IFEQ();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFGE(IFGE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IFGE();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFGT(IFGT inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IFGT();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFLE(IFLE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IFLE();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFLT(IFLT inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IFLT();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFNE(IFNE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IFNE();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFNONNULL(IFNONNULL inst) {
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        ConstraintAndResult result = o1.IFNONNULL();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIFNULL(IFNULL inst) {
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        ConstraintAndResult result = o1.IFNULL();
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ACMPEQ(IF_ACMPEQ inst) {
        ObjectValue o2 = (ObjectValue)currentFrame.pop();
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        ConstraintAndResult result = o1.IF_ACMPEQ(o2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ACMPNE(IF_ACMPNE inst) {
        ObjectValue o2 = (ObjectValue)currentFrame.pop();
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        ConstraintAndResult result = o1.IF_ACMPNE(o2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ICMPEQ(IF_ICMPEQ inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IF_ICMPEQ(i2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ICMPGE(IF_ICMPGE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IF_ICMPGE(i2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ICMPGT(IF_ICMPGT inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IF_ICMPGT(i2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ICMPLE(IF_ICMPLE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IF_ICMPLE(i2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ICMPLT(IF_ICMPLT inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IF_ICMPLT(i2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIF_ICMPNE(IF_ICMPNE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        ConstraintAndResult result = i1.IF_ICMPNE(i2);
        history.checkAndSetBranch(result);
//        System.out.println(result);
    }

    public void visitIINC(IINC inst) {
        IntValue i1 = (IntValue)currentFrame.getLocal(inst.var);
        currentFrame.setLocal(inst.var, i1.IINC(inst.increment));
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
        currentFrame.pop();
        currentFrame.push(new IntValue(1)); // could be wrong boolean value
    }

    private void setArgumentsAndNewFrame(String desc, boolean isInstance) {
        Type[] types = Type.getArgumentTypes(desc);
        Type retType = Type.getReturnType(desc);
        int nReturnWords;
        if (retType==Type.DOUBLE_TYPE || retType==Type.LONG_TYPE) {
            nReturnWords = 2;
        } else if (retType==Type.VOID_TYPE) {
            nReturnWords = 0;
        } else {
            nReturnWords = 1;
        }
        Frame tmp;
        stack.push(tmp = new Frame(nReturnWords));
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
        setArgumentsAndNewFrame(inst.desc, true);
    }

    public void visitINVOKESPECIAL(INVOKESPECIAL inst) {
        setArgumentsAndNewFrame(inst.desc, true);
    }

    public void visitINVOKESTATIC(INVOKESTATIC inst) {
        setArgumentsAndNewFrame(inst.desc, false);
    }

    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL inst) {
        setArgumentsAndNewFrame(inst.desc, true);
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
        currentFrame.ret = currentFrame.pop();
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
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push2(ref.getField(i1.concrete,currentFrame));
    }

    public void visitLAND(LAND inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LAND(i2));
    }

    public void visitLASTORE(LASTORE inst) {
        Value value = currentFrame.pop2();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
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
        try {
            currentFrame.push2(i1.LDIV(i2));
        } catch (Exception e) {
            System.err.println("User Exception in LDIV");
            e.printStackTrace();
            currentFrame.clear();
            currentFrame.push(PlaceHolder.instance);
        }
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
        currentFrame.ret = currentFrame.pop2();
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
        currentFrame.setLocal2(inst.var, currentFrame.pop2());
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
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitMONITOREXIT(MONITOREXIT inst) {
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitNEW(NEW inst) {
        ObjectInfo oi = cnames.get(inst.cIdx);
        currentFrame.push(new ObjectValue(oi.nFields));
    }

    public void visitNEWARRAY(NEWARRAY inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue tmp = new ObjectValue(i1.concrete);
        currentFrame.push(tmp);
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
        ObjectInfo oi = cnames.get(inst.cIdx);
        FieldInfo fi = oi.get(inst.fIdx,false);
        Value value;
        if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
            value = currentFrame.pop2();
        } else {
            value = currentFrame.pop();
        }
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(fi.fieldId,value,currentFrame);
    }

    public void visitPUTSTATIC(PUTSTATIC inst) {
        ObjectInfo oi = cnames.get(inst.cIdx);
        FieldInfo fi = oi.get(inst.fIdx,true);
        Value value;
        if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
            value = currentFrame.pop2();
        } else {
            value = currentFrame.pop();
        }
        oi.setField(fi.fieldId,value);
    }

    public void visitRET(RET inst) {
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitRETURN(RETURN inst) {
    }

    public void visitSALOAD(SALOAD inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push(ref.getField(i1.concrete,currentFrame));
    }

    public void visitSASTORE(SASTORE inst) {
        Value value = currentFrame.pop();
        IntValue i = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        ref.setField(i.concrete,value,currentFrame);
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

    public void visitINVOKEMETHOD_EXCEPTION(INVOKEMETHOD_EXCEPTION inst) {
        stack.pop();
        currentFrame = stack.peek();
        currentFrame.clear();
        currentFrame.push(PlaceHolder.instance);  // placeholder for the exception object
    }

    public void visitINVOKEMETHOD_END(INVOKEMETHOD_END inst) {
        Frame old = stack.pop();
        currentFrame = stack.peek();
        if (old.nReturnWords==2) {
            currentFrame.push2(old.ret);
        } else if (old.nReturnWords==1) {
            currentFrame.push(old.ret);
        }
    }

    public void visitMAKE_SYMBOLIC(MAKE_SYMBOLIC inst) {
        if (currentFrame.peek()==PlaceHolder.instance) {
            currentFrame.peek2().MAKE_SYMBOLIC(symbol++);
            inputs.add(currentFrame.peek2());
        } else {
            currentFrame.peek().MAKE_SYMBOLIC(symbol++);
            inputs.add(currentFrame.peek());
        }
    }

    public void visitMULTIANEWARRAY(MULTIANEWARRAY inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitLOOKUPSWITCH(LOOKUPSWITCH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitTABLESWITCH(TABLESWITCH inst) {
        throw new RuntimeException("Unimplemented instruction "+inst);
    }


}
