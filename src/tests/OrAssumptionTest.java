/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;
import janala.interpreters.OrValue;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/9/12
 * Time: 9:44 AM
 */
public class OrAssumptionTest {
    public static void main(String[] args) {
        int x = Main.readInt(10);
        Main.MakeSymbolic(x);

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
