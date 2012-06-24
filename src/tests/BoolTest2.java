/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/23/12
 * Time: 5:13 PM
 */
public class BoolTest2 {
    public static void main(String[] args) {
        boolean  a, b, c;


        a = Main.readBool(false);
        Main.MakeSymbolic(a);
        b = Main.readBool(false);
        Main.MakeSymbolic(b);
        c = Main.readBool(false);
        Main.MakeSymbolic(c);

        if (a && b && c) {
            System.out.println("Hello");
        }
    }

}
