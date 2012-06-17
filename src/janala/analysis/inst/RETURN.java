/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class RETURN extends Instruction {
    public RETURN(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "RETURN iid="+iid+" mid="+mid;
    }
}
