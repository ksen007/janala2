/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEWARRAY_SHORT extends Instruction {
    public NEWARRAY_SHORT(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "NEWARRAY_SHORT iid="+iid+" mid="+mid;
    }
}
