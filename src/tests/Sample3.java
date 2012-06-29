package tests;

import janala.Main;

public class Sample3 {
    public static void sample3(int y) {
        int sum = 0;
        for (int i = y; i < 3; i=i+1) {
            sum = sum + i;
        }
        if (y > 5)
            System.out.println("hello");
        if (sum < 2) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void main(String[] args){
        int y = Main.readInt(0);
        Main.MakeSymbolic(y);
        sample3(y);
    }
}

