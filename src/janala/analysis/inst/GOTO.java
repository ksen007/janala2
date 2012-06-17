/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GOTO extends Instruction {
    int label;

    public GOTO(int iid, int mid, int label) {
        super(iid, mid);
        this.label = label;
    }

    @Override
    public String toString() {
        return "GOTO iid="+iid+" mid="+mid+" label="+label;
    }
}
