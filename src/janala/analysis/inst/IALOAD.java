/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IALOAD extends Instruction {
    public IALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IALOAD iid="+iid+" mid="+mid;
    }
}
