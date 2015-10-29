package janala;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import janala.config.Config;
import janala.instrument.Coverage;
import janala.interpreters.OrValue;
import janala.utils.MyLogger;
import janala.utils.Inputs;

import java.io.*;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Main runtime environment for parsing inputs and writing state files. */
public class Main {
  private final static Logger logger = MyLogger.getLogger(Coverage.class.getName());
  public static boolean isInPrefix = true;
  public static boolean skipPath = false;

  private static Inputs inputs;
  private static int scopeDepth;
  private static int inputDepth;

  static {
    scopeDepth = 0;
    inputDepth = 0;
    inputs = new Inputs(Config.instance.inputs);
  }
  
  /** Set the inputs of the system. */
  static void setInput(Inputs inputsOverride) {
    inputs = inputsOverride;
  }

  private static boolean isInputAvailable() {
    if (inputs.isInputAvailable() && scopeDepth >= inputDepth) {
      return !inputs.isBeginScope() && !inputs.isEndScope();
    }
    return false;
  }

  public static void SkipPath() {
    skipPath = true;
  }

  public static void Ignore() {}

  public static void Assume(int b) {
    if (b == 0) {
      System.out.println("Assumption failed!");
      System.exit(0);
    }
  }

  public static void ForceTruth(int b) {
    if (b == 0) {
      System.out.print("f,");
    } else {
      System.out.print("t,");
    }
  }

  public static void BeginScope() {
    scopeDepth++;
    if (inputs.isInputAvailable()) {
      if (inputs.isBeginScope()) {
        inputs.next();
        inputDepth++;
      }
    }
  }

  public static void EndScope() {
    scopeDepth--;
    while (true) {
      if (scopeDepth >= inputDepth) {
        return;
      }
      if (inputs.isInputAvailable()) {
        if (inputs.isBeginScope()) {
          inputDepth++;
        } else if (inputs.isEndScope()) {
          inputDepth--;
        }
        inputs.next();
      }
    }
  }

  /** Insert a check that an abstract value is equal to the concrete value. 
   * @param predicate */
  public static void AbstractEqualsConcrete(boolean predicate) {}

  public static boolean compare(Object a, Object b) {
    return a.equals(b);
  }

  public static OrValue AssumeOrBegin(int b) {
    return new OrValue(b != 0);
  }

  public static OrValue AssumeOr(int b, OrValue b2) {
    return new OrValue(b != 0 || b2.boolValue());
  }

  public static void AssumeOrEnd(OrValue b) {
    if (!b.boolValue()) {
      System.out.println("Assumption (OR) failed!");
      System.exit(0);
    }
  }

  // These functions will be handled in the concolic interpreter.
  /**
   * @param intValue
   */
  public static void MakeSymbolic(int intValue) {}

  /**
   * @param longValue
   */
  public static void MakeSymbolic(long longValue) {}

  /**
   * @param charValue
   */
  public static void MakeSymbolic(char charValue) {}

  /**
   * @param byteValue
   */
  public static void MakeSymbolic(byte byteValue) {}

  /**
   * @param shortValue
   */
  public static void MakeSymbolic(short shortValue) {}

  /**
   * @param boolValue
   */
  public static void MakeSymbolic(boolean boolValue) {}

  /**
   * @param stringValue
   */
  public static void MakeSymbolic(String stringValue) {}

  public static int readInt(int x) {
    if (isInputAvailable()) {
      String input = inputs.read();
      return Integer.parseInt(input);
    } else {
      return x;
    }
  }

  public static long readLong(long x) {
    if (isInputAvailable()) {
      String input = inputs.read();
       //System.out.println(input);
      return Long.parseLong(input);
    } else {
      //System.out.println(x);
      return x;
    }
  }

  public static char readChar(char x) {
    if (isInputAvailable()) {
      String input = inputs.read();
      return (char) Integer.parseInt(input);
    } else {
      //System.out.println(x);
      return x;
    }
  }

