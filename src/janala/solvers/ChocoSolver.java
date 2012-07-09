/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.variables.integer.IntDomainVar;
import gnu.trove.iterator.TIntLongIterator;
import janala.config.Config;
import janala.interpreters.*;
import janala.utils.MyLogger;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static janala.interpreters.SymbolicInt.COMPARISON_OPS;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 4:34 PM
 */
public class ChocoSolver implements Solver {
    boolean first = true;
    ArrayList<Value> inputs;
    IntegerVariable[] vars;
    CPModel m;
    private final static Logger logger = MyLogger.getLogger(ChocoSolver.class.getName());


    public void setInputs(ArrayList<Value> inputs) {
        this.inputs = inputs;
        this.first = true;
    }

    private Constraint initSolver(Constraint c) {
        if (first) {
            first = false;
            this.m = new CPModel();
            int len = inputs.size();
            vars = new IntegerVariable[len];
            for(int i=0; i<len; i++) {
                vars[i] = Choco.makeIntVar("x"+i);
                //m.addVariable(vars[i]);
            }
            return c.not();
        }
        return c;
    }

    public void visitSymbolicInt(SymbolicInt c) {
        c = (SymbolicInt)initSolver(c);
        logger.log(Level.INFO,"{0}",c);
        boolean first2 = true;
        IntegerExpressionVariable old = null;
        for ( TIntLongIterator it = c.linear.iterator(); it.hasNext(); ) {
            it.advance();

            int integer = it.key();
            long l = it.value();
            if (first2) {
                first2 = false;
                old = Choco.mult((int)l,vars[integer-1]);
            } else {
                old = Choco.sum(old,Choco.mult((int)l,vars[integer-1]));
            }
        }
        if (c.constant != 0) {
            old = Choco.sum(new IntegerConstantVariable((int)c.constant),old);
        }
        if (c.op == COMPARISON_OPS.EQ) {
            m.addConstraint(Choco.eq(old, 0));
        } else if (c.op == COMPARISON_OPS.NE) {
            m.addConstraint(Choco.not(Choco.eq(old, 0)));
        } else if (c.op == COMPARISON_OPS.LE) {
            m.addConstraint(Choco.leq(old, 0));
        } else if (c.op == COMPARISON_OPS.LT) {
            m.addConstraint(Choco.lt(old, 0));
        } else if (c.op == COMPARISON_OPS.GE) {
            m.addConstraint(Choco.geq(old, 0));
        } else if (c.op == COMPARISON_OPS.GT) {
            m.addConstraint(Choco.gt(old, 0));
        }

    }

    public void visitSymbolicOr(SymbolicOrConstraint c) {
        throw new RuntimeException("Unimplemented feature");
    }


//    public void visitPointerConstraint(PointerConstraint c) {
//        initSolver(c);
//        System.out.println(c);
//        if (c.first>0 && c.second>0) {
//            m.addConstraint(c.isEqual?Choco.eq(vars[c.first-1],vars[c.second-1]):Choco.not(Choco.eq(vars[c.first-1],vars[c.second-1])));
//        } else if (c.first>0) {
//            m.addConstraint(c.isEqual?Choco.eq(vars[c.first-1],c.second):Choco.not(Choco.eq(vars[c.first-1],c.second)));
//        } else if (c.second>0) {
//            m.addConstraint(c.isEqual?Choco.eq(vars[c.second-1],c.first):Choco.not(Choco.eq(vars[c.second-1],c.first)));
//        }
//    }

    public boolean solve() {
        if (m!=null) {
            CPSolver s = new CPSolver();
            s.read(m);
            s.solve();
            if (s.isFeasible()) {
                try {
                    PrintStream out = new PrintStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(Config.inputs)));
                    for(int i=0; i<vars.length; i++) {
                        IntDomainVar var = s.getVar(vars[i]);

                        Value input = inputs.get(i);
                        if (var!=null) {
                            if (input instanceof janala.interpreters.StringValue) {
                                out.println(StringConstants.instance.get(var.getVal()));
                            } else {
                                out.println(var.getVal());
                            }
                        } else {
                            out.println(input.getConcrete());
                        }
                    }
                    out.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE,"",e);
                    System.exit(1);
                }
                return true;
            } else {
                logger.log(Level.INFO,"-- Infeasible");
                return false;
            }

        }
        return false;
    }

}
