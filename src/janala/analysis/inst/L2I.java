/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class L2I extends Instruction {
    public L2I(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "L2I iid="+iid+" mid="+mid;
    }
}
