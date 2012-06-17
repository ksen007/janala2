/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/16/12
 * Time: 9:22 AM
 */
public class GETVALUE_void extends Instruction {

    public GETVALUE_void() {
        super(-1, -1);
    }

    @Override
    public String toString() {
        return "GETVALUE_void";
    }
}
