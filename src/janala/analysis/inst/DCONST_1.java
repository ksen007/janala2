/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DCONST_1 extends Instruction {
    public DCONST_1(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DCONST_1 iid="+iid+" mid="+mid;
    }
}
