/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.instrument;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class SnoopInstructionClassAdapter extends ClassAdapter {
    public SnoopInstructionClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //System.out.println("<**************** "+name+" desc "+desc);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null) {
//            System.out.println("******************* "+((access & Opcodes.ACC_STATIC)>0));
            mv = new SnoopInstructionMethodAdapter(mv,name.equals("<init>"));
        }
        //System.err.println("****************> end "+name);

        return mv;
    }
}
