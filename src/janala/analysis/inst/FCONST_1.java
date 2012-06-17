/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FCONST_1 extends Instruction {
    public FCONST_1(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FCONST_1 iid="+iid+" mid="+mid;
    }
}
