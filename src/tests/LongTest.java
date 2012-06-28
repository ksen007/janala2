/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/27/12
 * Time: 11:57 PM
 */
public class LongTest {
    public static void testme(long x,long y){
        long z = foo(y);
        if(z==x){
            if(x>y+10){
                System.out.println("Error"); // ERROR
            }

        }
    }

    private static long foo(long y) {
        return 2*y;
    }

    public static void main(String[] args){
        long x = Main.readLong(22L);
        Main.MakeSymbolic(x);
        long y = Main.readLong(11L);
        Main.MakeSymbolic(y);
        testme(x,y);
    }
}
