/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import janala.analysis.ClassNames;
import janala.analysis.inst.IVisitor;
import janala.analysis.inst.Instruction;
import janala.config.Config;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 12:22 PM
 */
public class LoadAndExecuteInstructions {
    public static void main(String[] args) {
        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(Config.traceAuxFileName));
            ClassNames cnames = (ClassNames)inputStream.readObject();
            inputStream.close();
            cnames.init();

            IVisitor intp = new PrintInterpreter();
            inputStream = new ObjectInputStream(new FileInputStream(Config.traceFileName));
            Instruction inst;
            while((inst  = (Instruction)inputStream.readObject())!=null) {
                //System.out.println(inst);
                inst.visit(intp);
            }
            inputStream.close();
        } catch (EOFException e) {
            System.out.println("Read all instructions");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
