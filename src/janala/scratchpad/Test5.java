/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.scratchpad;

public class Test5 {
    public static void main(String[] args) {
        B b = new B();
        b.x = 4;
        A a = (A)b;
        System.out.println(b.x + "a.x = " + a.x);
    }
}

class A {
    int x;
}

class B extends A {
    int x;
}


