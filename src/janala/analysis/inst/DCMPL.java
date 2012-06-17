/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DCMPL extends Instruction {
    public DCMPL(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DCMPL iid="+iid+" mid="+mid;
    }
}
