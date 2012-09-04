/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

import java.sql.Timestamp;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class TimestampTest {
    public static void testme(Timestamp x,Timestamp y){
        if(x.after(y)){
//            System.out.println("Fixed "+Timestamp.valueOf("1976-01-01 01:00:00").getTime());
            if(y.before(Timestamp.valueOf("1976-01-01 01:00:00"))){
                System.out.println("Error "); // ERROR
            }

        }
    }

    public static void main(String[] args){
        long x = Main.readLong(10L);
        Main.MakeSymbolic(x);
        Main.Assume(x>0?1:0);
        Timestamp t1 = new Timestamp(x);
        System.out.println("t1 = " + t1);
        long y = Main.readLong(11L);
        Main.MakeSymbolic(y);
        Main.Assume(y > 0 ? 1 : 0);
        Timestamp t2 = new Timestamp(y);
        System.out.println("t2 = " + t2);
        testme(t1,t2);
    }
}