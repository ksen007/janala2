/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 9:05 PM
 */
public class NonLinear {
        public static void main(String[] args){
            int x, y, z;

            x = Main.readInt(0);
            Main.MakeSymbolic(x);

            if((x+1)*(x+1)>=6)
                if(x>0)
                    System.out.println("Reached destination");

        }
}
