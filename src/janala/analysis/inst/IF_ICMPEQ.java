/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IF_ICMPEQ extends Instruction {
    int label;

    public IF_ICMPEQ(int iid, int mid, int label) {
        super(iid, mid);
        this.label = label;
    }

    @Override
    public String toString() {
        return "IF_ICMPEQ iid="+iid+" mid="+mid+" label="+label;
    }
}
