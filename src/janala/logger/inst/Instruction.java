/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.logger.inst;

import java.io.Serializable;

public abstract class Instruction implements Serializable {
    int iid;
    int mid;

    public abstract void visit(IVisitor visitor);

    public Instruction(int iid, int mid) {
        this.iid = iid;
        this.mid = mid;
    }
}
