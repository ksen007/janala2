/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LAND extends Instruction {
    public LAND(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LAND iid="+iid+" mid="+mid;
    }
}
