/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LLOAD extends Instruction {
    int var;

    public LLOAD(int iid, int mid, int var) {
        super(iid, mid);
        this.var = var;
    }

    @Override
    public String toString() {
        return "LLOAD iid="+iid+" mid="+mid+" var="+var;
    }
}
