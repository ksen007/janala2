/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class NEWARRAY_BOOLEAN extends Instruction {
    public NEWARRAY_BOOLEAN(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitNEWARRAY_BOOLEAN(this);
    }

    @Override
    public String toString() {
        return "NEWARRAY_BOOLEAN iid="+iid+" mid="+mid;
    }
}
