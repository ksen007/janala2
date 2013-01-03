/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
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

package janala.interpreters;

import gnu.trove.map.hash.TIntObjectHashMap;
import janala.config.Config;
import janala.logger.ClassNames;
import janala.logger.FieldInfo;
import janala.logger.ObjectInfo;
import janala.logger.inst.*;
import janala.solvers.History;
import janala.utils.MyLogger;
import org.objectweb.asm.Type;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 12:12 PM
 */
public class ConcolicInterpreter implements IVisitor {
    private Stack<Frame> stack;
    private Frame currentFrame;
    private ClassNames cnames;
    private TIntObjectHashMap<Value> objects;
    private int symbol = 1;
    private History history;
    private Instruction next;
    private final static Logger logger = MyLogger.getLogger(ConcolicInterpreter.class.getName());

    public ConcolicInterpreter(ClassNames cnames) {
        stack = new Stack<Frame>();
        stack.add(currentFrame = new Frame(0));
        this.cnames = cnames;
        objects = new TIntObjectHashMap<Value>();
        history = History.readHistory(Config.instance.getSolver());
//        inputs = new ArrayList<Value>();
    }

    private void checkAndSetException() {
        if (!(next instanceof SPECIAL) || ((SPECIAL)next).i!=0) {
            currentFrame.clear();
            currentFrame.push(PlaceHolder.instance);
        }
    }

    private void checkAndSetBranch(IntValue cr) {
        cr.concrete = 0;
        if (next instanceof SPECIAL) {
            if (((SPECIAL)next).i==1) {
                cr.concrete = 1;
            }
        }
    }

    public void endExecution() {
        history.solveAndSave();
    }


