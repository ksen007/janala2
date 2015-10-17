package janala.solvers;

import janala.config.Config;
import janala.interpreters.*;
import janala.interpreters.StringValue;
import janala.utils.MyLogger;
import janala.utils.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CVC4Solver implements Solver {
  public static enum RESULT_TYPE {
    TRUE,
    FALSE,
    UNKNOWN
  };

  public static enum CONSTRAINT_TYPE {
    INT,
    STR
  };

  public static enum SOLVER_STATUS {
    SAT,
    UNSAT,
    FAIL
  }

  private List<InputElement> inputs;
  public List<InputElement> getInputs() {
    return inputs;
  }

  private final Config config;
  private final FileUtil fileUtil;
  
  private List<String> solution;
  public List<String> getSolution() {
    return solution;
  }
  
  public CVC4Solver(Config config, FileUtil fileUtil) {
    this.config = config;
    this.fileUtil = fileUtil;
    this.solution = null;
  }

  public CVC4Solver() {
    config = Config.instance;
    fileUtil = new FileUtil();
    this.solution = null;
  }
  
  List<Constraint> constraints;
  int pathConstraintIndex;

  private final static Logger logger = MyLogger.getLogger(CVC4Solver.class.getName());
  private final static Logger tester =
      MyLogger.getTestLogger(Config.mainClass + "." + Config.iteration);

  public void setInputs(List<InputElement> inputs) {
    this.inputs = inputs;
  }

  public void setPathConstraint(List<Constraint> pathConstraint) {
    this.constraints = pathConstraint;
  }

  public void setPathConstraintIndex(int pathConstraintIndex) {
    this.pathConstraintIndex = pathConstraintIndex;
  }

  public static class Printer {
    private final Set<String> freeVars;
    private final Map<String, Long> soln;
    private final CONSTRAINT_TYPE type;
    private final PrintStream out;

    public Printer(Set<String> freeVars, Map<String, Long> sol, CONSTRAINT_TYPE type, 
      PrintStream out) {
      this.freeVars = freeVars;
      this.soln = sol;
      this.type = type;
      this.out = out;
    }

    public void printSymInt(SymbolicInt c) {
      boolean first = true;
      for (Map.Entry<Integer, Long> it : c.getLinear().entrySet()) {      
        if (first) {
          first = false;
        } else {
          out.print(" + ");
        }
        out.printf("%s*(%d)", "x" + String.valueOf(it.getKey()), it.getValue());
      }
    
      if (c.getConstant() != 0) {
        out.printf(" + (%d)", c.getConstant());
      }
      out.print(" " + c.getOp() + " ");
      out.print("0");

      for (Map.Entry<Integer, Long> it : c.getLinear().entrySet()){
        int integer = it.getKey();
        freeVars.add("x" + integer);
      }
    }

    //Visible for testing
    public void printIntCompare(SymbolicIntCompareConstraint c) {
      out.printf("(%s) - (%s) %s 0", c.left, c.right, c.op);

      if (c.left.isSym) {
        freeVars.add(c.left.getSym());
      }
      if (c.right.isSym) {
          freeVars.add(c.right.getSym());
      }
    }

    public void printOr(SymbolicOrConstraint or){
      if (or.constraints.isEmpty()) {
        out.print(" TRUE ");
      } else {
        boolean first = true;
        for (Constraint c : or.constraints) {
          if (first) {
            first = false;
          } else {
            out.print(" OR ");
          }
          out.print("(");
          print(c); // Recursion
          out.print(")");
        }
      }      
    }

    public void printAnd(SymbolicAndConstraint and) {
      if (and.constraints.isEmpty()) {
        out.print(" FALSE ");
        return;
      } 
      boolean first = true;
      for (Constraint c : and.constraints) {
        if (first) {
          first = false;
        } else {
          out.print(" AND ");
        }
        out.print("(");
        print(c);
        out.print(")");
      }      
    }

    public void printNot(SymbolicNotConstraint not) {
      out.print(" NOT ");
      out.print("(");
      print(not.getConstraint());
      out.print(")");
    }

    public void print(Constraint con) {
      if (con instanceof SymbolicInt) {
         printSymInt((SymbolicInt)con);
      } else if (con instanceof SymbolicIntCompareConstraint) {
        printIntCompare((SymbolicIntCompareConstraint)con);
      } else if (con instanceof SymbolicOrConstraint) {
        printOr((SymbolicOrConstraint) con);
      } else if (con instanceof SymbolicAndConstraint) {
        printAnd((SymbolicAndConstraint) con);
      } else if (con instanceof SymbolicNotConstraint) {
        printNot((SymbolicNotConstraint) con);
      } else if (con instanceof SymbolicTrueConstraint) {
        out.print(" TRUE ");
      } else if (con instanceof SymbolicFalseConstraint) {
        out.print(" FALSE ");
      } else if (con instanceof SymbolicStringPredicate) {
        SymbolicStringPredicate str = (SymbolicStringPredicate) con;
        Constraint intConstraint = str.getFormula(freeVars, type, soln);
        print(intConstraint);
      } else {
        throw new RuntimeException("Unimplemented constraint type " + con);
      }
    }
  } 

  private void print(Constraint con,
      PrintStream out,
      Set<String> freeVars,
      CONSTRAINT_TYPE type,
      Map<String, Long> soln) {
   
    new Printer(freeVars, soln, type, out).print(con);
  }

  public void visitSymbolicInt(SymbolicInt c) {}

  public void visitSymbolicOr(SymbolicOrConstraint c) {}

  public void visitSymbolicStringPredicate(SymbolicStringPredicate c) {}

  public void visitSymbolicAnd(SymbolicAndConstraint c) {}

  public void visitSymbolicNot(SymbolicNotConstraint c) {}

  public void visitSymbolicTrue(SymbolicTrueConstraint c) {}

  public void visitSymbolicFalse(SymbolicFalseConstraint c) {}

  public void visitSymbolicIntCompare(SymbolicIntCompareConstraint c) {}

  private boolean quickUnsatCheck(CONSTRAINT_TYPE type) {
    if (type == CONSTRAINT_TYPE.INT) {
      Constraint last = constraints.get(pathConstraintIndex);
      if (last instanceof SymbolicStringPredicate) {
        for (int i = 0; i < pathConstraintIndex; i++) {
          Constraint tmp = constraints.get(i);
          if (tmp.equals(last)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean printFormula(PrintStream out, Map<String, Long> soln, Set<String> freeVars,
    String extra, CONSTRAINT_TYPE type) {
    boolean allTrue = true;
    for (int i = 0; i < pathConstraintIndex; i++) {
      out.print("ASSERT ");
      Constraint tmp = constraints.get(i).substitute(soln);
      if (tmp != SymbolicTrueConstraint.instance) {
        allTrue = false;
      }
      print(tmp, out, freeVars, type, soln);
      out.println(";");
    }
    if (extra != null) {
      out.print("ASSERT ");
      out.print(extra);
      out.println(";");
    }

    out.print("CHECKSAT ");

      //System.out.println("Constraint "+pathConstraintIndex+": !" + constraints.get(pathConstraintIndex));
    Constraint notCon = constraints.get(pathConstraintIndex).not().substitute(soln);
    if (notCon != SymbolicTrueConstraint.instance) {
      allTrue = false;
    }
    print(notCon, out, freeVars, type, soln);
    out.println(";");
    out.println("COUNTERMODEL;");
    return allTrue;
  }

  private RESULT_TYPE writeFormula(
      String extra, CONSTRAINT_TYPE type, TreeMap<String, Long> soln) {
    try {
      PrintStream out =
          new PrintStream(
              new BufferedOutputStream(new FileOutputStream(config.formulaFile + ".tmp")));
      if (quickUnsatCheck(type)) {
        return RESULT_TYPE.FALSE;
      }

      LinkedHashSet<String> freeVars = new LinkedHashSet<String>();
      boolean allTrue = printFormula(out, soln, freeVars, extra, type);
      out.close();

      concatFile(
          freeVars, config.formulaFile + ".tmp", config.formulaFile, true);
      return allTrue ? RESULT_TYPE.TRUE : RESULT_TYPE.UNKNOWN;
    } catch (IOException ioe) {
      ioe.printStackTrace();
      logger.log(Level.SEVERE, "{0}", ioe);
      Runtime.getRuntime().halt(1);
      return RESULT_TYPE.UNKNOWN;
    }
  }

  public List<String> getSolution(Map<String, Long> soln) {
    List<String> result = new ArrayList<String>();
    for (InputElement ielem : inputs) {
      Integer sym = ielem.symbol;
      Value val = ielem.value;
      if (sym.intValue() == config.scopeBeginSymbol) {
        result.add(config.scopeBeginMarker);
      } else if (sym.intValue() == config.scopeEndSymbol) {
        result.add(config.scopeEndMarker);
      } else {
          //System.out.println("sym "+sym);
        Long l = soln.get("x" + sym);
        if (l != null) {
          result.add(l.toString());
            //System.out.println("l = " + l);
        } else {
          if (val instanceof StringValue) {
            StringValue sval = (StringValue) val;
            String old = sval.getConcrete();

            assert sval.getSymbolicExp() != null;
            
            IntValue tmp = sval.getSymbolicExp().getField("length");
            int len = (int) (long) tmp.substituteInLinear(soln);
            StringBuilder ret = new StringBuilder();
            for (int i = 0; i < len; i++) {
              Long v = soln.get("x" + sym + "__" + i);

              char c;
              if (v != null) {
                c = (char) (long) v;
              } else if (i < old.length()) {
                c = old.charAt(i);
              } else {
                c = 'a';
              }
              ret.append(c);
            }
            result.add(ret.toString());
          } else {
            result.add(val.getConcrete().toString());
          }
        }
      }
    }
    return result;
  }

  private void processResults(TreeMap<String, Long> soln) {
    solution = getSolution(soln);
  }

  public String processInputs(BufferedReader br, Map<String, Long> soln) {
    String line = null;
    String negatedSolution = null;

    try {
      if (config.printFormulaAndSolutions) {
        System.out.println("-----------Solution-------------");
      }

      line = br.readLine();
      if (config.printFormulaAndSolutions) {
        System.out.println(line);
      }
      if (!line.startsWith("sat")) {
        if (!line.contains("unsat")) {
          logger.log(Level.SEVERE, line);
          logger.log(
              Level.SEVERE,
              "Call to CVC4 failed (concolic.cvc4 = " + config.cvc4Command + ")");
          Runtime.getRuntime().halt(1);
        }
        logger.log(Level.INFO, "-- Infeasible");
        while ((line = br.readLine()) != null) {
          if (config.printFormulaAndSolutions) {
            System.out.println(line);
          }
        }
        br.close();
        return null;
      } else {
        while ((line = br.readLine()) != null) {
          if (config.printFormulaAndSolutions) {
            System.out.println(line);
          }

          String[] tokens = line.split(" ");
          if (tokens.length == 5) {
            String tmp = tokens[4].trim();
            tmp = tmp.substring(0, tmp.indexOf(";"));

            if (negatedSolution != null) {
              negatedSolution += " AND (" + tokens[0] + " = " + tmp + " )";
            } else {
              negatedSolution = "(" + tokens[0] + " = " + tmp + " )";
            }

            long val = Long.parseLong(tmp);
            soln.put(tokens[0], val);
          }
        }
        br.close();

        negatedSolution = "(NOT (" + negatedSolution + "))";
        return negatedSolution;
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
      logger.log(Level.SEVERE, "{0}", ioe);
      Runtime.getRuntime().halt(1);
      return null;
    }
  }

  public boolean solve() {
    int count = 0, MAX_COUNT = 100;
    String extra = null;
    while (count < MAX_COUNT) {
      TreeMap<String, Long> soln = new TreeMap<String, Long>();
      String negatedSolution = solve(extra, CONSTRAINT_TYPE.INT, soln);
      if (negatedSolution != null) {
        String negatedSolution2 = solve(null, CONSTRAINT_TYPE.STR, soln);
        if (negatedSolution2 != null) {
          processResults(soln);
          tester.log(Level.INFO, "Feasible = true at " + pathConstraintIndex);
          return true;
        } else {
          if (extra != null) {
            extra = extra + " AND " + negatedSolution;
          } else {
            extra = negatedSolution;
          }
        }
      } else {
        tester.log(Level.INFO, "Feasible = false at " + pathConstraintIndex);
        return false;
      }
      count++;
    }
    tester.log(Level.INFO, "Feasible = false at " + pathConstraintIndex);
    return false;
  }

  
  private String solve(String extra, CONSTRAINT_TYPE type, TreeMap<String, Long> soln) {
    try {
      RESULT_TYPE res;
      String negatedSolution;
      if ((res = writeFormula(extra, type, soln)) == RESULT_TYPE.TRUE) {
        return "";
      } else if (res == RESULT_TYPE.FALSE) {
        return null;
      }

      ProcessBuilder builder =
          new ProcessBuilder(
              new String[] {
                config.cvc4Command, "--lang", "cvc4", config.formulaFile
              });

      builder.redirectErrorStream(true);
      Process process = builder.start();

      (new StreamGobbler(process.getErrorStream(), "ERROR")).start();

      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      negatedSolution = processInputs(br, soln);
      process.waitFor();
      return negatedSolution;

    } catch (IOException ioe) {
      ioe.printStackTrace();
      logger.log(Level.SEVERE, "{0}", ioe);
      Runtime.getRuntime().halt(1);
      return null;
    } catch (InterruptedException ie) {
      ie.printStackTrace();
      logger.log(Level.SEVERE, "{0}", ie);
      Runtime.getRuntime().halt(1);
      return null;
    }
  }

  //VisbleForTest
  public void concatStreams(List<PrintStream> ps, Set<String> freeVars, String from, boolean cvc4)  throws java.io.IOException {
    for (PrintStream pw : ps) {
      if (cvc4) {
        pw.println("OPTION \"produce-models\";");
      }
      for (String var : freeVars) {
        pw.print(var);
        pw.println(" :INT;");
      }
      fileUtil.copyContent(from, pw);
    }
  }

  public void concatFile(
      LinkedHashSet<String> freeVars, String from, String to, boolean cvc4)
      throws java.io.IOException {
    List<PrintStream> ps = new ArrayList<PrintStream>();

    PrintStream toStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(to)));
    ps.add(toStream);

    if (config.printFormulaAndSolutions) {
      System.out.println("-----------Formula-------------");
      ps.add(System.out);
    }

    concatStreams(ps, freeVars, from, cvc4);
    toStream.close();
  }
}
