/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.scratchpad;

public class Test3 {

    public static void foo(float[] fa) {
        if (fa instanceof float[]) {
            System.out.println("H");
        }
        String f = "hu";
        if (f instanceof String) {
            System.out.println("n");
        }
    }

    public static void main(String[] args) {
        double d;
        String s;

        d = 0.45d;
        s = "hello";
        System.out.println(d+s);
        float[] fa = {6.5f,8.9f};
        foo(fa);
    }
}
