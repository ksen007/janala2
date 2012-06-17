/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ACONST_NULL extends Instruction {
    public ACONST_NULL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ACONST_NULL iid="+iid+" mid="+mid;
    }
}
