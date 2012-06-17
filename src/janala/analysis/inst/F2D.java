/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class F2D extends Instruction {
    public F2D(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "F2D iid="+iid+" mid="+mid;
    }
}
