/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 12:23 PM
 */
public abstract class Constraint {
    public abstract void accept(ConstraintVisitor v);
    public abstract void not();
}
