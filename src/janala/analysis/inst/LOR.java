/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LOR extends Instruction {
    public LOR(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "LOR iid="+iid+" mid="+mid;
    }
}
