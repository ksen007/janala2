/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class L2D extends Instruction {
    public L2D(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "L2D iid="+iid+" mid="+mid;
    }
}
