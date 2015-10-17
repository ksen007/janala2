package tests;

import janala.Main;

public class BoolTest {
    public static void main(String[] args){
        int a, b;
        boolean c;
        a = Main.readInt(1);
        Main.MakeSymbolic(a);
        b = Main.readInt(2);
        Main.MakeSymbolic(b);
        c = a==b;
        if ( c )
            System.out.println("a==b\n");
        else
            System.out.println("a!=b\n");
    }
}

