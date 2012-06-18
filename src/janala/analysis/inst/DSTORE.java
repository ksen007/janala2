/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DSTORE extends Instruction {
    int var;

    public DSTORE(int iid, int mid, int var) {
        super(iid, mid);
        this.var = var;
    }

    public void visit(IVisitor visitor) {
	visitor.visitDSTORE(this);
    }

    @Override
    public String toString() {
        return "DSTORE iid="+iid+" mid="+mid+" var="+var;
    }
}