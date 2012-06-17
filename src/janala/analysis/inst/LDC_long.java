/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDC_long extends Instruction {
    long c;

    public LDC_long(int iid, int mid, long c) {
        super(iid, mid);
        this.c = c;
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }
}
