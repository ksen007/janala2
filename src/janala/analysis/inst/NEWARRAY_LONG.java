/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_LONG extends Instruction {
    public NEWARRAY_LONG(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "NEWARRAY_LONG iid="+iid+" mid="+mid;
    }
}
