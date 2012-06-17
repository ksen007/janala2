/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDIV extends Instruction {
    public LDIV(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LDIV iid="+iid+" mid="+mid;
    }
}
