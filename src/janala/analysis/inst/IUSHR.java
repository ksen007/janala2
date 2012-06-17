/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IUSHR extends Instruction {
    public IUSHR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IUSHR iid="+iid+" mid="+mid;
    }
}
