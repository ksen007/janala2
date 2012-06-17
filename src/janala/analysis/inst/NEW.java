/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class NEW extends Instruction {
    String type;
    int cIdx;

    public NEW(int iid, int mid, String type, int cIdx) {
        super(iid, mid);
        this.type = type;
        this.cIdx = cIdx;
    }

    @Override
    public String toString() {
        return "NEW iid="+iid+" mid="+mid+" cIdx="+cIdx;
    }
}
