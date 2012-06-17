/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NOP extends Instruction {
    public NOP(int iid, int mid) {
        super(iid, mid);
    }

    public void visit(IVisitor visitor) {
	visitor.visitNOP(this);
    }

    @Override
    public String toString() {
        return "NOP iid="+iid+" mid="+mid;
    }
}
