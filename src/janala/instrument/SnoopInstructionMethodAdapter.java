package janala.instrument;

import janala.analysis.ClassDepot;
import janala.analysis.ClassNames;
import janala.analysis.ObjectInfo;
import janala.config.Config;
import org.objectweb.asm.*;

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
public class SnoopInstructionMethodAdapter extends MethodAdapter implements Opcodes{

    public SnoopInstructionMethodAdapter(MethodVisitor mv) {
        super(mv);
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

//        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
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
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(D)V");
                break;
            case Type.LONG:
                //mv.visitInsn(LCONST_1);
                mv.visitInsn(DUP2);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(J)V");
                break;
            case Type.ARRAY:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(Ljava/lang/Object;)V");
                break;
            case Type.BOOLEAN:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(Z)V");
                break;
            case Type.BYTE:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(B)V");
                break;
            case Type.CHAR:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(C)V");
                break;
            case Type.FLOAT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(F)V");
                break;
            case Type.INT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(I)V");
                break;
            case Type.OBJECT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(Ljava/lang/Object;)V");
                break;
            case Type.SHORT:
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "(S)V");
                break;
            case Type.VOID:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, methodNamePrefix+"VALUE", "()V");
                break;
            default:
                System.err.println("Unknown field or method descriptor "+desc);
                System.exit(1);
        }
        
    }

    @Override
    public void visitInsn(int opcode) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        switch (opcode) {
            case NOP:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NOP", "(II)V");
                break;
            case ACONST_NULL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ACONST_NULL",
                        "(II)V");
                break;
            case ICONST_M1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_M1",
                        "(II)V");
                break;
            case ICONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_0",
                        "(II)V");
                break;
            case ICONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_1",
                        "(II)V");
                break;
            case ICONST_2:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_2",
                        "(II)V");
                break;
            case ICONST_3:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_3",
                        "(II)V");
                break;
            case ICONST_4:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_4",
                        "(II)V");
                break;
            case ICONST_5:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ICONST_5",
                        "(II)V");
                break;
            case LCONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LCONST_0",
                        "(II)V");
                break;
            case LCONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LCONST_1",
                        "(II)V");
                break;
            case FCONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FCONST_0",
                        "(II)V");
                break;
            case FCONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FCONST_1",
                        "(II)V");
                break;
            case FCONST_2:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FCONST_2",
                        "(II)V");
                break;
            case DCONST_0:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DCONST_0",
                        "(II)V");
                break;
            case DCONST_1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DCONST_1",
                        "(II)V");
                break;
            case IALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IALOAD",
                        "(II)V");
                break;
            case LALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LALOAD",
                        "(II)V");
                break;
            case FALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FALOAD",
                        "(II)V");
                break;
            case DALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DALOAD",
                        "(II)V");
                break;
            case AALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "AALOAD",
                        "(II)V");
                break;
            case BALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "BALOAD",
                        "(II)V");
                break;
            case CALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "CALOAD",
                        "(II)V");
                break;
            case SALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "SALOAD",
                        "(II)V");
                break;
            case IASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IASTORE",
                        "(II)V");
                break;
            case LASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LASTORE",
                        "(II)V");
                break;
            case FASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FASTORE",
                        "(II)V");
                break;
            case DASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DASTORE",
                        "(II)V");
                break;
            case AASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "AASTORE",
                        "(II)V");
                break;
            case BASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "BASTORE",
                        "(II)V");
                break;
            case CASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "CASTORE",
                        "(II)V");
                break;
            case SASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "SASTORE",
                        "(II)V");
                break;
            case POP:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "POP", "(II)V");
                break;
            case POP2:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "POP2",
                        "(II)V");
                break;
            case DUP:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DUP", "(II)V");
                break;
            case DUP_X1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DUP_X1",
                        "(II)V");
                break;
            case DUP_X2:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DUP_X2",
                        "(II)V");
                break;
            case DUP2:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DUP2",
                        "(II)V");
                break;
            case DUP2_X1:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DUP2_X1",
                        "(II)V");
                break;
            case DUP2_X2:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DUP2_X2",
                        "(II)V");
                break;
            case SWAP:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "SWAP",
                        "(II)V");
                break;
            case IADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IADD",
                        "(II)V");
                break;
            case LADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LADD",
                        "(II)V");
                break;
            case FADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FADD",
                        "(II)V");
                break;
            case DADD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DADD",
                        "(II)V");
                break;
            case ISUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ISUB",
                        "(II)V");
                break;
            case LSUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LSUB",
                        "(II)V");
                break;
            case FSUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FSUB",
                        "(II)V");
                break;
            case DSUB:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DSUB",
                        "(II)V");
                break;
            case IMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IMUL",
                        "(II)V");
                break;
            case LMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LMUL",
                        "(II)V");
                break;
            case FMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FMUL",
                        "(II)V");
                break;
            case DMUL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DMUL",
                        "(II)V");
                break;
            case IDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IDIV",
                        "(II)V");
                break;
            case LDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LDIV",
                        "(II)V");
                break;
            case FDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FDIV",
                        "(II)V");
                break;
            case DDIV:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DDIV",
                        "(II)V");
                break;
            case IREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IREM",
                        "(II)V");
                break;
            case LREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LREM",
                        "(II)V");
                break;
            case FREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FREM",
                        "(II)V");
                break;
            case DREM:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DREM",
                        "(II)V");
                break;
            case INEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INEG",
                        "(II)V");
                break;
            case LNEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LNEG",
                        "(II)V");
                break;
            case FNEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FNEG",
                        "(II)V");
                break;
            case DNEG:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DNEG",
                        "(II)V");
                break;
            case ISHL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ISHL",
                        "(II)V");
                break;
            case LSHL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LSHL",
                        "(II)V");
                break;
            case ISHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ISHR",
                        "(II)V");
                break;
            case LSHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LSHR",
                        "(II)V");
                break;
            case IUSHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IUSHR",
                        "(II)V");
                break;
            case LUSHR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LUSHR",
                        "(II)V");
                break;
            case IAND:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IAND",
                        "(II)V");
                break;
            case LAND:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LAND",
                        "(II)V");
                break;
            case IOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IOR", "(II)V");
                break;
            case LOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LOR", "(II)V");
                break;
            case IXOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IXOR",
                        "(II)V");
                break;
            case LXOR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LXOR",
                        "(II)V");
                break;
            case I2L:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "I2L", "(II)V");
                break;
            case I2F:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "I2F", "(II)V");
                break;
            case I2D:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "I2D", "(II)V");
                break;
            case L2I:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "L2I", "(II)V");
                break;
            case L2F:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "L2F", "(II)V");
                break;
            case L2D:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "L2D", "(II)V");
                break;
            case F2I:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "F2I", "(II)V");
                break;
            case F2L:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "F2L", "(II)V");
                break;
            case F2D:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "F2D", "(II)V");
                break;
            case D2I:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "D2I", "(II)V");
                break;
            case D2L:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "D2L", "(II)V");
                break;
            case D2F:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "D2F", "(II)V");
                break;
            case I2B:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "I2B", "(II)V");
                break;
            case I2C:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "I2C", "(II)V");
                break;
            case I2S:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "I2S", "(II)V");
                break;
            case LCMP:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LCMP",
                        "(II)V");
                break;
            case FCMPL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FCMPL",
                        "(II)V");
                break;
            case FCMPG:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FCMPG",
                        "(II)V");
                break;
            case DCMPL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DCMPL",
                        "(II)V");
                break;
            case DCMPG:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DCMPG",
                        "(II)V");
                break;
            case IRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IRETURN",
                        "(II)V");
                break;
            case LRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LRETURN",
                        "(II)V");
                break;
            case FRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FRETURN",
                        "(II)V");
                break;
            case DRETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DRETURN",
                        "(II)V");
                break;
            case ARETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ARETURN",
                        "(II)V");
                break;
            case RETURN:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "RETURN",
                        "(II)V");
                break;
            case ARRAYLENGTH:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ARRAYLENGTH",
                        "(II)V");
                break;
            case ATHROW:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ATHROW",
                        "(II)V");
                break;
            case MONITORENTER:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "MONITORENTER",
                        "(II)V");
                break;
            case MONITOREXIT:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "MONITOREXIT",
                        "(II)V");
                break;
            default:
                System.err.println("Unknown instruction opcode "+opcode);
                System.exit(1);
        }
        mv.visitInsn(opcode);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, var);
        switch (opcode) {
            case ILOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ILOAD",
                        "(III)V");
                break;
            case LLOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LLOAD",
                        "(III)V");
                break;
            case FLOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FLOAD",
                        "(III)V");
                break;
            case DLOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DLOAD",
                        "(III)V");
                break;
            case ALOAD:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ALOAD",
                        "(III)V");
                break;
            case ISTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ISTORE",
                        "(III)V");
                break;
            case LSTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LSTORE",
                        "(III)V");
                break;
            case FSTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "FSTORE",
                        "(III)V");
                break;
            case DSTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "DSTORE",
                        "(III)V");
                break;
            case ASTORE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ASTORE",
                        "(III)V");
                break;
            case RET:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "RET",
                        "(III)V");
                break;
            default:
                System.err.println("Unknown var instruction opcode "+opcode);
                System.exit(1);
        }
        mv.visitVarInsn(opcode, var);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        switch (opcode) {
            case BIPUSH:
                addBipushInsn(mv, operand);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "BIPUSH", "(III)V");
                break;
            case SIPUSH:
                addBipushInsn(mv, operand);
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "SIPUSH", "(III)V");
                break;
            case NEWARRAY:
                switch (operand) {
                    case T_INT:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_INT", "(II)V");
                        break;
                    case T_BYTE:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_BYTE", "(II)V");
                        break;
                    case T_CHAR:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_CHAR", "(II)V");
                        break;
                    case T_LONG:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_LONG", "(II)V");
                        break;
                    case T_BOOLEAN:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_BOOLEAN", "(II)V");
                        break;
                    case T_DOUBLE:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_DOUBLE", "(II)V");
                        break;
                    case T_FLOAT:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_FLOAT", "(II)V");
                        break;
                    case T_SHORT:
                        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEWARRAY_SHORT", "(II)V");
                        break;
                    default:
                        System.err.println("Unknown new array primitive type "+operand);
                        System.exit(1);

                }
                break;
            default:
                System.err.println("Unknown int instruction opcode "+opcode);
                System.exit(1);
        }
        mv.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        mv.visitTypeInsn(opcode, type);
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(type);
        switch (opcode) {
            case NEW:
                int cIdx = ClassNames.instance.get(type);
                addBipushInsn(mv,cIdx);

                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "NEW", "(IILjava/lang/String;I)V");
                break;
            case ANEWARRAY:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "ANEWARRAY", "(IILjava/lang/String;)V");
                break;
            case CHECKCAST:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "CHECKCAST", "(IILjava/lang/String;)V");
                break;
            case INSTANCEOF:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INSTANCEOF", "(IILjava/lang/String;)V");
                break;
            default:
                System.err.println("Unknown type instruction opcode "+opcode);
                System.exit(1);
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        int cIdx = ClassNames.instance.get(owner);
        ObjectInfo tmp = ClassNames.instance.get(cIdx);
        addBipushInsn(mv,cIdx);
