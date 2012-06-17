/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ARETURN extends Instruction {
    public ARETURN(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ARETURN iid="+iid+" mid="+mid;
    }
}
