/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class F2I extends Instruction {
    public F2I(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "F2I iid="+iid+" mid="+mid;
    }
}
