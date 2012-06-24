/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import janala.interpreters.ConstraintVisitor;
import janala.interpreters.Value;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 4:42 PM
 */
public interface Solver extends ConstraintVisitor {
    public boolean solve();
    public void setInputs(ArrayList<Value> inputs);
}
