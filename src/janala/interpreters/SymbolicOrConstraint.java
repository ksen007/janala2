/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/8/12
 * Time: 1:46 PM
 */
public class SymbolicOrConstraint extends Constraint {
    LinkedList<Constraint> constraints;
    boolean isNegated;

    public SymbolicOrConstraint(Constraint c) {
        isNegated = false;
        constraints = new LinkedList<Constraint>();
        if (c!=null)
            constraints.add(c);
    }

    private SymbolicOrConstraint(SymbolicOrConstraint c) {
        isNegated = c.isNegated;
        constraints = new LinkedList<Constraint>();
        constraints.addAll(c.constraints);
    }

    public SymbolicOrConstraint OR(Constraint c) {
        if (c!=null) {
            SymbolicOrConstraint ret = new SymbolicOrConstraint(this);
            ret.constraints.add(c);
            return ret;
        } else {
            return this;
        }
    }

    @Override
    public void accept(ConstraintVisitor v) {
        v.visitSymbolicOr(this);
    }

    @Override
    public Constraint not() {
        SymbolicOrConstraint ret = new SymbolicOrConstraint(this);
        ret.isNegated = !ret.isNegated;
        return ret;
    }

    @Override
    public void print(PrintStream out) {
        if (isNegated) {
            out.print("(not (or ");
        } else {
            out.print("(or ");
        }
        for(Constraint c:constraints) {
            c.print(out);
            out.print(" ");
        }
        if (isNegated) {
            out.print("))");
        } else {
            out.print(")");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isNegated) {
            sb.append("!(");
        }
        boolean first = true;
        for(Constraint c:constraints) {
            if (first) {
                first = false;
            } else {
                sb.append(" || ");
            }
            sb.append("(");
            sb.append(c);
            sb.append(")");
        }
        if (isNegated) {
            sb.append(")");
        }
        return sb.toString();
    }
}
