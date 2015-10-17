package tests;

import catg.CATG;

public class StringSubStringTest1 {
	public static void main(String[] argv){
		String a = CATG.readString("a");
		String b = CATG.readString("b");
		String c = CATG.readString("c");
		String d = CATG.readString("d");
		String e = CATG.readString("e");

		if(a.contains(b) && a.startsWith(c) && a.endsWith(d) ){
			System.out.println("a.contains(b) && a.startsWith(c) && a.endsWith(d)");
		}

		System.out.println("a : " + a);
		System.out.println("b : " + b);
		System.out.println("c : " + c);
		System.out.println("d : " + d);
		System.out.println("e : " + e);
	}
}
