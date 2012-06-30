/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import janala.config.Config;
import janala.logger.ClassNames;
import janala.logger.inst.IVisitor;
import janala.logger.inst.Instruction;
import janala.utils.MyLogger;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 12:22 PM
 */
public class LoadAndExecuteInstructions {
    private static Instruction readInst(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
        Instruction inst;

        try {
            inst  = (Instruction)inputStream.readObject();
        } catch (EOFException e) {
            inst = null;
        }
        return inst;
    }

    public static void main(String[] args) {
        ObjectInputStream inputStream = null;
        IVisitor intp = null;

        Logger tester = MyLogger.getTestLogger(Config.mainClass+"."+Config.iteration);

        try {
            inputStream = new ObjectInputStream(new FileInputStream(Config.traceAuxFileName));
            ClassNames cnames = (ClassNames)inputStream.readObject();
            inputStream.close();
            cnames.init();

            intp = new ConcreteInterpreter(cnames);
            inputStream = new ObjectInputStream(new FileInputStream(Config.traceFileName));

            Instruction inst, next;
            inst=readInst(inputStream);
            next=readInst(inputStream);
            while(inst !=null) {
                intp.setNext(next);
                inst.visit(intp);
                inst = next;
                next=readInst(inputStream);
            }
            ((ConcreteInterpreter)intp).endExecution();
            inputStream.close();
            MyLogger.checkLog(tester);
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
