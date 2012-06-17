/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class D2L extends Instruction {
    public D2L(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "D2L iid="+iid+" mid="+mid;
    }
}
