/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IADD extends Instruction {
    public IADD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IADD iid="+iid+" mid="+mid;
    }
}
