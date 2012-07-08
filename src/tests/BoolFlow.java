/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/30/12
 * Time: 9:04 PM
 */
public class BoolFlow {
    public static void main(String[] args) {
        int x = Main.readInt(10);
        Main.MakeSymbolic(x);
        Main.Assume(x < 5?1:0);
    }
}
