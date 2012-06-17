/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IDIV extends Instruction {
    public IDIV(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IDIV iid="+iid+" mid="+mid;
    }
}
