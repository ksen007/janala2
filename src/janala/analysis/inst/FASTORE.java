/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FASTORE extends Instruction {
    public FASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FASTORE iid="+iid+" mid="+mid;
    }
}
