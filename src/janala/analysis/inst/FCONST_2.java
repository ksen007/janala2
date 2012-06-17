/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FCONST_2 extends Instruction {
    public FCONST_2(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FCONST_2 iid="+iid+" mid="+mid;
    }
}
