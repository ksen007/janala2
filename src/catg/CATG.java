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

package catg;/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

import janala.Main;
import janala.interpreters.OrValue;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 12:09 PM
 */
public class CATG {
    public static int readInt(int x) {
        int y = Main.readInt(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Integer.MAX_VALUE?1:0);
        Main.Assume(y >= Integer.MIN_VALUE?1:0);
        return y;
    }

    public static long readLong(long x) {
        long y = Main.readLong(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Long.MAX_VALUE?1:0);
        Main.Assume(y >= Long.MIN_VALUE+1?1:0);
        return y;
    }

    public static char readChar(char x) {
        char y = Main.readChar(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Character.MAX_VALUE?1:0);
        Main.Assume(y >= Character.MIN_VALUE?1:0);
        return y;
    }

    public static byte readByte(byte x) {
        byte y = Main.readByte(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Byte.MAX_VALUE?1:0);
        Main.Assume(y >= Byte.MIN_VALUE?1:0);
        return y;
    }

    public static short readShort(short x) {
        short y = Main.readShort(x);
        Main.MakeSymbolic(y);
        Main.Assume(y <= Short.MAX_VALUE?1:0);
        Main.Assume(y >= Short.MIN_VALUE?1:0);
        return y;
    }

    public static boolean readBool(boolean x) {
        boolean y = Main.readBool(x);
        Main.MakeSymbolic(y);
        OrValue tmp;
        Main.Ignore();
        tmp = Main.AssumeOrBegin(y==true?1:0);
        Main.Ignore();
        tmp = Main.AssumeOr(!y?1:0,tmp);
        if (tmp!=null) {
            Main.AssumeOrEnd(tmp);
        }
        return y;
    }

    public static String readString(String x) {
        String y = Main.readString(x);
        Main.MakeSymbolic(y);
        return y;
    }
}
