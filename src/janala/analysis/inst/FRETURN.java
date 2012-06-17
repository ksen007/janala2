/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FRETURN extends Instruction {
    public FRETURN(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitFRETURN(this);
    }

    @Override
    public String toString() {
        return "FRETURN iid="+iid+" mid="+mid;
    }
}
