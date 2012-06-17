/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LNEG extends Instruction {
    public LNEG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LNEG iid="+iid+" mid="+mid;
    }
}
