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

package janala;

import janala.config.Config;
import janala.interpreters.OrValue;

import java.io.*;
import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 4:50 PM
 */
public class Main {
    private static boolean isInputAvailable() {
        if (index < inputs.size() && scopeDepth >= inputDepth) {
            String tmp = inputs.get(index);
            if (tmp.equals(Config.instance.scopeBeginMarker) || tmp.equals(Config.instance.scopeEndMarker)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static void Ignore() {}

    public static void Assume(int b) {
        if (b==0) {
            System.out.println("Assumption failed!");
            System.exit(0);
        }
    }

    public static void ForceTruth(int b) {
        if (b==0) {
            System.out.print("f,");
        } else {
            System.out.print("t,");
        }
    }

    public static void BeginScope() {
        scopeDepth++;
        if (index < inputs.size()) {
            if (inputs.get(index).equals(Config.instance.scopeBeginMarker)) {
                index++;
                inputDepth++;
            }
        }
    }

    public static void EndScope() {
        scopeDepth--;
        while (true) {
            if (scopeDepth >= inputDepth) {
                return;
            }
            if (index < inputs.size()) {
                String tmp = inputs.get(index++);
                if (tmp.equals(Config.instance.scopeBeginMarker)) {
                    inputDepth++;
                } else if (tmp.equals(Config.instance.scopeEndMarker)) {
                    inputDepth--;
                }
            }
        }
    }

    public static void AbstractEqualsConcrete(boolean b) {
    }

    public static boolean compare(Object a, Object b) {
        return a.equals(b);
    }


    public static OrValue AssumeOrBegin(int b) {
        return new OrValue(b!=0);
    }

    public static OrValue AssumeOr(int b, OrValue b2) {
        return new OrValue(b!=0 || b2.boolValue());
    }

    public static void AssumeOrEnd(OrValue b) {
        if (!b.boolValue()) {
            System.out.println("Assumption (OR) failed!");
            System.exit(0);
        }
    }

    public static void MakeSymbolic(int x) {    }
    public static void MakeSymbolic(long x) {    }
    public static void MakeSymbolic(char x) {    }
    public static void MakeSymbolic(byte x) {    }
    public static void MakeSymbolic(short x) {    }
    public static void MakeSymbolic(boolean x) {    }
    public static void MakeSymbolic(String x) {    }

    static public int readInt(int x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Integer.parseInt(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public long readLong(long x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Long.parseLong(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public char readChar(char x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
//            System.out.println("ReadChar:"+input);
            return (char)Integer.parseInt(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public short readShort(short x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Short.parseShort(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public byte readByte(byte x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return Byte.parseByte(input);
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public boolean readBool(boolean x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
            boolean ret = Integer.parseInt(input)!=0;
            //System.out.println(ret);
            return ret;
        } else {
            //System.out.println(x);
            return x;
        }
    }

    static public String readString(String x) {
        if (isInputAvailable()) {
            String input = inputs.get(index++);
            //System.out.println(input);
            return new String(input);
        } else {
            //System.out.println(x);
            return new String(x);
        }
    }

    private static ArrayList<String> inputs;
    private static int index;
    private static int scopeDepth;
    private static int inputDepth;

    static {
        inputs = new ArrayList<String>();
        index = 0;
        scopeDepth = 0;
        inputDepth = 0;
        DataInputStream in = null;
        try{
            FileInputStream fstream = new FileInputStream(Config.instance.inputs);
            in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                inputs.add(strLine);
            }
            in.close();
        } catch (Exception e) {
            //System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
    }


}
