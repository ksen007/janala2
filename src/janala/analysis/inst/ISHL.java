/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ISHL extends Instruction {
    public ISHL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ISHL iid="+iid+" mid="+mid;
    }
}
