package example;

import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;

public class IntExample2o {
  
  private int greaterThanZero(int x) {
    if (x > 0) {
      return 1;
    }
    return -1;
  }
  
  public static void main(String[] args) {
    int x = new IntExample2o().greaterThanZero(1);
    if (x == 1) {
      System.out.println("Greater");
    }
  }
  
}
