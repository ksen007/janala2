/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class I2S extends Instruction {
    public I2S(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "I2S iid="+iid+" mid="+mid;
    }
}
