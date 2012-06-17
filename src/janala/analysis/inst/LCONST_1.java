/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LCONST_1 extends Instruction {
    public LCONST_1(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LCONST_1 iid="+iid+" mid="+mid;
    }
}
