/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 3:20 PM
 */
public class TestmeInteger {
    public static void testme(Integer x,Integer y){
        int z = foo(y);
        if(x.equals(z)){
            if(x>y+10){
                System.out.println("Error"); // ERROR
            }

        }
    }

    private static int foo(int y) {
        return 2*y;
    }

    public static void main(String[] args){
        int x = Main.readInt(22);
        Main.MakeSymbolic(x);
        int y = Main.readInt(11);
        Main.MakeSymbolic(y);
        testme(x,y);
    }
}
