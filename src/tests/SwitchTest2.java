/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 8:29 PM
 */
class MyException2 extends RuntimeException {

}

public class SwitchTest2 {

    public static int f(int x){
        //if(x>199){
            throw new MyException();
        //} else {
        //    return x;
        //}
    }

    public static void main(String[] args) {
        int x = Main.readInt(4);
        Main.MakeSymbolic(x);
        int y = 4;
        try {
            f(x);
            System.out.println("y = " + y);
        } catch(MyException e){
            y = x+10;
            if(y==250)
                System.out.println("OOPS ...");
        }
    }
}
