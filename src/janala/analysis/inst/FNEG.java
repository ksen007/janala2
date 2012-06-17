/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FNEG extends Instruction {
    public FNEG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FNEG iid="+iid+" mid="+mid;
    }
}
