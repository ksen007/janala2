/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LDC_double extends Instruction {
    double c;

    public LDC_double(int iid, int mid, double c) {
        super(iid, mid);
        this.c = c;
    }

    @Override
    public String toString() {
        return "LDC iid="+iid+" mid="+mid+" c="+c;
    }

}
