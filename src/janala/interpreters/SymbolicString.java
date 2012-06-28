/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.io.PrintStream;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/27/12
 * Time: 8:07 PM
 */
public class SymbolicString extends Constraint {
    int symbol;
    String constant;
    boolean isEqual;

    public SymbolicString(int symbol) {
        this.symbol = symbol;
    }

    public SymbolicString(SymbolicString ss) {
        symbol = ss.symbol;
        constant = ss.constant;
        isEqual = ss.isEqual;
    }

    public SymbolicString EQ(String s) {
        SymbolicString ret = new SymbolicString(symbol);
        ret.constant = s;
        ret.isEqual = true;
        return ret;
    }

    public SymbolicString NE(String s) {
        SymbolicString ret = EQ(s);
        ret.isEqual = false;
        return ret;
    }
    
    @Override
    public void accept(ConstraintVisitor v) {
        v.visitSymboliString(this);
    }

    @Override
    public void not() {
        isEqual = !isEqual;
    }

    @Override
    public void print(PrintStream out) {
    }
}
