/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class BALOAD extends Instruction {
    public BALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "BALOAD iid="+iid+" mid="+mid;
    }
}
