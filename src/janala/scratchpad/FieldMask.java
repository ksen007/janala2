package janala.scratchpad;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/3/12
 * Time: 11:26 AM
 */

class A2 {
    int x = 1;

    @Override
    public String toString() {
        return x+"";
    }
}

class B2 extends A2 {
    int x = 2;
}

public class FieldMask {
    public static void main(String[] args) {
        B2 tmp = new B2();
        System.out.println(tmp);
    }
}
