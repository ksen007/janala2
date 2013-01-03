package tests.string;
import catg.CATG;

public class StringRegexTest1 {
	public static void main(String[] argv){
		//String a = "1AdexaaaaabbccccYXYX";
		String a = CATG.readString("xyz");

		String patternString = "1?[a-cA-C]*[de]+[^a-e]a{5,7}b{2}[cC]{3,}(XY|YX)+";

		if(RegexMatch.IsMatch(a, patternString)){
			System.out.println("a matches "+patternString);
		}

		System.out.println("a : "+ a);
	}
}
