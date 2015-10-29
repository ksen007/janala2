
package janala.instrument;

import janala.logger.ClassNames;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class SnoopInstructionClassAdapter extends ClassVisitor {
  public SnoopInstructionClassAdapter(ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, 
      String signature, String[] exceptions) {
    Coverage.instance.setLastMethod(name + ":" + signature);
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    if (mv != null) {
      return new SnoopInstructionMethodAdapter(mv, name.equals("<init>"), 
          Coverage.get(), GlobalStateForInstrumentation.instance, ClassNames.getInstance());
    }
    return null;
  }
}
