/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class CHECKCAST extends Instruction {
    String type;

    public CHECKCAST(int iid, int mid, String type) {
        super(iid, mid);
        this.type = type;
    }

    @Override
    public String toString() {
        return "CHECKCAST iid="+iid+" mid="+mid+" type="+type;
    }
}
