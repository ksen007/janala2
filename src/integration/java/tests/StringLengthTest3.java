package tests;

import catg.CATG;

public class StringLengthTest3 {
	public static void main(String[] argv){
		String a = CATG.readString("abcd");
		int x = CATG.readInt(1);

		if(a.length() == x){
			System.out.println("a.length() == x");
		}

		System.out.println("a : " + a);
		System.out.println("x : " + x);
	}
}
