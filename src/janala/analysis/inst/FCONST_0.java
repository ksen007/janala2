/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FCONST_0 extends Instruction {
    public FCONST_0(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FCONST_0 iid="+iid+" mid="+mid;
    }
}
