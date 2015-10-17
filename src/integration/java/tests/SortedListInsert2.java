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
public class SortedListInsert2 {
    private static class List {
        private int x;
        private List next;

        private static final int SENTINEL = Integer.MAX_VALUE;

        private List(int x, List next) {
            this.x = x;
            this.next = next;
        }

        public List() {
            this(SENTINEL, null);
        }

        public void insert(int data) {
            if (data > this.x) {
                next.insert(data);
            } else {
                next = new List(x, next);
                x = data;
            }
        }
    }

    public static void main(String[] args) {
        int len = 0;
        List list = new List();
        while (len < 3 && CATG.readBool(false)) {
            len++;
            int x = CATG.readInt(0);
            System.out.print("Input ");
            System.out.println(x);
            list.insert(x);
            System.out.println("Length  = " + len);
        }
        CATG.equivalent("test", "loc1", (len>1)?2:len);

        int x = CATG.readInt(0);
        System.out.print("Input f ");
        System.out.println(x);
        list.insert(x);
        System.out.println("Length f = "+(len+1));
    }
}
