/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class AALOAD extends Instruction {
    public AALOAD(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "AALOAD iid="+iid+" mid="+mid;
    }
}
