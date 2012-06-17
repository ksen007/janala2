/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DDIV extends Instruction {
    public DDIV(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DDIV iid="+iid+" mid="+mid;
    }
}
