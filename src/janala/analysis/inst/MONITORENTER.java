/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class MONITORENTER extends Instruction {
    public MONITORENTER(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "MONITORENTER iid="+iid+" mid="+mid;
    }
}
