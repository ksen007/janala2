package tests;
import catg.CATG;

public class StringComplexTest1 {
	public static void main(String[] argv){
		//String a = "1AdeeexaaaaabbccccYX";
		String a = CATG.readString("xyz");

		String patternString = "1?[a-cA-C]*[de]+[^a-e]a{5,7}b{2}[cC]{3,}(XY|YX)+";

		if(a.startsWith("1") && a.contains("cccc") && a.length() == 20 && a.matches(patternString)){
			System.out.println("a.startsWith(\"1\") && a.contains(\"cccc\") && a.length() == 20 && a matches "+patternString);
		}

		System.out.println("a : "+ a);
	}
}
