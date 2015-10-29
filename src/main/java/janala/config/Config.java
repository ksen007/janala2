package janala.config;

import janala.logger.Logger;
import janala.solvers.Solver;
import janala.solvers.Strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
  // System properties
  public static final String mainClass = System.getProperty("janala.mainClass", null);
  public static final int iteration = Integer.getInteger("janala.iteration", 0);
  public static final String propFile = System.getProperty("janala.conf", "catg.conf");
  
  
  public static final Config instance = new Config();

  public boolean isTest;
  public boolean verbose;
  public boolean printTrace;
  public boolean printConstraints;
  public String analysisClass;
  public String traceFileName;
  public String traceAuxFileName;
  public String history;
  public String coverage;
  public String inputs;
  public String formulaFile;
  public String testLog;
  public String cvc4Command = "cvc4";
  public String[] excludeList;
  public String[] includeList;
  private String loggerClass;
  private String solver;
  private String strategy;
  public int maxStringLength;
  public int pathId;
  public boolean printFormulaAndSolutions;
  public String scopeBeginMarker;
  public String scopeEndMarker;
  public int scopeBeginSymbol = -1;
  public int scopeEndSymbol = -2;
  public String test;
  
  public String oldStates;
  public boolean printHistory;

  public Config() {
    try {
      Properties properties = new Properties();
      properties.load(new FileInputStream(propFile));

      isTest = properties.getProperty("catg.isInternalTestMode", "false").equals("true");
      verbose = properties.getProperty("catg.isVerbose", "false").equals("true");
      printHistory = properties.getProperty("catg.isPrintHistory", "false").equals("true");
      printTrace = properties.getProperty("catg.isPrintTrace", "false").equals("true");
      printConstraints = properties.getProperty("catg.isPrintConstraints", "false").equals("true");
      printFormulaAndSolutions =
          properties.getProperty("catg.isPrintFormulaAndSolutions", "false").equals("true");
      traceFileName = properties.getProperty("catg.traceFile", "trace");
      traceAuxFileName = properties.getProperty("catg.auxTraceFile", "trace.aux");
      history = properties.getProperty("catg.historyFile", "history");
      coverage = properties.getProperty("catg.coverageFile", "coverage.catg");
      inputs = properties.getProperty("catg.inputsFile", "inputs");
      
      formulaFile = properties.getProperty("catg.formulaFile", "formula");
      testLog = properties.getProperty("catg.testLogFile", "test.log");
      
      cvc4Command = properties.getProperty("catg.cvc4Command", "cvc4");
      loggerClass = System.getProperty("janala.loggerClass", "janala.logger.StringLogger");
      analysisClass =
          properties.getProperty("catg.analysisClass", "janala.logger.DJVM").replace('.', '/');
      solver = properties.getProperty("catg.solverClass", "janala.solvers.YicesSolver2");
      strategy = properties.getProperty("catg.strategyClass", "janala.solvers.DFSStrategy");
      excludeList = properties.getProperty("catg.excludeList", "").split(",");
      includeList = properties.getProperty("catg.includeList", "catg.CATG").split(",");
      maxStringLength = Integer.parseInt(properties.getProperty("catg.maxStringLength", "30"));
      pathId = Integer.parseInt(properties.getProperty("catg.pathId", "1"));
      scopeBeginMarker = properties.getProperty("catg.scopeBeginMarker", "begin$$$$");
      scopeEndMarker = properties.getProperty("catg.scopeEndMarker", "end$$$$");

      oldStates = properties.getProperty("catg.oldStatesFile", "oldStates");
      test = System.getProperty("catg.test", properties.getProperty("catg.test", "test"));
    } catch (IOException ex) {
      //ex.printStackTrace();
      // If no property file is given, set up the bare minimum
      analysisClass = "janala/logger/DJVM";
      loggerClass = "janala.logger.StringLogger";
      traceFileName = "trace.dat";
      traceAuxFileName = "trace_aux.dat";
      history = "history.dat";
    }
  }

  private Object getObject(String className) {
    try {
      Class<?> clazz = Class.forName(className);
      Object ret = clazz.newInstance();
      return ret;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (InstantiationException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  public Logger getLogger() {
    if (loggerClass == null || loggerClass.isEmpty()) {
      return null;
    }
    return (Logger) getObject(loggerClass);
  }

  public Solver getSolver() {
    if (solver == null || solver.isEmpty()) {
      return null;
    }
    return (Solver) getObject(solver);
  }

  public Strategy getStrategy() {
    if (strategy == null || strategy.isEmpty()) {
      return null;
    }
    return (Strategy) getObject(strategy);
  }
}
