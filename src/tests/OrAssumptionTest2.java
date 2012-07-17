/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;
import janala.interpreters.OrValue;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/16/12
 * Time: 11:54 PM
 */
public class OrAssumptionTest2 {
    public static void main(String[] args) {
        int x = 401;

        int y = Main.readInt(20);
        Main.MakeSymbolic(y);

        if (y < 0) {

            Main.Ignore();
            OrValue tmp = Main.AssumeOrBegin(x < -100?1:0);
            Main.Ignore();
            Main.AssumeOrEnd(Main.AssumeOr(x>100?1:0,tmp));
            if (y+x==300) {
                System.out.println("then");
            } else {
                System.out.println("else");
            }
        }
    }
}
