/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ICONST_5 extends Instruction {
    public ICONST_5(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ICONST_5 iid="+iid+" mid="+mid;
    }
}
