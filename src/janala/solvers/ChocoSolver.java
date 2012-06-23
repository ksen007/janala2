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
import gnu.trove.iterator.TIntLongIterator;
import janala.config.Config;
import janala.interpreters.PointerConstraint;
import janala.interpreters.SymbolicInt;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static janala.interpreters.SymbolicInt.COMPARISON_OPS;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 4:34 PM
 */
public class ChocoSolver extends Solver {
    boolean first = true;
    ArrayList<Object> inputs;
    IntegerVariable[] vars;
    CPModel m;

    @Override
    public void setInputs(ArrayList<Object> inputs) {
        this.inputs = inputs;
    }

    public void visitSymbolicInt(SymbolicInt c) {
        if (first) {
            first = false;
            this.m = new CPModel();
            int len = inputs.size();
            vars = new IntegerVariable[len];
            for(int i=0; i<len; i++) {
                vars[i] = Choco.makeIntVar("x"+i);
            }
            c.not();
        }
        System.out.println(c);
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

    public void visitPointerConstraint(PointerConstraint c) {
        System.out.println(c);
    }

    @Override
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
                        out.println(s.getVar(vars[i]).getVal());
                    }
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

}
