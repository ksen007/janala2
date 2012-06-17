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
        //System.out.println("visitMethod "+name+" desc "+desc);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null) {
            //System.out.println("*******************");
            mv = new SnoopInstructionMethodAdapter(mv);
        }
        //System.out.println("end visitMethod "+name);

        return mv;
    }
}
