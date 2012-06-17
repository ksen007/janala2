/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FDIV extends Instruction {
    public FDIV(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FDIV iid="+iid+" mid="+mid;
    }
}
