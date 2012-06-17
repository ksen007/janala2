/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class I2F extends Instruction {
    public I2F(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "I2F iid="+iid+" mid="+mid;
    }
}
