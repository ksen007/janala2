/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class NEWARRAY_INT extends Instruction {
    public NEWARRAY_INT(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitNEWARRAY_INT(this);
    }

    @Override
    public String toString() {
        return "NEWARRAY_INT iid="+iid+" mid="+mid;
    }
}
