/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ICONST_0 extends Instruction {
    public ICONST_0(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ICONST_0 iid="+iid+" mid="+mid;
    }
}
