/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LUSHR extends Instruction {
    public LUSHR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LUSHR iid="+iid+" mid="+mid;
    }
}
