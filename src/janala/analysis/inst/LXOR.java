/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LXOR extends Instruction {
    public LXOR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LXOR iid="+iid+" mid="+mid;
    }
}
