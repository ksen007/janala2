/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.scratchpad;

public class Test1 {

    public  static int foo(int x, int y, String s) {
        System.out.println("do nothing");
        return x+y;
    }

    public static void main(String[] args) {
        foo(9999999,3,"Hello");
        System.out.println("c");
    }
}
