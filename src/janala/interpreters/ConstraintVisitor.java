/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 4:31 PM
 */
public interface ConstraintVisitor {
    void visitSymbolicInt(SymbolicInt c);
    void visitSymbolicOr(SymbolicOrConstraint c);
}
