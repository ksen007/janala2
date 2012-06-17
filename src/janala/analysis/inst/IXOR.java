/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IXOR extends Instruction {
    public IXOR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IXOR iid="+iid+" mid="+mid;
    }
}
