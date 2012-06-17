/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class SWAP extends Instruction {
    public SWAP(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitSWAP(this);
    }

    @Override
    public String toString() {
        return "SWAP iid="+iid+" mid="+mid;
    }
}
