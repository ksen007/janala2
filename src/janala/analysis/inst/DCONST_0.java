/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DCONST_0 extends Instruction {
    public DCONST_0(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DCONST_0 iid="+iid+" mid="+mid;
    }
}
