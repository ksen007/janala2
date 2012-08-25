/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 8/25/12
 * Time: 2:49 PM
 */
public class TestmeLong  {
    public static void testme(Long x,Long y){
        long z = foo(y);
        if(x.equals(z)){
            if(x>y+10){
                System.out.println("Error"); // ERROR
            }

        }
    }

    private static long foo(long y) {
        return 2*y;
    }

    public static void main(String[] args){
        long x = Main.readLong(22);
        Main.MakeSymbolic(x);
        long y = Main.readLong(11);
        Main.MakeSymbolic(y);
        testme(x,y);
    }
}

