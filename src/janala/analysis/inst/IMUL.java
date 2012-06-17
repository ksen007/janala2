/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IMUL extends Instruction {
    public IMUL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IMUL iid="+iid+" mid="+mid;
    }
}
