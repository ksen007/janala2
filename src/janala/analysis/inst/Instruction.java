/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

import java.io.Serializable;

public class Instruction implements Serializable {
    int iid;
    int mid;

    public Instruction(int iid, int mid) {
        this.iid = iid;
        this.mid = mid;
    }
}
