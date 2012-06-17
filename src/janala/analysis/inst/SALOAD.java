/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class SALOAD extends Instruction {
    public SALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "SALOAD iid="+iid+" mid="+mid;
    }
}
