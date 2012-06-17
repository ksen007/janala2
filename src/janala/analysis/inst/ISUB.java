/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ISUB extends Instruction {
    public ISUB(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ISUB iid="+iid+" mid="+mid;
    }
}
