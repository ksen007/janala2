/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 12:06 PM
 */
public class BoolFlow2 {
    public static void main(String[] args) {
        int x = Main.readInt(0);
        Main.MakeSymbolic(x);
        Main.Assume(x < 5);
    }
}
