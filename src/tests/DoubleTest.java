/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 4:58 PM
 */
public class DoubleTest {
    public static void main(String[] args) {
        foo(4.5,6.7);
        bar(1L,10L);
    }

    private static long bar(long l, long l1) {
        return l + l1;
    }

    private static double foo(double v, double v1) {
        return v + v1;
    }
}
