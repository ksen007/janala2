/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDC_int extends Instruction {
    int c;

    public LDC_int(int iid, int mid, int c) {
        super(iid, mid);
        this.c = c;
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }
}
