/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

import java.util.HashMap;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/29/12
 * Time: 11:35 PM
 */
public class StringTest3 {
    public static void main(String[] args) {
        HashMap<String,String> map =new HashMap<String, String>();

        String s1 = Main.readString("A");
        Main.MakeSymbolic(s1);
        String s2 = Main.readString("B");
        Main.MakeSymbolic(s2);

        map.put(s1,s2);

        if (map.get(s1).equals("C")) {
            if (!s1.equals(s2))
                System.out.println("then");
        } else {
            System.out.println("else");
        }
    }
}
