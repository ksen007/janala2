/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class NEWARRAY_LONG extends Instruction {
    public NEWARRAY_LONG(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitNEWARRAY_LONG(this);
    }

    @Override
    public String toString() {
        return "NEWARRAY_LONG iid="+iid+" mid="+mid;
    }
}
