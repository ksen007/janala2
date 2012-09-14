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

package tests;

import janala.Main;

import java.sql.Timestamp;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class TimestampTest {
    public static void testme(Timestamp x,Timestamp y){
        if(x.after(y)){
//            System.out.println("Fixed "+Timestamp.valueOf("1976-01-01 01:00:00").getTime());
            if(y.before(Timestamp.valueOf("1976-01-01 01:00:00"))){
                System.out.println("Error "); // ERROR
            }

        }
    }

    public static void main(String[] args){
        long x = Main.readLong(10L);
        Main.MakeSymbolic(x);
        Main.Assume(x>0?1:0);
        Timestamp t1 = new Timestamp(x);
        System.out.println("t1 = " + t1);
        long y = Main.readLong(11L);
        Main.MakeSymbolic(y);
        Main.Assume(y > 0 ? 1 : 0);
        Timestamp t2 = new Timestamp(y);
        System.out.println("t2 = " + t2);
        testme(t1,t2);
    }
}