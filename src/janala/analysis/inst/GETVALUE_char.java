/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class GETVALUE_char extends Instruction {
    char v;

    public GETVALUE_char(char v) {
        super(-1, -1);
        this.v = v;
    }

    @Override
    public String toString() {
        return "GETVALUE_char v="+v;
    }
}
