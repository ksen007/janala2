/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class SIPUSH extends Instruction {
    int value;

    public SIPUSH(int iid, int mid, int value) {
        super(iid, mid);
        this.value = value;
    }

    public void visit(IVisitor visitor) {
	visitor.visitSIPUSH(this);
    }

    @Override
    public String toString() {
        return "SIPUSH iid="+iid+" mid="+mid+" value="+value;
    }
}
