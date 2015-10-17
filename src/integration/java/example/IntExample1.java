package example;

import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;
import janala.instrument.Coverage;

import java.lang.Class;
import java.lang.reflect.Method;

public class IntExample1 {
  private int greaterThanZero(int x) {
    if (x > 0) {
      return 1;
    }
    return -1;
  }

  public void test1() {
    int x = greaterThanZero(1);
    if (x == 1) {
      System.out.println("Pass");
    } else {
      System.out.println("Fail");
    }
  }

  public static void test() throws Exception {
    Class<IntExample1> clazz = IntExample1.class;
    Method method = clazz.getMethod("test1");
    Object obj = clazz.newInstance();
    method.invoke(obj);
  }

  public static void main(String[] args) {
    new IntExample1().test1();
  }
}
