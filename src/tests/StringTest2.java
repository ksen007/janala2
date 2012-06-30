/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/29/12
 * Time: 10:48 PM
 */
public class StringTest2 {
    public static void main(String[] args) {
        String s1 = Main.readString("A");
        Main.MakeSymbolic(s1);
        String s2 = Main.readString("B");
        Main.MakeSymbolic(s2);

        if (s1.equals("C")) {
            if (!s1.equals(s2))
                System.out.println("then");
        } else {
            System.out.println("else");
        }
    }
}
