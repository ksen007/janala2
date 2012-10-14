/*  * Bug report from Jason Vallet
*/

package tests;

import catg.CATG;

public class test_q1_q4 {

	private static class test {
	
		// Variables //
	
		public boolean a;
		public boolean b;
		public boolean c;
		public boolean d;
		public boolean e;
	
	
		// Functions //
	
		private void ResetBool () {
			a = false;
			b = false;
			c = false;
			d = false;
			e = false;
		}
	
		public void q1_q4() {
			if (! (( ( b==true && a==true) || (b==true) || (e==true && b==true && a==true) || (c==true) || (d==true && e==true) ))) {
				System.out.println("Precondition Error on q1_q4");
			} else {
	
			ResetBool();
			d = true;
			e = true;
			c = true;
	
				if (! (( ( d==true) || (d==true && e==true && c==true) || (a==true) ))) {
					System.out.println("Postcondition Error on q1_q4");
				}
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

		current_class.d = CATG.readBool(false);
		current_class.e = CATG.readBool(false);
		current_class.b = CATG.readBool(false);
		current_class.c = CATG.readBool(false);
		current_class.a = CATG.readBool(false);
		System.out.print("d = " + current_class.d + " ; ");
		System.out.print("e = " + current_class.e + " ; ");
		System.out.print("b = " + current_class.b + " ; ");
		System.out.print("c = " + current_class.c + " ; ");
		System.out.print("a = " + current_class.a + " ; ");
		System.out.print("\n");
		//System.out.println("q1_q4()");

		current_class.q1_q4();
		System.out.print("d = " + current_class.d + " ; ");
		System.out.print("e = " + current_class.e + " ; ");
		System.out.print("b = " + current_class.b + " ; ");
		System.out.print("c = " + current_class.c + " ; ");
		System.out.print("a = " + current_class.a + " ; ");
		System.out.print("\n");
	}
}
