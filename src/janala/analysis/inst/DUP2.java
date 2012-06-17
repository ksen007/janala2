/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DUP2 extends Instruction {
    public DUP2(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DUP2 iid="+iid+" mid="+mid;
    }
}
