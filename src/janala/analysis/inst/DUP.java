/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DUP extends Instruction {
    public DUP(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DUP iid="+iid+" mid="+mid;
    }
}
