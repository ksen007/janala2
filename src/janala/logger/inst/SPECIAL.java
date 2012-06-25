/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/25/12
 * Time: 10:44 AM
 */
public class SPECIAL extends Instruction {
    int i;

    public SPECIAL(int i) {
        super(-1, -1);
        this.i = i;
    }

    public void visit(IVisitor visitor) {
	visitor.visitSPECIAL(this);
    }

    @Override
    public String toString() {
        return "SPECIAL i="+i;
    }
}
