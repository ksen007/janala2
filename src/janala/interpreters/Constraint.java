/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.io.PrintStream;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 12:23 PM
 */
public abstract class Constraint {
    public int iid;
    public int index;

    public abstract void accept(ConstraintVisitor v);
    public abstract Constraint not();
    public abstract void print(PrintStream out);
}
