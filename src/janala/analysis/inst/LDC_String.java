/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDC_String extends Instruction {
    String c;

    public LDC_String(int iid, int mid, String c) {
        super(iid, mid);
        this.c = c;
    }

    public void visit(IVisitor visitor) {
	visitor.visitLDC_String(this);
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }
}
