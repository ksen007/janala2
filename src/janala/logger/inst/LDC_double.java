/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

public class LDC_double extends Instruction {
    public double c;

    public LDC_double(int iid, int mid, double c) {
        super(iid, mid);
        this.c = c;
    }

    public void visit(IVisitor visitor) {
	visitor.visitLDC_double(this);
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }

}
