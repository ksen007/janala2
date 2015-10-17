package janala.utils;

public class Debugger {
  public static void Debug(DebugAction debugAction) {
    debugAction.execute();
  }
}
