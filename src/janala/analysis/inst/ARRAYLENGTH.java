/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ARRAYLENGTH extends Instruction {
    public ARRAYLENGTH(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ARRAYLENGTH iid="+iid+" mid="+mid;
    }
}
