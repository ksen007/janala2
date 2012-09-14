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

package janala.instrument;

import janala.config.Config;
import janala.logger.ClassNames;
import janala.logger.ObjectInfo;
import org.objectweb.asm.*;

import java.util.LinkedList;

public class SnoopInstructionMethodAdapter extends MethodAdapter implements Opcodes{
    boolean isInit;
    boolean isSuperInitCalled;
    LinkedList<TryCatchBlock> tryCatchBlocks;
    private int line;
    boolean calledNew = false;

    public SnoopInstructionMethodAdapter(MethodVisitor mv, boolean isInit) {
        super(mv);
        this.isInit = isInit;
        this.isSuperInitCalled = false;
        tryCatchBlocks = new LinkedList<TryCatchBlock>();
    }

    @Override
    public void visitCode() {
        GlobalStateForInstrumentation.instance.incMid();
        //System.out.println("visitCode");
        mv.visitCode();
    }

    private void addBipushInsn(MethodVisitor mv, int val) {
        switch (val) {
            case 0:
                mv.visitInsn(ICONST_0);
                break;
            case 1:
                mv.visitInsn(ICONST_1);
                break;
            case 2:
                mv.visitInsn(ICONST_2);
                break;
            case 3:
                mv.visitInsn(ICONST_3);
                break;
            case 4:
                mv.visitInsn(ICONST_4);
                break;
            case 5:
                mv.visitInsn(ICONST_5);
                break;
            default:
                mv.visitLdcInsn(new Integer(val));
                break;
        }

    }

