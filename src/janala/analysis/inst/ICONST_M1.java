/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ICONST_M1 extends Instruction {
    public ICONST_M1(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ICONST_M1 iid="+iid+" mid="+mid;
    }
}
