/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package tests;

import janala.Main;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/23/12
 * Time: 5:13 PM
 */
public class BoolTest3 {

	ArrayList<BooleanHolder> list = new ArrayList<BooleanHolder>();

    public static void main(String[] args) {
    	new BoolTest3().execute();
    }

    public void execute(){
    	list.add(new BooleanHolder());
    	BooleanHolder bh= list.get(0);
//    	BooleanHolder bh= new BooleanHolder();

//        boolean x= Main.readBool(false);
//        Main.MakeSymbolic(x);
//        bh.a = x;
//        x= Main.readBool(false);
//        Main.MakeSymbolic(x);
//        bh.b = x;
//        x= Main.readBool(false);
//        Main.MakeSymbolic(x);
//        bh.c = x;

        bh.a = Main.readBool(false);
        Main.MakeSymbolic(bh.a);
        bh.b = Main.readBool(false);
        Main.MakeSymbolic(bh.b);
        bh.c = Main.readBool(false);
        Main.MakeSymbolic(bh.c);

        if (bh.a && bh.b && bh.c) {
            System.out.println("Hello");
        }
    }

}
