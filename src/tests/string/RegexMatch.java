package tests.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatch {
	public static boolean IsMatch(String s, String patternString){
		Pattern p = Pattern.compile(patternString);
		Matcher m = p.matcher(s);
		return m.find();
	}
}
