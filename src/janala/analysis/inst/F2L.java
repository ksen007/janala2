/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class F2L extends Instruction {
    public F2L(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "F2L iid="+iid+" mid="+mid;
    }
}
