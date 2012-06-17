/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class JSR extends Instruction {
    int label;

    public JSR(int iid, int mid, int label) {
        super(iid, mid);
        this.label = label;
    }

    @Override
    public String toString() {
        return "JSR iid="+iid+" mid="+mid+" label="+label;
    }
}
