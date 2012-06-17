/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DADD extends Instruction {
    public DADD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DADD iid="+iid+" mid="+mid;
    }
}
