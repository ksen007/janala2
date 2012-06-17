/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ISHR extends Instruction {
    public ISHR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "ISHR iid="+iid+" mid="+mid;
    }
}
