/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DASTORE extends Instruction {
    public DASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DASTORE iid="+iid+" mid="+mid;
    }
}
