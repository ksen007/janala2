/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_DOUBLE extends Instruction {
    public NEWARRAY_DOUBLE(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "NEWARRAY_DOUBLE iid="+iid+" mid="+mid;
    }
}
