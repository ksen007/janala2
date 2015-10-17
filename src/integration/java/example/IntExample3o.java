package example;

import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;

public class IntExample3o {
  
  private boolean lessThanZero(int x) {
    if (x <= 0) {
      return true;
    }
    return false;
  }
  
  public static void main(String[] args) {
    boolean x = new IntExample3o().lessThanZero(1);
    if (x) {
      System.out.println("Greater");
    }
  }
  
}
