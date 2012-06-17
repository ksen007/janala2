/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IF_ACMPNE extends Instruction {
    int label;

    public IF_ACMPNE(int iid, int mid, int label) {
        super(iid, mid);
        this.label = label;
    }

    @Override
    public String toString() {
        return "IF_ACMPNE iid="+iid+" mid="+mid+" label="+label;
    }
}
