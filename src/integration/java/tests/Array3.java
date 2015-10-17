
package tests;

import catg.CATG;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class Array3 {
    public static void main(String[] args) {
        long[] a = new long[3];
        a[0] = 3;
        a[1] = 5;
        a[2] = 10;

        int x = CATG.readInt(0);
        if (a[x]==5) {
            System.out.println("Hello");
        }
    }

}
