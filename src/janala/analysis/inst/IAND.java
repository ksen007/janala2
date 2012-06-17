/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IAND extends Instruction {
    public IAND(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IAND iid="+iid+" mid="+mid;
    }
}
