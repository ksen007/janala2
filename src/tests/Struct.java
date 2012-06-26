/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

public class Struct {
    public int x = 0;
    public Struct next;

    public static int f(int v){
        return 2*v+1;
    }

    public static void testme(Struct p,int y){
        if(y>0){
            if(p!=null){
                if(f(y)==p.x){
                    System.out.println("ERROR");
                }
            }
        }
    }

    public static void main(String[] args) {
        int y = Main.readInt(0);
        Main.MakeSymbolic(y);
        Struct s;
        s = new Struct();
        s.x = Main.readInt(0);
        Main.MakeSymbolic(s.x);
        testme(s,y);
    }
}
