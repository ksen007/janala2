/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LALOAD extends Instruction {
    public LALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LALOAD iid="+iid+" mid="+mid;
    }
}
