/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/16/12
 * Time: 9:23 AM
 */
public class INVOKEMETHOD_EXCEPTION extends Instruction {

    public INVOKEMETHOD_EXCEPTION() {
        super(-1, -1);
    }

    public void visit(IVisitor visitor) {
	visitor.visitINVOKEMETHOD_EXCEPTION(this);
    }

    @Override
    public String toString() {
        return "INVOKEMETHOD_EXCEPTION";
    }
}