    private void addValueReadInsn(MethodVisitor mv, String desc, String methodNamePrefix) {
        Type t;

//        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
//        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        //System.out.println("***************** desc "+desc);
        if (desc.startsWith("(")) {
            t = Type.getReturnType(desc);
        } else {
            t = Type.getType(desc);
        }
//        System.out.println("************************************");
        //System.out.println(t + " "+t.getSort());
        switch(t.getSort()) {
            case Type.DOUBLE:
                //mv.visitInsn(DCONST_1);
                mv.visitInsn(DUP2);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"double", "(D)V");
                break;
            case Type.LONG:
                //mv.visitInsn(LCONST_1);
                mv.visitInsn(DUP2);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"long", "(J)V");
                break;
            case Type.ARRAY:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"Object", "(Ljava/lang/Object;)V");
                break;
            case Type.BOOLEAN:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"boolean", "(Z)V");
                break;
            case Type.BYTE:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"byte", "(B)V");
                break;
            case Type.CHAR:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"char", "(C)V");
                break;
            case Type.FLOAT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"float", "(F)V");
                break;
            case Type.INT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"int", "(I)V");
                break;
            case Type.OBJECT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"Object", "(Ljava/lang/Object;)V");
                break;
            case Type.SHORT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"short", "(S)V");
                break;
            case Type.VOID:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix+"void", "()V");
                break;
            default:
                System.err.println("Unknown field or method descriptor "+desc);
                System.exit(1);
        }
        
    }

    private void addSpecialInsn(MethodVisitor mv, int val) {
        addBipushInsn(mv, val);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SPECIAL", "(I)V");
    }

    @Override
    public void visitLineNumber(int i, Label label) {
        line = i;
        mv.visitLineNumber(i, label);
    }

    @Override
    public void visitInsn(int opcode) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        switch (opcode) {
            case NOP:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "NOP", "(II)V");
                break;
            case ACONST_NULL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ACONST_NULL",
                        "(II)V");
                break;
            case ICONST_M1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_M1",
                        "(II)V");
                break;
            case ICONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_0",
                        "(II)V");
                break;
            case ICONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_1",
                        "(II)V");
                break;
            case ICONST_2:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_2",
                        "(II)V");
                break;
            case ICONST_3:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_3",
                        "(II)V");
                break;
            case ICONST_4:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_4",
                        "(II)V");
                break;
            case ICONST_5:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_5",
                        "(II)V");
                break;
            case LCONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LCONST_0",
                        "(II)V");
                break;
            case LCONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LCONST_1",
                        "(II)V");
                break;
            case FCONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCONST_0",
                        "(II)V");
                break;
            case FCONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCONST_1",
                        "(II)V");
                break;
            case FCONST_2:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCONST_2",
                        "(II)V");
                break;
            case DCONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCONST_0",
                        "(II)V");
                break;
            case DCONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCONST_1",
                        "(II)V");
                break;
            case IALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"I","GETVALUE_");
                return;
            case LALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"J","GETVALUE_");
                return;
            case FALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"F","GETVALUE_");
                return;
            case DALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"D","GETVALUE_");
                return;
            case AALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "AALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"Ljava/lang/Object;","GETVALUE_");
                return;
            case BALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "BALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"B","GETVALUE_");
                return;
            case CALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "CALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"C","GETVALUE_");
                return;
            case SALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SALOAD",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"S","GETVALUE_");
                return;
            case IASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case LASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case FASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case DASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case AASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "AASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case BASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "BASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case CASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "CASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case SASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SASTORE",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case POP:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "POP", "(II)V");
                break;
            case POP2:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "POP2",
                        "(II)V");
                break;
            case DUP:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP", "(II)V");
                break;
            case DUP_X1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP_X1",
                        "(II)V");
                break;
            case DUP_X2:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP_X2",
                        "(II)V");
                break;
            case DUP2:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP2",
                        "(II)V");
                break;
            case DUP2_X1:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP2_X1",
                        "(II)V");
                break;
            case DUP2_X2:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP2_X2",
                        "(II)V");
                break;
            case SWAP:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SWAP",
                        "(II)V");
                break;
            case IADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IADD",
                        "(II)V");
                break;
            case LADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LADD",
                        "(II)V");
                break;
            case FADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FADD",
                        "(II)V");
                break;
            case DADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DADD",
                        "(II)V");
                break;
            case ISUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISUB",
                        "(II)V");
                break;
            case LSUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSUB",
                        "(II)V");
                break;
            case FSUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FSUB",
                        "(II)V");
                break;
            case DSUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DSUB",
                        "(II)V");
                break;
            case IMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IMUL",
                        "(II)V");
                break;
            case LMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LMUL",
                        "(II)V");
                break;
            case FMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FMUL",
                        "(II)V");
                break;
            case DMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DMUL",
                        "(II)V");
                break;
            case IDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IDIV",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case LDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDIV",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case FDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FDIV",
                        "(II)V");
                break;
            case DDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DDIV",
                        "(II)V");
                break;
            case IREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IREM",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case LREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LREM",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case FREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FREM",
                        "(II)V");
                break;
            case DREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DREM",
                        "(II)V");
                break;
            case INEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INEG",
                        "(II)V");
                break;
            case LNEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LNEG",
                        "(II)V");
                break;
            case FNEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FNEG",
                        "(II)V");
                break;
            case DNEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DNEG",
                        "(II)V");
                break;
            case ISHL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISHL",
                        "(II)V");
                break;
            case LSHL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSHL",
                        "(II)V");
                break;
            case ISHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISHR",
                        "(II)V");
                break;
            case LSHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSHR",
                        "(II)V");
                break;
            case IUSHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IUSHR",
                        "(II)V");
                break;
            case LUSHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LUSHR",
                        "(II)V");
                break;
            case IAND:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IAND",
                        "(II)V");
                break;
            case LAND:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LAND",
                        "(II)V");
                break;
            case IOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IOR", "(II)V");
                break;
            case LOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LOR", "(II)V");
                break;
            case IXOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IXOR",
                        "(II)V");
                break;
            case LXOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LXOR",
                        "(II)V");
                break;
            case I2L:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2L", "(II)V");
                break;
            case I2F:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2F", "(II)V");
                break;
            case I2D:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2D", "(II)V");
                break;
            case L2I:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "L2I", "(II)V");
                break;
            case L2F:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "L2F", "(II)V");
                break;
            case L2D:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "L2D", "(II)V");
                break;
            case F2I:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "F2I", "(II)V");
                break;
            case F2L:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "F2L", "(II)V");
                break;
            case F2D:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "F2D", "(II)V");
                break;
            case D2I:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "D2I", "(II)V");
                break;
            case D2L:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "D2L", "(II)V");
                break;
            case D2F:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "D2F", "(II)V");
                break;
            case I2B:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2B", "(II)V");
                break;
            case I2C:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2C", "(II)V");
                break;
            case I2S:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2S", "(II)V");
                break;
            case LCMP:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LCMP",
                        "(II)V");
                break;
            case FCMPL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCMPL",
                        "(II)V");
                break;
            case FCMPG:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCMPG",
                        "(II)V");
                break;
            case DCMPL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCMPL",
                        "(II)V");
                break;
            case DCMPG:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCMPG",
                        "(II)V");
                break;
            case IRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IRETURN",
                        "(II)V");
                break;
            case LRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LRETURN",
                        "(II)V");
                break;
            case FRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FRETURN",
                        "(II)V");
                break;
            case DRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DRETURN",
                        "(II)V");
                break;
            case ARETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ARETURN",
                        "(II)V");
                break;
            case RETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "RETURN",
                        "(II)V");
                break;
            case ARRAYLENGTH:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ARRAYLENGTH",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case ATHROW:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ATHROW",
                        "(II)V");
                break;
            case MONITORENTER:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "MONITORENTER",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            case MONITOREXIT:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "MONITOREXIT",
                        "(II)V");
                mv.visitInsn(opcode);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            default:
                System.err.println("Unknown instruction opcode "+opcode);
                System.exit(1);
        }
        mv.visitInsn(opcode);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, var);
        switch (opcode) {
            case ILOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ILOAD",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                addValueReadInsn(mv,"I","GETVALUE_");
                break;
            case LLOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LLOAD",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                addValueReadInsn(mv,"J","GETVALUE_");
                break;
            case FLOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FLOAD",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                addValueReadInsn(mv,"F","GETVALUE_");
                break;
            case DLOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DLOAD",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                addValueReadInsn(mv,"D","GETVALUE_");
                break;
            case ALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ALOAD",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                if (!(var==0 && isInit && !isSuperInitCalled))
                    addValueReadInsn(mv,"Ljava/lang/Object;","GETVALUE_");
                break;
            case ISTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISTORE",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                break;
            case LSTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSTORE",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                break;
            case FSTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FSTORE",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                break;
            case DSTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DSTORE",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                break;
            case ASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ASTORE",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                break;
            case RET:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "RET",
                        "(III)V");
                mv.visitVarInsn(opcode, var);
                break;
            default:
                System.err.println("Unknown var instruction opcode "+opcode);
                System.exit(1);
        }
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        switch (opcode) {
            case BIPUSH:
                addBipushInsn(mv, operand);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "BIPUSH", "(III)V");
                break;
            case SIPUSH:
                addBipushInsn(mv, operand);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SIPUSH", "(III)V");
                break;
            case NEWARRAY:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "NEWARRAY", "(II)V");
                mv.visitIntInsn(opcode, operand);
                addSpecialInsn(mv,0); // for non-exceptional path
                return;
            default:
                System.err.println("Unknown int instruction opcode "+opcode);
                System.exit(1);
        }
        mv.visitIntInsn(opcode, operand);
    }

