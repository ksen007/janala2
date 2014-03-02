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

import janala.config.Config;
import janala.logger.ClassNames;
import janala.logger.inst.IVisitor;
import janala.logger.inst.Instruction;
import janala.utils.MyLogger;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 12:22 PM
 */
public class LoadAndExecuteInstructions {
    private final static Logger logger = MyLogger.getLogger(LoadAndExecuteInstructions.class.getName());

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
            inputStream = new ObjectInputStream(new FileInputStream(Config.instance.traceAuxFileName));
            ClassNames cnames = (ClassNames)inputStream.readObject();
            inputStream.close();
            //cnames.init();

            intp = new ConcolicInterpreter(cnames);
            inputStream = new ObjectInputStream(new FileInputStream(Config.instance.traceFileName));

            Instruction inst, next;
            inst=readInst(inputStream);
            next=readInst(inputStream);
            int i=0;
            while(inst !=null) {
                intp.setNext(next);
                logger.log(Level.FINE,"{0}",inst);
                //System.out.println(inst);
                inst.visit(intp);
                inst = next;
                next=readInst(inputStream);
                i++;
            }
            ((ConcolicInterpreter)intp).endExecution();
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
