/*  * Bug report from Jason Vallet
*/

package tests;

import catg.CATG;

public class test_q1_q4_min {

	private static class test {
	
		// Variables //
	
		public boolean b;
		public boolean c;
		public boolean e;
	
	
		public void q1_q4() {
            if (! ( ( b==true ) || (e==true) || (c==true))) {
				System.out.println("Precondition Error on q1_q4");
            }
		}
	}
	
	public static void main (String[] args) {
		test current_class = new test();
        // The following API calls (commented out) should not be used for making Boolean's symbolic,
        // because they do not restrict the possible values of a Boolean which is treated as int in Java bytecode
//        current_class.d = Main.readBool(false);
//        Main.MakeSymbolic(current_class.d);
//        current_class.e = Main.readBool(false);
//        Main.MakeSymbolic(current_class.e);
//        current_class.b = Main.readBool(false);
//        Main.MakeSymbolic(current_class.b);
//        current_class.c = Main.readBool(false);
//        Main.MakeSymbolic(current_class.c);
//        current_class.a = Main.readBool(false);
//        Main.MakeSymbolic(current_class.a);

		current_class.e = CATG.readBool(false);
		current_class.b = CATG.readBool(false);
		current_class.c = CATG.readBool(false);
		current_class.q1_q4();
	}
}
