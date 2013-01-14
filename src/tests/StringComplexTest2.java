package tests;

import catg.CATG;

public class StringComplexTest2 {
	public static void main(String[] argv) {
		//String a= "abcdef";
		//String b= "de";
		//String dummy = "def";
		String a = CATG.readString("a");
		String b = CATG.readString("b");
		String dummy = CATG.readString("dummy");


		String patternString = "[a-zA-Z]*";
		if(!(a.matches(patternString) && a.length() >= 0 && a.length() <= 100)){
            System.out.println("Branch 1");
            return;
		}
		if(!(b.matches(patternString) && b.length() >= 0 && b.length() <= 100)){
            System.out.println("Branch 2");
			return;
		}

		if(substringEquals(a,3,b,dummy)){
			System.out.println("substringEquals(a,3,b) == true");
		}

		System.out.println("a : " + a);
		System.out.println("b : " + b);
	}

	// A and B are string variables, and S is integer value.
	// The constraint means A contains B and B starts at index S of A.
	private static boolean substringEquals(String A, int S, String B, String dummyString) {
		if (A.endsWith(dummyString) && dummyString.startsWith(B) && A.length() - dummyString.length() == S) {
			return true;
		} else {
			return false;
		}
	}
}
