/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GETVALUE_short extends Instruction {
    short v;

    public GETVALUE_short(short v) {
        super(-1, -1);
        this.v = v;
    }

    @Override
    public String toString() {
        return "GETVALUE_short v="+v;
    }
}
