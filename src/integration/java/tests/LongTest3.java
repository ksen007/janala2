package tests;
import catg.CATG;

public class LongTest3 {
	public static void main(String[] args){
        long x = CATG.readLong(10);
        long y = CATG.readLong(20);
        System.out.println(x);
        System.out.println(y);

        // constraint( x <= 20 && x >= 0 )
        if(!( x <= 20 && x >= 0)) {
            return;
        }

        // constraint( y <= 30 && y >= 0 )
        if(!( y <= 30 && y >= 10)) {
            return;
        }

        // condition( x == y )
        if(!( x == y )) {
            return;
        }

        System.out.println("-- Reached a solution --");
    }
}
