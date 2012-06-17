/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ATHROW extends Instruction {
    public ATHROW(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ATHROW iid="+iid+" mid="+mid;
    }
}
