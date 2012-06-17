/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class DSUB extends Instruction {
    public DSUB(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "DSUB iid="+iid+" mid="+mid;
    }
}
