/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.instrument;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class SnoopInstructionTransformer implements ClassFileTransformer {

	public static void premain(String agentArgs, Instrumentation inst) {
        //System.out.println("calling premain");
		inst.addTransformer(new SnoopInstructionTransformer());
	}

	public byte[] transform(ClassLoader loader,String cname, Class<?> c, ProtectionDomain d, byte[] cbuf)
            throws IllegalClassFormatException {

		if (!cname.startsWith("janala")
                && !cname.startsWith("gnu/trove")
                && !cname.startsWith("java/util")
                && !cname.startsWith("java/io")
                && !cname.startsWith("java/security")
                && !cname.startsWith("javax/security")
                && !cname.startsWith("sun/security")
                && !cname.startsWith("sun/reflect")
                && !cname.startsWith("com/apple/java")
                && !cname.startsWith("java/lang")) {
            //System.err.println("((((((((((((((( transform "+cname);
            ClassReader cr = new ClassReader(cbuf);
            ClassWriter cw = new ClassWriter(cr, 0);
            ClassVisitor cv = new SnoopInstructionClassAdapter(cw);
//            ClassVisitor cv = new SnoopInstructionClassAdapter(new TraceClassVisitor(cw,new PrintWriter( System.out )));
            cr.accept(cv, 0);
            //System.err.println(")))))))))))))) end transform "+cname);
            return cw.toByteArray();
        } else {
            //System.out.println("--------------- skipping "+cname);
        }
		return cbuf;
	}

}
