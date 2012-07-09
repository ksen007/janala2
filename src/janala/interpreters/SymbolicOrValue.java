/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/8/12
 * Time: 1:49 PM
 */
public class SymbolicOrValue extends ObjectValue {
    public boolean concrete;
    public SymbolicOrConstraint symbolic;

    public SymbolicOrValue(boolean concrete, SymbolicOrConstraint symbolic) {
        super(100,-1);
        this.concrete = concrete;
        this.symbolic = symbolic;
    }
}
