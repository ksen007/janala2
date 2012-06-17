/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IREM extends Instruction {
    public IREM(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IREM iid="+iid+" mid="+mid;
    }
}
