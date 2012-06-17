/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LASTORE extends Instruction {
    public LASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LASTORE iid="+iid+" mid="+mid;
    }
}
