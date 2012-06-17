/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FSUB extends Instruction {
    public FSUB(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FSUB iid="+iid+" mid="+mid;
    }
}
