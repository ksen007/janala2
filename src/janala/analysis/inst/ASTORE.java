/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ASTORE extends Instruction {
    int var;

    public ASTORE(int iid, int mid, int var) {
        super(iid, mid);
        this.var = var;
    }

    @Override
    public String toString() {
        return "ASTORE iid="+iid+" mid="+mid+" var="+var;
    }
}
