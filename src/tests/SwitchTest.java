/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;


class MyException extends RuntimeException {

}

public class SwitchTest {

    public static int f(int x){
        if(x>199){
            throw new MyException();
        } else {
            return 2*x+1;
        }
    }

    public static int g(int y){
        int ret = f(y) * 23;
        return ret;
    }

    public static void main(String[] args) {
        int x = 100;
        int y;
        switch(x){
            case -100:
                y=1;
                break;
            case 0:
                y = 2;
                break;
            case 100:
                y = 3;
                break;
            default:
                y=4;
        }
        try {
            int z = g(x);
            if(z==69){
                System.out.println("y = " + y);
            }
        } catch(MyException e){
            y = x+10;
            if(y==250)
                System.out.println("OOPS ...");
        }
    }
}
