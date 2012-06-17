/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class INSTANCEOF extends Instruction {
    String type;

    public INSTANCEOF(int iid, int mid, String type) {
        super(iid, mid);
        this.type = type;
    }

    @Override
    public String toString() {
        return "INSTANCEOF iid="+iid+" mid="+mid+" type="+type;
    }
}
