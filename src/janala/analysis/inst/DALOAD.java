/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DALOAD extends Instruction {
    public DALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DALOAD iid="+iid+" mid="+mid;
    }
}
