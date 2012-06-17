/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GETVALUE_byte extends Instruction {
    byte v;

    public GETVALUE_byte(byte v) {
        super(-1, -1);
        this.v = v;
    }

    @Override
    public String toString() {
        return "GETVALUE_byte v="+v;
    }
}
