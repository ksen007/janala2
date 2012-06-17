/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDC_float extends Instruction {
    float c;

    public LDC_float(int iid, int mid, float c) {
        super(iid, mid);
        this.c = c;
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }

}
