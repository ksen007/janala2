/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_BOOLEAN extends Instruction {
    public NEWARRAY_BOOLEAN(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "NEWARRAY_BOOLEAN iid="+iid+" mid="+mid;
    }
}
