/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class NEWARRAY_DOUBLE extends Instruction {
    public NEWARRAY_DOUBLE(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitNEWARRAY_DOUBLE(this);
    }

    @Override
    public String toString() {
        return "NEWARRAY_DOUBLE iid="+iid+" mid="+mid;
    }
}
