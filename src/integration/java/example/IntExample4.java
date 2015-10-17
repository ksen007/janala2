package example;

import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;
import janala.instrument.Coverage;

import catg.CATG;
import janala.Main;

public class IntExample4 {
    
  private static int notEqual4(int x, int y) {
    if (y == 2) {
      if (x == y) {
        return 1;
      }
    }
    return -1;
  }

    public static void main(String[] args) {
      int y = CATG.readInt(0);
      int z = CATG.readInt(0);
      int x = notEqual4(y, z);
      System.out.println("good");
    }

}
