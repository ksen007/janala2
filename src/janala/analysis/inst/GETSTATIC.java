/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GETSTATIC extends Instruction {
    int cIdx;
    int fIdx;
    String desc;

    public GETSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {
        super(iid, mid);
        this.cIdx = cIdx;
        this.fIdx = fIdx;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GETSTATIC iid="+iid+" mid="+mid+" cIdx="+cIdx+" fIdx="+fIdx+" desc="+desc;
    }
}
