/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LSHR extends Instruction {
    public LSHR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LSHR iid="+iid+" mid="+mid;
    }
}
