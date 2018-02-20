package tests.bugreports.putfieldbug;

import catg.CATG;
import janala.Main;

public class SienaBuggyExample {

  private int irrelevant0;
  private Object tmp;


  public SienaBuggyExample(int x) {
    tmp = new Object();
    if (x != 65 && x >= 65 && x % 65 == 0) {
      irrelevant0 = -23;
      System.out.println("#########");
    } else if (x != 2 && x % 2 == 0) {
      irrelevant0 = 350;
      System.out.println("??????");
    } else if (x == 0) {
      irrelevant0 = 0;
    } else if(x == 2) {
      irrelevant0 = 700;
      System.out.println("!!!!!!");
    } else {
      irrelevant0 = 1;
    }
  }

  public void method(int x) {
    if (irrelevant0 + x == 35) {
      tmp = null;
    }
    if (irrelevant0 == -23) {
      irrelevant0 = tmp.hashCode() * 700;
    }
    if (tmp != null) {
      irrelevant0 = tmp.hashCode();
    }
  }

  public static void main(String[] args) {
    int x = CATG.readInt(0);
    int y = CATG.readInt(16);
    SienaBuggyExample tok = new SienaBuggyExample(x);
    System.out.println(tok.irrelevant0);
    tok.method(y);
    System.out.println(tok.tmp);
    System.out.println(tok.irrelevant0);
  }
}