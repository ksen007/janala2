/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LCMP extends Instruction {
    public LCMP(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LCMP iid="+iid+" mid="+mid;
    }
}
