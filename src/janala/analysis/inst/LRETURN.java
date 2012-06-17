/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LRETURN extends Instruction {
    public LRETURN(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LRETURN iid="+iid+" mid="+mid;
    }
}
