/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/29/12
 * Time: 11:31 PM
 */
public class QSortLong {
        static void quicksort (long[] a, int lo, int hi) {
            int i=lo, j=hi;
                    long h;
            long x=a[(lo+hi)/2];

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

            if (lo<j) quicksort(a, lo, j);
            if (i<hi) quicksort(a, i, hi);
        }

        public static void main(String[] args) {
            long arr[] = new long[4];
            for(int i=0; i< 4; i++) {
                arr[i] = Main.readInt(0);
                Main.MakeSymbolic(arr[i]);
            }
            quicksort(arr,0,3);
        }

    }
