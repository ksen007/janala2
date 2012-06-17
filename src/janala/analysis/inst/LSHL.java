/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LSHL extends Instruction {
    public LSHL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LSHL iid="+iid+" mid="+mid;
    }
}
