/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class MONITOREXIT extends Instruction {
    public MONITOREXIT(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "MONITOREXIT iid="+iid+" mid="+mid;
    }
}
