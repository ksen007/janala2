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

package janala.utils;

import janala.config.Config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/29/12
 * Time: 1:58 PM
 */
public class MyLogger {
    private static ConsoleHandler handler = new ConsoleHandler();
    private static String testDataDir = "testdata/";

    static {
        handler.setFormatter(new MyFormatter());
    }
    
    public static Logger getLogger(String name) {
        Logger ret = Logger.getLogger(name);
        ret.setUseParentHandlers(false);
        ret.addHandler(handler);
        ret.setLevel(Config.instance.verbose?Level.FINE:Level.WARNING);
        return ret;
    }

    public static Logger getFileLogger(String name) {
        Logger ret = Logger.getLogger(name);
        FileHandler handler = null;
        try {
            handler = new FileHandler(name,true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        handler.setFormatter(new MyFormatter());
        ret.addHandler(handler);
        return ret;
    }

    public static Logger getTestLogger(String name) {
        if (!Config.instance.isTest)
            return getLogger(name);
        try {
            String filename = testDataDir+name;
            Logger ret = Logger.getLogger(name);
            ret.setUseParentHandlers(false);
            if (ret.getHandlers().length==0) {
                if ((new File(filename).exists())) {
                    FileHandler handler = new FileHandler(filename+".new");
                    handler.setFormatter(new MyFormatter());
                    ret.addHandler(handler);
                } else {
                    FileHandler handler = new FileHandler(filename);
                    handler.setFormatter(new MyFormatter());
                    ret.addHandler(handler);
                }
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void checkLog(Logger logger) {
        if (!Config.instance.isTest)
            return;
        Handler handlers[] = logger.getHandlers();
        if (handlers.length!=0) {
            if (handlers[0] instanceof FileHandler) {
                ((FileHandler)handlers[0]).close();
            }
        }
        String filename = testDataDir+logger.getName();
        File neW = new File(filename+".new");
        File old = new File(filename);

        if (neW.exists() && old.exists()) {
            if (!compareFiles(neW,old)) {
                Logger test = getFileLogger(Config.instance.testLog);
                test.warning("************* Test "+filename+" failed!! **************");
            }
        }
    }

    static private boolean compareFiles(File f1, File f2){
        try{
            if(f1.length() != f2.length())
                return false;

            BufferedInputStream in1 = new BufferedInputStream(new FileInputStream(f1));
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(f2));

            while(true){
                int a = in1.read();
                int b = in2.read();
                if(a != b) return false;
                if(a == -1){
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

class MyFormatter extends SimpleFormatter {
    @Override
    public String format(LogRecord logRecord) {
        return logRecord.getLevel()+": "+formatMessage(logRecord)+"\n";
    }
}