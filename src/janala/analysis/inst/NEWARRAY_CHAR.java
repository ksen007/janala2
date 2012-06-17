/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_CHAR extends Instruction {
    public NEWARRAY_CHAR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "NEWARRAY_CHAR iid="+iid+" mid="+mid;
    }
}
