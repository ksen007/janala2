package tests;

import catg.CATG;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class Array2 {
    public static void main(String[] args) {
        char[] a = new char[3];
        a[0] = 'a';
        a[1] = 'b';
        a[2] = 'c';

        int x = CATG.readInt(0);
        if (a[x]=='c') {
            System.out.println("Hello");
        }
    }

}
