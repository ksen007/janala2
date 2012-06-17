/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FADD extends Instruction {
    public FADD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FADD iid="+iid+" mid="+mid;
    }
}
