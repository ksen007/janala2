/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_BYTE extends Instruction {
    public NEWARRAY_BYTE(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitNEWARRAY_BYTE(this);
    }

    @Override
    public String toString() {
        return "NEWARRAY_BYTE iid="+iid+" mid="+mid;
    }
}
