/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class I2D extends Instruction {
    public I2D(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "I2D iid="+iid+" mid="+mid;
    }
}
