package tests;

import catg.CATG;

public class Testme {
    private static int foo(int y) {
        return 2*y;
    }

    public static void testme(int x,int y){
        int z = foo(y);
        if(z==x){
            if(x>y+10){
                System.err.println("Error"); // ERROR
            }

        }
    }

    public static void main(String[] args){
        int x = CATG.readInt(2);
        int y = CATG.readInt(1);
        testme(x,y);
        System.out.println("x = "+x+", y = "+y);
    }
}
