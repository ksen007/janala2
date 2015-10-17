package example;

import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;
import janala.Main;

public class IntExample1o {
    
    private static int greaterThanZero(int x) {
      if (x > 0) {
        return 1;
      }
      return -1;
    }

    public static void main(String[] args) {
      int i = 1;
      Main.MakeSymbolic(i);
      int x = greaterThanZero(i);
      
      if (x == 1) {
        System.out.println("Greater");
      }
    }

}
