/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

public class Testme {

    public static void testme(int x,int y){
        int z = foo(y);
        if(z==x){
            if(x>y+10){
                System.out.println("Error"); // ERROR
            }

        }
    }

    private static int foo(int y) {
        return 2*y;
    }

    public static void main(String[] args){
        int x = 22;
        Main.MakeSymbolic(x);
        int y = 11;
        Main.MakeSymbolic(y);
        testme(x,y);
    }
}
