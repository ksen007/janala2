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

import janala.Main;

public class QsortBuggy {

    public static void qsort(int[] a, int lo, int hi) {
        int i=lo, j=hi, h;
        int x=a[(lo+hi)/2];

        do {
            while (a[i]<x) i++;
            while (a[j]>x) j--;
            if (i<=j) {
                h=a[i];
                a[i]=a[j];
                a[j]=h;
                i++;
                j--;
            }
        } while (i<=j);

        if (lo<j) qsort(a, lo, j-1);
        if (i<hi) qsort(a, i, hi);

    }

    public static void main(String[] args) {
        final int N = 4;

        System.out.print("Input: ");
        int data[] = new int[N];
        for (int i = 0; i < N; i++) {
            data[i] = Main.readInt(0);
            Main.MakeSymbolic(data[i]);
        }
        System.out.println();

        qsort(data,0,N-1);

        System.out.print("Output: ");

        for(int j=0; j<N;j++) {
            System.out.print(data[j]+" ");
        }
        System.out.println();
        for(int i=0; i<N-1;i++) {
            if (data[i] > data[i+1]) {
                System.err.println("Array data is not sorted");
                (new RuntimeException()).printStackTrace();
            }
        }


    }
}
