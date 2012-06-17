/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class I2L extends Instruction {
    public I2L(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "I2L iid="+iid+" mid="+mid;
    }
}
