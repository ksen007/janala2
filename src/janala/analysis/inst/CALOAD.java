/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class CALOAD extends Instruction {
    public CALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "CALOAD iid="+iid+" mid="+mid;
    }
}
