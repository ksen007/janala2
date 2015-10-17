package janala.utils;

import janala.config.Config;
import janala.interpreters.ConcolicInterpreter;
import janala.logger.ClassNames;
import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;

import java.lang.String;
import java.lang.Class;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A runner class that create a class instance and run a method.
 */
public final class Runner {
  private final Class<?> clazz;
  private final Method method;
  private final ConcolicInterpreter interpreter;

  static private final ClassNames classNames = ClassNames.getInstance();

  /** Create a runner that creates an instance and calls a method */
  public Runner(String className, String methodName) throws ClassNotFoundException,
  NoSuchMethodException, SecurityException {
    clazz = Class.forName(className);
    method = clazz.getMethod(methodName);
    interpreter = new ConcolicInterpreter(ClassNames.getInstance(), Config.instance);
    DirectConcolicExecution dc = new DirectConcolicExecution(interpreter, false);
    DJVM.setInterpreter(dc);
  }

  public Runner(Class<?> clazz, Method method) {
    this.clazz = clazz;
    this.method = method;
    interpreter = new ConcolicInterpreter(ClassNames.getInstance(), Config.instance);
    DirectConcolicExecution dc = new DirectConcolicExecution(interpreter, false);
    DJVM.setInterpreter(dc);
  }

  /**
   * Create an instance and call the void method.
   *
   * Equivalent to:
   *     new MyClass().myMethod();
   */
  public void run() throws IllegalAccessException, IllegalArgumentException,
  InvocationTargetException, InstantiationException {
    String classInternalName = clazz.getName().replace(".", "/");
    int classIdx = classNames.get(classInternalName);
    int iid = 1;

    DJVM.NEW(iid++, 0, classInternalName, classIdx);
    DJVM.SPECIAL(0); // Allocated a new object on stack

    DJVM.DUP(iid++, 0);
    DJVM.INVOKESPECIAL(iid++, 0, classInternalName, "<init>", "()V");
    DJVM.INVOKEMETHOD_END();
    Object obj = clazz.newInstance();
    DJVM.GETVALUE_void();
    DJVM.GETVALUE_Object(obj); //Initialized a new object on stack

    DJVM.DUP(iid++, 0);
    DJVM.INVOKEVIRTUAL(iid++, 0, classInternalName, method.getName(), "()V");
    method.invoke(obj);
    DJVM.INVOKEMETHOD_END();
    DJVM.GETVALUE_void();

    DJVM.flush();
  }

  /** Run the method without instrumentation. */
  public void runConcrete() throws IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, InstantiationException {
    Object obj = clazz.newInstance();
    method.invoke(obj);
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.out.println("Usage java janala.utils.Runner <test-class> <test-method>");
      System.exit(0);
    }

    Runner runner = new Runner(args[0], args[1]);
    runner.run();
  }
}
