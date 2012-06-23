/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import janala.interpreters.PointerConstraint;
import janala.interpreters.SymbolicInt;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 4:34 PM
 */
public class ChocoSolver extends Solver {
    public void visitSymbolicInt(SymbolicInt c) {
        System.out.println(c);
    }

    public void visitPointerConstraint(PointerConstraint c) {
        System.out.println(c);
    }

    @Override
    public boolean solve() {
        return true;
    }
}
