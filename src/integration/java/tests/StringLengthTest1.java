package tests;

import catg.CATG;

public class StringLengthTest1 {
  public static void main(String[] argv){
    String a = CATG.readString("abcd");

    if(a.length() > 5 && a.length() <= 8 && a.length() != 6){
      System.out.println("a.length() > 5 && a.length() <= 8 && a.length() != 6");
    }
    System.out.println("a : " + a);
  }
}
