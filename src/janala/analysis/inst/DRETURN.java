/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DRETURN extends Instruction {
    public DRETURN(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DRETURN iid="+iid+" mid="+mid;
    }
}
