package janala.interpreters;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/27/12
 * Time: 10:34 PM
 */
public class StringConstants {
  private Map<String, Integer> stringValue;
  private ArrayList<String> reverseMap;
  final public static StringConstants instance = new StringConstants();

  public StringConstants() {
    stringValue = new TreeMap<String, Integer>();
    reverseMap = new ArrayList<String>();
  }

  public int get(String s) {
    Integer i = stringValue.get(s);
    if (i == null) {
      i = stringValue.size();
      stringValue.put(s, i);
      reverseMap.add(s);
    }
    return i;
  }

  public String get(int i) {
    try {
      return reverseMap.get(i);
    } catch (Exception e) {
      return "string" + i;
    }
  }
}
