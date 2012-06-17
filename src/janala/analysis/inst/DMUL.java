/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DMUL extends Instruction {
    public DMUL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DMUL iid="+iid+" mid="+mid;
    }
}
