package tests;

import catg.CATG;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class Array5 {
    public static void main(String[] args) {
        int[][] a = new int[][]{new int[]{1,2,3}, new int[]{4,5,6}, new int[]{7,8,9}};

        int x = CATG.readInt(0);
        int y = CATG.readInt(0);
        if (a[x][y]==5) {
            System.out.println("Hello");
        }
    }

}
