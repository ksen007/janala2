/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class CASTORE extends Instruction {
    public CASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "CASTORE iid="+iid+" mid="+mid;
    }
}