  public static short readShort(short x) {
    if (isInputAvailable()) {
      String input = inputs.read();
      return Short.parseShort(input);
    } else {
      return x;
    }
  }

  public static byte readByte(byte x) {
    if (isInputAvailable()) {
      String input = inputs.read();
      return Byte.parseByte(input);
    } else {
      return x;
    }
  }

  public static boolean readBool(boolean x) {
    if (isInputAvailable()) {
      String input = inputs.read();
      boolean ret = Integer.parseInt(input) != 0;
      return ret;
    } else {
      return x;
    }
  }

  public static String readString(String x) {
    if (isInputAvailable()) {
      String input = inputs.read();
      return new String(input);
    } else {
      //System.out.println(x);
      return new String(x);
    }
  }

  private static State pathsState;
  private static String pathRegex;
  private static String eventPrefix = "";

  private static boolean isRealInput = true;

  public static void setRealInput(boolean isReal) {
    Writer writer = null;

    try {
      if (isRealInput) {
        writer =
            new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("isRealInput"), "utf-8"));
        writer.write("" + isReal);
        isRealInput = isReal;
      }
    } catch (IOException ex) {
    } finally {
      try {
        writer.close();
      } catch (Exception ex) {
      }
    }
  }

  public static void event(String eventName) {
    eventPrefix = eventPrefix + " " + eventName;
    int i, len = eventName.length();
    for (i = 0; i < len; i++) {
      char c = eventName.charAt(i);
      pathsState = pathsState.step(c);
      
      if (pathsState == null) {
        System.out.println(
            "Pruning path as event prefix '"
                + eventPrefix
                + "' is not in regular expression '"
                + pathRegex
                + "'");
        Main.setRealInput(false);
        System.exit(0);
      }
    }
  }

  public static void pathRegex(String regex) {
    pathRegex = regex;
    Automaton pathsAutomaton = (new RegExp(regex)).toAutomaton();
    //System.out.println(pathsAutomaton.toDot());
    pathsState = pathsAutomaton.getInitialState();
  }

  private static TreeMap<String, HashSet<Serializable>> oldStates;
  private static boolean oldStatesChanged = false;

  public static void readOldStates() {
    if (oldStates == null) {
      ObjectInputStream inputStream = null;

      try {
        inputStream = new ObjectInputStream(new FileInputStream(Config.instance.oldStates));
        Object tmp = inputStream.readObject();
        if (tmp instanceof TreeMap<?,?>) {
          oldStates = (TreeMap<String, HashSet<Serializable>>) tmp;
        } else {
          oldStates = new TreeMap<String, HashSet<Serializable>>();
        }
      } catch (Exception e) {
        oldStates = new TreeMap<String, HashSet<Serializable>>();
      } finally {
        try {
          if (inputStream != null) {
            inputStream.close();
          }
        } catch (IOException ex) {
          logger.log(Level.WARNING, "", ex);
        }
      }
    }
  }

  public static void writeOldStates() {
    if (oldStatesChanged) {
      ObjectOutputStream outputStream;
      try {
        outputStream = new ObjectOutputStream(new FileOutputStream(Config.instance.oldStates));
        outputStream.writeObject(oldStates);
        outputStream.close();
      } catch (IOException e) {
        logger.log(Level.SEVERE, "", e);
        System.exit(1);
      }
    }
  }

  public static void equivalent(String location, Serializable value) {
    if (!isInPrefix) {
      readOldStates();
      HashSet<Serializable> states = oldStates.get(location);
      if (states == null) {
        states = new HashSet<Serializable>();
        oldStates.put(location, states);
        states.add(value);
        oldStatesChanged = true;
      } else {
        if (!states.contains(value)) {
          states.add(value);
          oldStatesChanged = true;
        } else {
          System.out.println("Pruning path as equivalent state found");
          Main.setRealInput(false);
          System.exit(0);
        }
      }
    }
  }
}
