package janala.utils

import static org.junit.Assert.assertTrue
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class MyTestClass {
  static boolean called = false;
  
  public void testme() {
    if (true == and(true, true)) {
      println("Pass")
    } else {
      println("Fail")
    }
    called = true
  }
  
  private boolean and(boolean a, boolean b) {
    return a && b
  }
}

@CompileStatic
class RunnerTest {
  @Test
  void runTest() {
    assertTrue(!MyTestClass.called)
    def runner = new Runner("janala.utils.MyTestClass", "testme")
    runner.runConcrete()
    assertTrue(MyTestClass.called)
  }
}