    public void visitAALOAD(AALOAD inst) {
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitAASTORE(AASTORE inst) {
        try {
            Value value = currentFrame.pop();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitACONST_NULL(ACONST_NULL inst) {
        currentFrame.push(ObjectValue.NULL);
    }

    public void visitALOAD(ALOAD inst) {
        currentFrame.push(currentFrame.getLocal(inst.var));
    }

    public void visitANEWARRAY(ANEWARRAY inst) {
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue tmp = new ObjectValue(i1.concrete);
            currentFrame.push(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        checkAndSetException();
    }

    public void visitARETURN(ARETURN inst) {
        currentFrame.ret = currentFrame.pop();
    }

    public void visitARRAYLENGTH(ARRAYLENGTH inst) {
        try {
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(new IntValue(ref.concrete.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitASTORE(ASTORE inst) {
        currentFrame.setLocal(inst.var, currentFrame.pop());
    }

    public void visitATHROW(ATHROW inst) {
        Value top = currentFrame.peek();
        currentFrame.clear();
        currentFrame.push(top);
    }

    public void visitBALOAD(BALOAD inst) {
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitBASTORE(BASTORE inst) {
        try {
            Value value = currentFrame.pop();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitBIPUSH(BIPUSH inst) {
        currentFrame.push(new IntValue(inst.value));
    }

    public void visitCALOAD(CALOAD inst) {
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitCASTORE(CASTORE inst) {
        try {
            Value value = currentFrame.pop();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitCHECKCAST(CHECKCAST inst) {
        checkAndSetException();
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
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push2(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitDASTORE(DASTORE inst) {
        try {
            Value value = currentFrame.pop2();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitFASTORE(FASTORE inst) {
        try {
            Value value = currentFrame.pop();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        try {
            ObjectInfo oi = cnames.get(inst.cIdx);
            FieldInfo fi = oi.get(inst.fIdx,false);
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
                currentFrame.push2(ref.getField(fi.fieldId));
            } else {
                currentFrame.push(ref.getField(fi.fieldId));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitGETSTATIC(GETSTATIC inst) {
        try {
            ObjectInfo oi = cnames.get(inst.cIdx);
            FieldInfo fi = oi.get(inst.fIdx,true);
            if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
                currentFrame.push2(oi.getStaticField(fi.fieldId));
            } else {
                currentFrame.push(oi.getStaticField(fi.fieldId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitGETVALUE_Object(GETVALUE_Object inst) {
        Value peek = currentFrame.peek();
        Value tmp;
        if (peek == PlaceHolder.instance || (((ObjectValue)peek).address != -1 && ((ObjectValue)peek).address != inst.v) ) {
            //if (peek != PlaceHolder.instance)
            //    logger.log(Level.WARNING, "** Failed to match " + currentFrame.peek() + " and " + inst.v);
            logger.log(Level.FINE, "** Failed to match " + currentFrame.peek() + " and " + inst.v);
            currentFrame.pop();
            tmp = objects.get(inst.v);
            if (tmp!=null) {
                currentFrame.push(tmp);
            } else if (inst.v==0) {
                currentFrame.push(ObjectValue.NULL);
            } else {
                if (inst.isString) {
                    currentFrame.push(tmp = new StringValue(inst.string,inst.v));
                } else {
                    currentFrame.push(tmp = new ObjectValue(100,inst.v));
                }
                objects.put(inst.v,tmp);
            }
        } else if (((ObjectValue)peek).address == -1) {
            if (inst.v == 0) {
                logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
                currentFrame.pop();
                currentFrame.push(ObjectValue.NULL);
            } else {
                ((ObjectValue)peek).setAddress(inst.v);
                objects.put(inst.v,peek);
            }
        }
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitGETVALUE_boolean(GETVALUE_boolean inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != (inst.v?1:0)) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop();
            
            currentFrame.push(new IntValue(inst.v?1:0));
        }
    }

    public void visitGETVALUE_byte(GETVALUE_byte inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_char(GETVALUE_char inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_double(GETVALUE_double inst) {
        if (currentFrame.peek2()==PlaceHolder.instance || ((DoubleValue)currentFrame.peek2()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop2();
            currentFrame.push2(new DoubleValue(inst.v));
        }
    }

    public void visitGETVALUE_float(GETVALUE_float inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((FloatValue)currentFrame.peek()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop();
            currentFrame.push(new FloatValue(inst.v));
        }
    }

    public void visitGETVALUE_int(GETVALUE_int inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop();
            currentFrame.push(new IntValue(inst.v));
        }
    }

    public void visitGETVALUE_long(GETVALUE_long inst) {
        if (currentFrame.peek2()==PlaceHolder.instance || ((LongValue)currentFrame.peek2()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
            currentFrame.pop2();
            currentFrame.push2(new LongValue(inst.v));
        }
    }

    public void visitGETVALUE_short(GETVALUE_short inst) {
        if (currentFrame.peek()==PlaceHolder.instance || ((IntValue)currentFrame.peek()).concrete != inst.v) {
            logger.log(Level.FINE,"** Failed to match {0} and "+inst.v, currentFrame.peek());
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
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitIAND(IAND inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IAND(i2));
    }

    public void visitIASTORE(IASTORE inst) {
        try {
            Value value = currentFrame.pop();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        try {
            IntValue i2 = (IntValue)currentFrame.pop();
            IntValue i1 = (IntValue)currentFrame.pop();
            currentFrame.push(i1.IDIV(i2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitIFEQ(IFEQ inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IFEQ();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic, inst.iid);
    }

    public void visitIFGE(IFGE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IFGE();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIFGT(IFGT inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IFGT();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIFLE(IFLE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IFLE();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIFLT(IFLT inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IFLT();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIFNE(IFNE inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IFNE();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIFNONNULL(IFNONNULL inst) {
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        IntValue result = o1.IFNONNULL();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIFNULL(IFNULL inst) {
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        IntValue result = o1.IFNULL();
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ACMPEQ(IF_ACMPEQ inst) {
        ObjectValue o2 = (ObjectValue)currentFrame.pop();
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        IntValue result = o1.IF_ACMPEQ(o2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ACMPNE(IF_ACMPNE inst) {
        ObjectValue o2 = (ObjectValue)currentFrame.pop();
        ObjectValue o1 = (ObjectValue)currentFrame.pop();
        IntValue result = o1.IF_ACMPNE(o2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ICMPEQ(IF_ICMPEQ inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IF_ICMPEQ(i2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ICMPGE(IF_ICMPGE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IF_ICMPGE(i2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ICMPGT(IF_ICMPGT inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IF_ICMPGT(i2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ICMPLE(IF_ICMPLE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IF_ICMPLE(i2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ICMPLT(IF_ICMPLT inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IF_ICMPLT(i2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
    }

    public void visitIF_ICMPNE(IF_ICMPNE inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        IntValue result = i1.IF_ICMPNE(i2);
        checkAndSetBranch(result);
        history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
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
        try {
            currentFrame.pop();
            currentFrame.push(new IntValue(1)); // could be wrong boolean value
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    private void setArgumentsAndNewFrame(String desc, String owner, String name, boolean isInstance, Instruction inst) {
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
        ObjectValue instance = null;
        if (isInstance) {
            instance =  (ObjectValue)currentFrame.pop();
            tmp.addLocal(instance);
        }
        for (int i=0; i<len; i++) {
            if (types[i]==Type.DOUBLE_TYPE || types[i]==Type.LONG_TYPE) {
                tmp.addLocal2(tmpValues[i]);
            } else {
                tmp.addLocal(tmpValues[i]);
            }
        }
        currentFrame = tmp;

        if (next instanceof INVOKEMETHOD_END || next instanceof INVOKEMETHOD_EXCEPTION || next == null) {
            if (isInstance) {
                currentFrame.ret = instance.invokeMethod(name,tmpValues);
            } else {
//                checkAssumption(owner,name,tmpValues);
                currentFrame.ret = StaticInvocation.invokeMethod(inst.iid,owner,name,tmpValues, history);
            }
        }
    }

//    private void checkAssumption(String owner, String name, Value[] tmpValues) {
//        if (owner.equals("janala/Main") && name.equals("Assume") && tmpValues.length==1) {
//            if (((IntValue)tmpValues[0]).concrete!=0) {
//                history.setLastBranchDone();
//            }
//        }
//    }
//
    public void visitINVOKEINTERFACE(INVOKEINTERFACE inst) {
        setArgumentsAndNewFrame(inst.desc, inst.owner, inst.name, true, inst);
    }

    public void visitINVOKESPECIAL(INVOKESPECIAL inst) {
        setArgumentsAndNewFrame(inst.desc, inst.owner, inst.name, true, inst);
    }

    public void visitINVOKESTATIC(INVOKESTATIC inst) {
        setArgumentsAndNewFrame(inst.desc, inst.owner, inst.name, false, inst);
    }

    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL inst) {
        setArgumentsAndNewFrame(inst.desc, inst.owner, inst.name, true, inst);
    }

    public void visitIOR(IOR inst) {
        IntValue i2 = (IntValue)currentFrame.pop();
        IntValue i1 = (IntValue)currentFrame.pop();
        currentFrame.push(i1.IOR(i2));
    }

    public void visitIREM(IREM inst) {
        try {
            IntValue i2 = (IntValue)currentFrame.pop();
            IntValue i1 = (IntValue)currentFrame.pop();
            currentFrame.push(i1.IREM(i2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        try {
        IntValue i1 = (IntValue)currentFrame.pop();
        ObjectValue ref = (ObjectValue)currentFrame.pop();
        currentFrame.push2(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitLAND(LAND inst) {
        LongValue i2 = (LongValue)currentFrame.pop2();
        LongValue i1 = (LongValue)currentFrame.pop2();
        currentFrame.push2(i1.LAND(i2));
    }

    public void visitLASTORE(LASTORE inst) {
        try {
            Value value = currentFrame.pop2();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        currentFrame.push(new StringValue(inst.c, inst.address));
    }

    public void visitLDC_Object(LDC_Object inst) {
        Value tmp = objects.get(inst.c);
        if (tmp!=null) {
            currentFrame.push(tmp);
        } else if (inst.c==0) {
            currentFrame.push(ObjectValue.NULL);
        } else {
            currentFrame.push(tmp = new ObjectValue(100,inst.c));
            objects.put(inst.c,tmp);
        }
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
        try {
            LongValue i2 = (LongValue)currentFrame.pop2();
            LongValue i1 = (LongValue)currentFrame.pop2();
            currentFrame.push2(i1.LDIV(i2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        try {
            LongValue i2 = (LongValue)currentFrame.pop2();
            LongValue i1 = (LongValue)currentFrame.pop2();
            currentFrame.push2(i1.LREM(i2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        checkAndSetException();
    }

    public void visitMONITOREXIT(MONITOREXIT inst) {
        checkAndSetException();
    }

    public void visitNEW(NEW inst) {
        try {
            ObjectInfo oi = cnames.get(inst.cIdx);
//            currentFrame.push(new ObjectValue(oi.nFields));
            currentFrame.push(ObjectFactory.create(oi.nFields,oi.className));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        checkAndSetException();

    }

    public void visitNEWARRAY(NEWARRAY inst) {
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue tmp = new ObjectValue(i1.concrete);
            currentFrame.push(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
        try {
            ObjectInfo oi = cnames.get(inst.cIdx);
            FieldInfo fi = oi.get(inst.fIdx,false);
            Value value;
            if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
                value = currentFrame.pop2();
            } else {
                value = currentFrame.pop();
            }
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(fi.fieldId,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitPUTSTATIC(PUTSTATIC inst) {
        try {
            ObjectInfo oi = cnames.get(inst.cIdx);
            FieldInfo fi = oi.get(inst.fIdx,true);
            Value value;
            if (inst.desc.startsWith("D") || inst.desc.startsWith("J")) {
                value = currentFrame.pop2();
            } else {
                value = currentFrame.pop();
            }
            oi.setField(fi.fieldId,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitRET(RET inst) {
//        throw new RuntimeException("Unimplemented instruction "+inst);
    }

    public void visitRETURN(RETURN inst) {
    }

    public void visitSALOAD(SALOAD inst) {
        try {
            IntValue i1 = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            currentFrame.push(ref.getField(i1.concrete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
    }

    public void visitSASTORE(SASTORE inst) {
        try {
            Value value = currentFrame.pop();
            IntValue i = (IntValue)currentFrame.pop();
            ObjectValue ref = (ObjectValue)currentFrame.pop();
            ref.setField(i.concrete,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndSetException();
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
            history.addInput(currentFrame.peek2());
        } else {
            currentFrame.peek().MAKE_SYMBOLIC(symbol++);
            history.addInput(currentFrame.peek());
        }
    }

    public void visitSPECIAL(SPECIAL inst) {
        
    }

    public void setNext(Instruction next) {
        this.next = next;
    }

    private ObjectValue initMultiArray(int[] dims, int idx) {
        ObjectValue tmp = new ObjectValue(dims[idx]);
        if (idx<dims.length-1) {
            for (int i=0; i<dims[idx]; i++) {
                tmp.concrete[i] = initMultiArray(dims,idx+1);
            }
        }
        return tmp;
    }

    public void visitMULTIANEWARRAY(MULTIANEWARRAY inst) {
        int dims[] = new int[inst.dims];
        try {
            for (int i = 0; i < dims.length; i++) {
                IntValue i1 = (IntValue)currentFrame.pop();
                dims[dims.length-i-1] = i1.concrete;

            }
            ObjectValue tmp = initMultiArray(dims,0);
            currentFrame.push(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void visitLOOKUPSWITCH(LOOKUPSWITCH inst) {
        int[] keys = inst.keys;
        IntValue i1 = (IntValue)currentFrame.pop();
        for (int key : keys) {
            IntValue result = i1.IF_ICMPEQ(new IntValue(key));
            history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
            if (result.concrete==1) return;
        }
    }

    public void visitTABLESWITCH(TABLESWITCH inst) {
        IntValue i1 = (IntValue)currentFrame.pop();
        for (int i=inst.min; i<=inst.max; i++) {
            IntValue result = i1.IF_ICMPEQ(new IntValue(i));
            history.checkAndSetBranch(result.concrete==1, result.symbolic,inst.iid);
            if (result.concrete==1) return;
        }
    }


}
