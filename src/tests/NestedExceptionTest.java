/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 10:02 PM
 */
public class NestedExceptionTest {
    public static void f() {
        throw new RuntimeException("Hello");
    }
    public static void main(String[] args) {
        try {
            try {
                f();
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
    }
}
