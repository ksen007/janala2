/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class IOR extends Instruction {
    public IOR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "IOR iid="+iid+" mid="+mid;
    }
}
