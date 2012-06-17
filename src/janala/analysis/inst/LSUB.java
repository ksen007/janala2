/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LSUB extends Instruction {
    public LSUB(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitLSUB(this);
    }

    @Override
    public String toString() {
        return "LSUB iid="+iid+" mid="+mid;
    }
}
