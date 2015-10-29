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

package tests;

import catg.CATG;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class AnnotationExample {
    public static int count(int[] array,int element){
        int ret = 0;
        for (int i=0; i<array.length; i++)
            if (array[i]==element) {
                CATG.event("a");
                ret++;
            }
        return ret;
    }
    public static void main(String[] args) {
        CATG.pathRegex("test1", "a?ba?");
        int[] input = CATG.readIntArray(7, 0);

        CATG.BeginScope();
        int count1 = count(input, 3);
        System.out.print("count1 = ");
        System.out.println(count1);

        CATG.EndScope();
        count1 = CATG.abstractInt(count1);
        System.out.print("count1 = ");
        System.out.println(count1);
        CATG.equivalent("test2", "loc1", count1>0);
        CATG.event("b");

        CATG.BeginScope();
        int count2 = count(input, 7);
        System.out.print("count2 = ");
        System.out.println(count2);

        CATG.EndScope();
        count2 = CATG.abstractInt(count2);
        System.out.print("count2 = ");
        System.out.println(count2);

        if (count1 <= 0 && count2 <= 0) {
            System.out.println("do something ");
        } else {
            System.out.println("do something else");
        }
    }
}
