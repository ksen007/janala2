/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

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
                    if(p.next!=p) System.out.println("ERROR");
                }
            }
        }
    }

    public static void main(String[] args) {
        Struct s = new Struct();
        int y = 2;
        testme(s,y);
    }
}
