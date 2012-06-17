/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GETVALUE_long extends Instruction {
    long v;

    public GETVALUE_long(long v) {
        super(-1, -1);
        this.v = v;
    }

    public void visit(IVisitor visitor) {
	visitor.visitGETVALUE_long(this);
    }

    @Override
    public String toString() {
        return "GETVALUE_long v="+v;
    }
}
