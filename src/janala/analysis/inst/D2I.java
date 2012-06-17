/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class D2I extends Instruction {
    public D2I(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "D2I iid="+iid+" mid="+mid;
    }
}