//        mv.visitLdcInsn(owner);
//        mv.visitLdcInsn(name);
//        System.out.println("*************************** Idx ");
        switch (opcode) {
            case GETSTATIC:
                int fIdx = tmp.get(owner,name,true);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "GETSTATIC",
                        "(IIIILjava/lang/String;)V");

                //System.out.println("Idx "+ ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                addValueReadInsn(mv,desc,"GETFIELDORSTATIC_");
                break;
            case PUTSTATIC:
                fIdx = tmp.get(owner,name,true);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "PUTSTATIC",
                        "(IIIILjava/lang/String;)V");
                //System.out.println("Idx "+ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                break;
            case GETFIELD:
                fIdx = tmp.get(owner,name,false);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "GETFIELD",
                        "(IIIILjava/lang/String;)V");
                //System.out.println("Idx "+ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                addValueReadInsn(mv,desc,"GETFIELDORSTATIC_");
                break;
            case PUTFIELD:
                fIdx = tmp.get(owner,name,false);
                addBipushInsn(mv,fIdx);
                mv.visitLdcInsn(desc);

                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "PUTFIELD",
                        "(IIIILjava/lang/String;)V");
                //System.out.println("Idx "+ClassDepot.instance.getStaticFieldIndex(owner.replace('/','.'),name));
                mv.visitFieldInsn(opcode, owner, name, desc);
                break;
            default:
                System.err.println("Unknown field access opcode "+opcode);
                System.exit(1);
        }
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(owner);
        mv.visitLdcInsn(name);
        mv.visitLdcInsn(desc);
        switch (opcode) {
            case INVOKEVIRTUAL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INVOKEVIRTUAL",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            case INVOKESPECIAL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INVOKESPECIAL",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            case INVOKESTATIC:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INVOKESTATIC",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            case INVOKEINTERFACE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INVOKEINTERFACE",
                        "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
                break;
            default:
                System.err.println("Unknown method invocation opcode "+opcode);
                System.exit(1);
        }

        Label begin = new Label();
        Label handler = new Label();
        Label end = new Label();

        mv.visitLabel(begin);
        mv.visitMethodInsn(opcode, owner, name, desc);
        mv.visitJumpInsn(GOTO,end);
        mv.visitLabel(handler);

        //addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        //saddBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "INVOKEMETHOD_EXCEPTION", "()V");
        mv.visitInsn(ATHROW);

        mv.visitLabel(end);
        addValueReadInsn(mv,desc,"INVOKEMETHOD_");

        mv.visitTryCatchBlock(begin,handler,handler,null);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, System.identityHashCode(label)); // label.getOffset()
        switch (opcode) {
            case IFEQ:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFEQ", "(III)V");
                break;
            case IFNE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFNE", "(III)V");
                break;
            case IFLT:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFLT", "(III)V");
                break;
            case IFGE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFGE", "(III)V");
                break;
            case IFGT:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFGT", "(III)V");
                break;
            case IFLE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFLE", "(III)V");
                break;
            case IF_ICMPEQ:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ICMPEQ", "(III)V");
                break;
            case IF_ICMPNE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ICMPNE", "(III)V");
                break;
            case IF_ICMPLT:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ICMPLT", "(III)V");
                break;
            case IF_ICMPGE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ICMPGE", "(III)V");
                break;
            case IF_ICMPGT:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ICMPGT", "(III)V");
                break;
            case IF_ICMPLE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ICMPLE", "(III)V");
                break;
            case IF_ACMPEQ:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ACMPEQ", "(III)V");
                break;
            case IF_ACMPNE:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IF_ACMPNE", "(III)V");
                break;
            case GOTO:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "GOTO", "(III)V");
                break;
            case JSR:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "JSR", "(III)V");
                break;
            case IFNULL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFNULL", "(III)V");
                break;
            case IFNONNULL:
                mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IFNONNULL", "(III)V");
                break;
            default:
                System.err.println("Unknown jump opcode "+opcode);
                System.exit(1);
        }

        mv.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object cst) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(cst);
        if (cst instanceof Integer) {
            mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LDC", "(III)V");
        } else if (cst instanceof Long) {
            mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LDC", "(IIJ)V");
        } else if (cst instanceof Float) {
            mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LDC", "(IIF)V");
        } else if (cst instanceof Double) {
            mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LDC", "(IID)V");
        } else if (cst instanceof String) {
            mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LDC", "(IILjava/lang/String;)V");
        } else {
            System.err.println("Cannot instrument LDC for constant "+cst);
            System.exit(1);
        }
        mv.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        addBipushInsn(mv, var);
        addBipushInsn(mv, increment);
        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "IINC", "(IIII)V");
        mv.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
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

        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "TABLESWITCH", "(IIIII[I)V");
        mv.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
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

        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "LOOKUPSWITCH", "(III[I[I)V");
        mv.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getIid());
        addBipushInsn(mv,GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(desc);
        addBipushInsn(mv, dims);
        mv.visitMethodInsn(INVOKESTATIC, Config.analysisClass, "MULTIANEWARRAY", "(IILjava/lang/String;I)V");
        mv.visitMultiANewArrayInsn(desc, dims);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mv.visitMaxs(maxStack + 8, maxLocals);    //To change body of overridden methods use File | Settings | File Templates.
    }
    
}
