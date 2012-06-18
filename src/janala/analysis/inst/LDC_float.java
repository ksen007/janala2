/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDC_float extends Instruction {
    public float c;

    public LDC_float(int iid, int mid, float c) {
        super(iid, mid);
        this.c = c;
    }

    public void visit(IVisitor visitor) {
	visitor.visitLDC_float(this);
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }

}
