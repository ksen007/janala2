/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import janala.logger.ClassNames;
import janala.logger.inst.IVisitor;
import janala.logger.inst.Instruction;
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

        IVisitor intp = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(Config.traceAuxFileName));
            ClassNames cnames = (ClassNames)inputStream.readObject();
            inputStream.close();
            cnames.init();

            intp = new ConcreteInterpreter(cnames);
            inputStream = new ObjectInputStream(new FileInputStream(Config.traceFileName));
            Instruction inst;
            while((inst  = (Instruction)inputStream.readObject())!=null) {
                inst.visit(intp);
            }
            inputStream.close();
        } catch (EOFException e) {
            ((ConcreteInterpreter)intp).endExecution();
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
