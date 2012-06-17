/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class SASTORE extends Instruction {
    public SASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "SASTORE iid="+iid+" mid="+mid;
    }
}
