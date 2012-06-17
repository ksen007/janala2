/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class ANEWARRAY extends Instruction {
    String type;

    public ANEWARRAY(int iid, int mid, String type) {
        super(iid, mid);
        this.type = type;
    }

    @Override
    public String toString() {
        return "ANEWARRAY iid="+iid+" mid="+mid+" type="+type;
    }
}
