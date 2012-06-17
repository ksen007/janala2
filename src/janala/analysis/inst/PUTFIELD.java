/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class PUTFIELD extends Instruction {
    int cIdx;
    int fIdx;
    String desc;

    public PUTFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {
        super(iid, mid);
        this.cIdx = cIdx;
        this.fIdx = fIdx;
        this.desc = desc;
    }

    public void visit(IVisitor visitor) {
	visitor.visitPUTFIELD(this);
    }

    @Override
    public String toString() {
        return "PUTFIELD iid="+iid+" mid="+mid+" cIdx="+cIdx+" fIdx="+fIdx+" desc="+desc;
    }
}
