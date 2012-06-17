/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class I2C extends Instruction {
    public I2C(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "I2C iid="+iid+" mid="+mid;
    }
}
