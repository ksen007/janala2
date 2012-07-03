/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

import java.util.TreeMap;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 8:20 PM
 */
public class StringMapTest {
    public static void main(String[] args) {
        String x = Main.readString("x");
        Main.MakeSymbolic(x);
        TreeMap<String,String> map = new TreeMap<String, String>();
        map.put("y","x");
        String z = map.get("y");
        if (x.equals(z)) {
            System.out.println("then");
        } else {
            System.out.println("else");
        }
    }
}
