/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FMUL extends Instruction {
    public FMUL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FMUL iid="+iid+" mid="+mid;
    }
}
