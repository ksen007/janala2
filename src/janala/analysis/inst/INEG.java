/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class INEG extends Instruction {
    public INEG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "INEG iid="+iid+" mid="+mid;
    }
}
