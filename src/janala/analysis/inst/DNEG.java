/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DNEG extends Instruction {
    public DNEG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DNEG iid="+iid+" mid="+mid;
    }
}
