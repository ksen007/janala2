/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 9:26 PM
 */
public class QSort {
    static void quicksort (int[] a, int lo, int hi) {
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

        if (lo<j) quicksort(a, lo, j);
        if (i<hi) quicksort(a, i, hi);
    }

    public static void main(String[] args) {
        int arr[] = new int[5];
        for(int i=0; i< 5; i++) {
            arr[i] = i*17%11;
            Main.MakeSymbolic(arr[i]);
            System.out.println(arr[i]);
        }
        quicksort(arr,0,4);
    }

}
