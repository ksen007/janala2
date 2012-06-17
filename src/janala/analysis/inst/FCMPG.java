/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FCMPG extends Instruction {
    public FCMPG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FCMPG iid="+iid+" mid="+mid;
    }
}
