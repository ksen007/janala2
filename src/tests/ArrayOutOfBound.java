/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/20/12
 * Time: 10:26 AM
 */
public class ArrayOutOfBound {
    int[] arr;

    public ArrayOutOfBound() {
        arr = new int[10];
    }

    public static void main(String[] args) {
        ArrayOutOfBound x = new ArrayOutOfBound();
        try {
            int y = x.arr[10];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Exiting");
        }
    }
}
