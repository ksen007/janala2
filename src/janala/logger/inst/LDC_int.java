/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class LDC_int extends Instruction {
    public int c;

    public LDC_int(int iid, int mid, int c) {
        super(iid, mid);
        this.c = c;
    }

    public void visit(IVisitor visitor) {
	visitor.visitLDC_int(this);
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }
}
