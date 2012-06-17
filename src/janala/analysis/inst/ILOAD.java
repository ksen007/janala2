/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ILOAD extends Instruction {
    int var;

    public ILOAD(int iid, int mid, int var) {
        super(iid, mid);
        this.var = var;
    }

    public void visit(IVisitor visitor) {
	visitor.visitILOAD(this);
    }

    @Override
    public String toString() {
        return "ILOAD iid="+iid+" mid="+mid+" var="+var;
    }
}
