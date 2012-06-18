/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IFGE extends Instruction {
    int label;

    public IFGE(int iid, int mid, int label) {
        super(iid, mid);
        this.label = label;
    }

    public void visit(IVisitor visitor) {
	visitor.visitIFGE(this);
    }

    @Override
    public String toString() {
        return "IFGE iid="+iid+" mid="+mid+" label="+label;
    }
}