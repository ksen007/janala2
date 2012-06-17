/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IF_ICMPGE extends Instruction {
    int label;

    public IF_ICMPGE(int iid, int mid, int label) {
        super(iid, mid);
        this.label = label;
    }

    @Override
    public String toString() {
        return "IF_ICMPGE iid="+iid+" mid="+mid+" label="+label;
    }
}
