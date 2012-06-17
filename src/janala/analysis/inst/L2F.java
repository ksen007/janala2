/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class L2F extends Instruction {
    public L2F(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "L2F iid="+iid+" mid="+mid;
    }
}
