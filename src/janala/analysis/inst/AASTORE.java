/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class AASTORE extends Instruction {
    public AASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "AASTORE iid="+iid+" mid="+mid;
    }
}
