/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FCMPL extends Instruction {
    public FCMPL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FCMPL iid="+iid+" mid="+mid;
    }
}
