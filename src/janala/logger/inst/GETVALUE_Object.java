/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class GETVALUE_Object extends Instruction {
    int v;

    public GETVALUE_Object(int v) {
        super(-1, -1);
        this.v = v;
    }

    public void visit(IVisitor visitor) {
	visitor.visitGETVALUE_Object(this);
    }

    @Override
    public String toString() {
        return "GETVALUE_Object v="+v;
    }
}
