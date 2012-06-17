/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LREM extends Instruction {
    public LREM(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LREM iid="+iid+" mid="+mid;
    }
}
