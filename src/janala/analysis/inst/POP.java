/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class POP extends Instruction {
    public POP(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "POP iid="+iid+" mid="+mid;
    }
}
