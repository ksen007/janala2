/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.io.PrintStream;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 5:59 PM
 */
public class PointerConstraint extends Constraint {
    public int first;
    public int second;
    public boolean isEqual;

    public PointerConstraint(int first, int second, boolean equal) {
        this.first = first;
        this.second = second;
        isEqual = equal;
    }

    public void not() {
        isEqual = !isEqual;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (first!=-1 || second!=-1)
            sb.append("p").append(first==0?"null":first).append(isEqual?"==":"!=").append("p").append(second==0?"null":second);
        else
            sb.append("null");
        return sb.toString();
    }

    @Override
    public void accept(ConstraintVisitor v) {
        v.visitPointerConstraint(this);
    }

    public void print(PrintStream out) {
        out.print("(");
        if (isEqual) {
            out.print("==");
        } else {
            out.print("!=");
        }
        if (first>0) {
            out.print('p');
        }
        out.print(first);
        if (second>0) {
            out.print('p');
        }
        out.print(second);
        out.print(")");
    }

}
