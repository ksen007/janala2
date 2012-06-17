/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IASTORE extends Instruction {
    public IASTORE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IASTORE iid="+iid+" mid="+mid;
    }
}
