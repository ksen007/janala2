/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_FLOAT extends Instruction {
    public NEWARRAY_FLOAT(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "NEWARRAY_FLOAT iid="+iid+" mid="+mid;
    }
}
