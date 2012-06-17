/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DCMPG extends Instruction {
    public DCMPG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DCMPG iid="+iid+" mid="+mid;
    }
}
