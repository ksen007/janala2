/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LMUL extends Instruction {
    public LMUL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LMUL iid="+iid+" mid="+mid;
    }
}
