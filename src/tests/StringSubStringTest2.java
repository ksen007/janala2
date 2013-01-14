package tests;

import catg.CATG;

public class StringSubStringTest2 {
	public static void main(String[] argv){
		String a = CATG.readString("abcd");

		if(a.contains("xyz") && a.startsWith("123") && a.endsWith("!?")){
			System.out.println("a contains \"xyz\" && startswith \"123\" && endswith \"!?\"");
		}

		System.out.println("a : " + a);
	}
}
