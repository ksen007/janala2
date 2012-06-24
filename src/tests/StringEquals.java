/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/23/12
 * Time: 5:04 PM
 */
public class StringEquals {
    public static void main(String[] args) {
        String s = "Hello";
        Main.MakeSymbolic(s);

        if (s.equals("World")) {
            System.out.println("Hello world");
        } else {
            System.out.println("else");
        }
    }
}
