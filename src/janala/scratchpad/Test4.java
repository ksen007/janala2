/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.scratchpad;

public class Test4 {
    int x;
    double v;
    Object m;
    int[] a;

    public void foo(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        Test4 t = new Test4();
        t.x = 9;
        t.v = 7.8d;
        t.m = new String("c");
        System.out.println("t.x = " + t.x);
        System.out.println("t.v = " + t.v);
        System.out.println("t.m = " + t.m);
        int a[] = {1,2,3};
        t.a = a;
        t.foo(t.a);
    }
}
