/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GETVALUE_float extends Instruction {
    float v;

    public GETVALUE_float(float v) {
        super(-1, -1);
        this.v = v;
    }

    @Override
    public String toString() {
        return "GETVALUE_float v="+v;
    }
}