//    @Override
//    public void visitTypeInsn(int opcode, String type) {
//        mv.visitTypeInsn(opcode, type);
//        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
//        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
//        mv.visitLdcInsn(type);
//        switch (opcode) {
//            case NEW:
//                int cIdx = ClassNames.instance.get(type);
//                addBipushInsn(mv,cIdx);
//
//                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "NEW", "(IILjava/lang/String;I)V");
//                break;
//            case ANEWARRAY:
//                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ANEWARRAY", "(IILjava/lang/String;)V");
//                break;
//            case CHECKCAST:
//                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "CHECKCAST", "(IILjava/lang/String;)V");
//                break;
//            case INSTANCEOF:
//                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INSTANCEOF", "(IILjava/lang/String;)V");
//                addValueReadInsn(mv,"I","GETVALUE_");
//                break;
//            default:
//                System.err.println("Unknown type instruction opcode "+opcode);
//                System.exit(1);
//        }
//    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(type);
        switch (opcode) {
            case NEW:
                int cIdx = ClassNames.instance.get(type);
                addBipushInsn(mv, cIdx);
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "NEW", "(IILjava/lang/String;I)V");
                mv.visitTypeInsn(opcode, type);
                calledNew = true;
                addSpecialInsn(mv, 0); // for non-exceptional path
                addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
                addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP", "(II)V");
                mv.visitInsn(DUP);
                break;
            case ANEWARRAY:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ANEWARRAY", "(IILjava/lang/String;)V");
                mv.visitTypeInsn(opcode, type);
                addSpecialInsn(mv,0); // for non-exceptional path
                break;
            case CHECKCAST:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "CHECKCAST", "(IILjava/lang/String;)V");
                mv.visitTypeInsn(opcode, type);
                addSpecialInsn(mv,0); // for non-exceptional path
                break;
            case INSTANCEOF:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INSTANCEOF", "(IILjava/lang/String;)V");
                mv.visitTypeInsn(opcode, type);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,"I","GETVALUE_");
                break;
            default:
                System.err.println("Unknown type instruction opcode "+opcode);
                System.exit(1);
        }
    }


    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        int cIdx = ClassNames.instance.get(owner);
        ObjectInfo tmp = ClassNames.instance.get(cIdx);
        addBipushInsn(mv,cIdx);
        switch (opcode) {
            case GETSTATIC:
                int fIdx = tmp.get(owner,name,true);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "GETSTATIC",
                        "(IIIILjava/lang/String;)V");

                //System.out.println("Idx "+ ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,desc,"GETVALUE_");
                break;
            case PUTSTATIC:
                fIdx = tmp.get(owner,name,true);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "PUTSTATIC",
                        "(IIIILjava/lang/String;)V");
                //System.out.println("Idx "+ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                addSpecialInsn(mv,0); // for non-exceptional path
                break;
            case GETFIELD:
                fIdx = tmp.get(owner,name,false);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "GETFIELD",
                        "(IIIILjava/lang/String;)V");
                //System.out.println("Idx "+ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                addSpecialInsn(mv,0); // for non-exceptional path
                addValueReadInsn(mv,desc,"GETVALUE_");
                break;
            case PUTFIELD:
                fIdx = tmp.get(owner,name,false);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "PUTFIELD",
                        "(IIIILjava/lang/String;)V");
                //System.out.println("Idx "+ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                addSpecialInsn(mv,0); // for non-exceptional path
                break;
            default:
                System.err.println("Unknown field access opcode "+opcode);
                System.exit(1);
        }
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        boolean isInit2 = false;
        if (owner.equals("janala/Main") && name.equals("MakeSymbolic")) {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "MAKE_SYMBOLIC", "()V");
        }
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(owner);
        mv.visitLdcInsn(name);
        mv.visitLdcInsn(desc);
        switch (opcode) {
            case INVOKEVIRTUAL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKEVIRTUAL",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            case INVOKESPECIAL:
                if (name.equals("<init>")) {
                    isSuperInitCalled = true;
                    isInit2 = true;
                }
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKESPECIAL",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            case INVOKESTATIC:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKESTATIC",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            case INVOKEINTERFACE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKEINTERFACE",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            default:
                System.err.println("Unknown method invocation opcode "+opcode);
                System.exit(1);
        }

        Label begin = new Label();
        Label handler = new Label();
        Label end = new Label();

        tryCatchBlocks.addFirst(new TryCatchBlock(begin,handler,handler,null));
        mv.visitLabel(begin);
        mv.visitMethodInsn(opcode, owner, name, desc);
        mv.visitJumpInsn(GOTO,end);
        mv.visitLabel(handler);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKEMETHOD_EXCEPTION", "()V");
        mv.visitInsn(ATHROW);
        mv.visitLabel(end);
        
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKEMETHOD_END", "()V");
        addValueReadInsn(mv,desc,"GETVALUE_");
        if (isInit2 && calledNew) {
            calledNew = false;
            addValueReadInsn(mv,"Ljava/lang/Object;","GETVALUE_");
            addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
            addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "POP", "(II)V");
            mv.visitInsn(POP);
        }

    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, System.identityHashCode(label)); // label.getOffset()
        switch (opcode) {
            case IFEQ:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFEQ", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IFNE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFNE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IFLT:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFLT", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IFGE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFGE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IFGT:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFGT", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IFLE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFLE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ICMPEQ:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPEQ", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ICMPNE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPNE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ICMPLT:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPLT", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ICMPGE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPGE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ICMPGT:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPGT", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ICMPLE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPLE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ACMPEQ:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ACMPEQ", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IF_ACMPNE:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ACMPNE", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case GOTO:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "GOTO", "(III)V");
                mv.visitJumpInsn(opcode, label);
                break;
            case JSR:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "JSR", "(III)V");
                mv.visitJumpInsn(opcode, label);
                break;
            case IFNULL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFNULL", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            case IFNONNULL:
                mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFNONNULL", "(III)V");
                mv.visitJumpInsn(opcode, label);
                addSpecialInsn(mv,1); // for true path
                break;
            default:
                System.err.println("Unknown jump opcode "+opcode);
                System.exit(1);
        }

    }

    @Override
    public void visitLdcInsn(Object cst) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(cst);
        if (cst instanceof Integer) {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(III)V");
        } else if (cst instanceof Long) {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IIJ)V");
        } else if (cst instanceof Float) {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IIF)V");
        } else if (cst instanceof Double) {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IID)V");
        } else if (cst instanceof String) {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IILjava/lang/String;)V");
        } else {
            mv.visitMethodInsn(INVOKESTATIC, Config.instance.instance.analysisClass, "LDC", "(IILjava/lang/Object;)V");
        }
        mv.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, var);
        addBipushInsn(mv, increment);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IINC", "(IIII)V");
        mv.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, min);
        addBipushInsn(mv, max);
        addBipushInsn(mv,System.identityHashCode(dflt)); // label.getOffset()

        addBipushInsn(mv,labels.length);
        mv.visitIntInsn(NEWARRAY, T_INT);
        for (int i=0; i<labels.length; i++) {
            mv.visitInsn(DUP);
            addBipushInsn(mv,i);
            addBipushInsn(mv,System.identityHashCode(labels[i])); // label.getOffset()
            mv.visitInsn(IASTORE);
        }

        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "TABLESWITCH", "(IIIII[I)V");
        mv.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv,System.identityHashCode(dflt));  // label.getOffset()

        addBipushInsn(mv,keys.length);
        mv.visitIntInsn(NEWARRAY, T_INT);
        for (int i=0; i<keys.length; i++) {
            mv.visitInsn(DUP);
            addBipushInsn(mv,i);
            addBipushInsn(mv,keys[i]);
            mv.visitInsn(IASTORE);
        }

        addBipushInsn(mv,labels.length);
        mv.visitIntInsn(NEWARRAY, T_INT);
        for (int i=0; i<labels.length; i++) {
            mv.visitInsn(DUP);
            addBipushInsn(mv,i);
            addBipushInsn(mv,System.identityHashCode(labels[i])); // label.getOffset()
            mv.visitInsn(IASTORE);
        }

        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LOOKUPSWITCH", "(III[I[I)V");
        mv.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid(line));
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(desc);
        addBipushInsn(mv, dims);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "MULTIANEWARRAY", "(IILjava/lang/String;I)V");
        mv.visitMultiANewArrayInsn(desc, dims);
        addSpecialInsn(mv,0); // for non-exceptional path
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        for(TryCatchBlock b:tryCatchBlocks) {
            b.visit(mv);
        }
        mv.visitMaxs(maxStack + 8, maxLocals);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
        tryCatchBlocks.addLast(new TryCatchBlock(label, label1, label2, s));
    }
}
