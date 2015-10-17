package janala.solvers;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.constraints.LogicalConstraintFactory;
import org.chocosolver.util.ESat;

import janala.config.Config;
import janala.interpreters.*;
import janala.utils.MyLogger;
import janala.utils.Inputs;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import janala.interpreters.COMPARISON_OPS;

/** A solver that uses Choco as backend. */
public class ChocoSolver implements janala.solvers.Solver {
  boolean first = true;
  List<InputElement> inputs;
  List<String> solution;
  IntVar[] vars;
  Solver solver; 
  private final static Logger logger = MyLogger.getLogger(ChocoSolver.class.getName());

  public void setInputs(List<InputElement> inputs) {
    this.inputs = inputs;
    this.first = true;
    solution = null;
  }

  public void setPathConstraint(List<Constraint> pathConstraint) {
  }

  public void setPathConstraintIndex(int pathConstraintIndex) {  
  }

  private SymbolicInt initSolver(SymbolicInt c) {
    if (first) {
      first = false;
      solver = new Solver();
      int len = inputs.size();
      vars = new IntVar[len];
      for (int i = 0; i < len; i++) {
        vars[i] = VariableFactory.integer("x" + i, 
            VariableFactory.MIN_INT_BOUND, VariableFactory.MAX_INT_BOUND, solver);
      }
      return c.not();
    }
    return c;
  }

  public void visitSymbolicInt(SymbolicInt c) {
    SymbolicInt tmp = initSolver(c);
    solver.post(createConstraintForSymbolicInt(tmp));
  }

  public void visitSymbolicIntCompare(SymbolicIntCompareConstraint c) {
    throw new RuntimeException("Unimplemented feature");
  }

  private org.chocosolver.solver.constraints.Constraint createConstraintForSymbolicInt(SymbolicInt c) {
    logger.log(Level.INFO, "{0}", c);
    
    int n = c.getLinear().size();
    IntVar[] variables = new IntVar[n];
    int[] coeff = new int[n];
    
    int idx = 0;
    for (Map.Entry<Integer, Long> it : c.getLinear().entrySet()) {
      int varIdx = it.getKey() - 1;
      variables[idx] = vars[varIdx]; 
      coeff[idx] = it.getValue().intValue();
      idx++;
    }
    // Create a fixed variable for the right-hand side
    IntVar rhs = VariableFactory.fixed(-(int) c.getConstant(), solver);
    String op = c.getOp().toString();
    
    return IntConstraintFactory.scalar(variables, coeff, op, rhs);
  }

  public void visitSymbolicOr(SymbolicOrConstraint or) {
    org.chocosolver.solver.constraints.Constraint[] con = 
        new org.chocosolver.solver.constraints.Constraint[or.constraints.size()];
    int idx = 0;
    for (Constraint c : or.constraints) {
      SymbolicInt tmp = initSolver((SymbolicInt)c);
      con[idx] = createConstraintForSymbolicInt(tmp);
      idx++;
    }
    
    solver.post(LogicalConstraintFactory.or(con));
  }

  public void visitSymbolicStringPredicate(SymbolicStringPredicate symbolicStringPredicate) {
    throw new RuntimeException(
        "String functions and regular expressions are not supported with Choco solver");
  }

  public void visitSymbolicAnd(SymbolicAndConstraint c) {
    throw new RuntimeException("Unsupported");
  }

  public void visitSymbolicNot(SymbolicNotConstraint c) {
    throw new RuntimeException("Unsupported");
  }

  public void visitSymbolicTrue(SymbolicTrueConstraint c) {
    throw new RuntimeException("Unsupported");
  }

  public void visitSymbolicFalse(SymbolicFalseConstraint c) {
    throw new RuntimeException("Unsupported");
  }

  public boolean solve() {
    if (solver != null) {
      System.out.println("Running choco solver ...");
      logger.log(Level.INFO, "Running Choco Solver ...");
      solver.findSolution();
      logger.log(Level.INFO, "end running Choco Solver ");

      if (solver.isFeasible() == ESat.TRUE) {
        solution = new ArrayList<String>();

        for (int i = 0; i < vars.length; i++) {
          int var = vars[i].getValue();

          Value input = inputs.get(i).value;
          if (input instanceof janala.interpreters.StringValue) {
            solution.add(StringConstants.instance.get(var));
          } else {
            solution.add(Integer.toString(var));
          }
        }
        return true;
      } else {
        logger.log(Level.INFO, "-- Infeasible");
        return false;
      }
    }
    return false;
  }
  
  public List<String> getSolution() {
    return solution;
  }
}
