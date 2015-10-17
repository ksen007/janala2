package tests;

import catg.CATG;

public class StringLengthTest2 {
	public static void main(String[] argv){
		String a = CATG.readString("abcd");
		String b = CATG.readString("efgh");

		if(a.length() > b.length()){
			System.out.println("a.length() > b.length");
		}

		System.out.println("a : " + a);
		System.out.println("b : " + b);
	}
}
