/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import janala.interpreters.ConstraintVisitor;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 4:42 PM
 */
public abstract class Solver implements ConstraintVisitor {
    public abstract boolean solve();
    public abstract void setInputs(ArrayList<Object> inputs);
}
