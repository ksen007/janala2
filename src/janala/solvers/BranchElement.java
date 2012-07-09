/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 2:28 PM
 */
public class BranchElement extends Element {
    boolean branch;
    boolean done;
    int pathConstraintIndex; // -1 for no index
    int iid;
    public boolean ignore;

    public BranchElement(boolean branch, boolean done, int pathConstraintIndex, int iid) {
        this.branch = branch;
        this.done = done;
        this.pathConstraintIndex = pathConstraintIndex;
        this.iid = iid;
        this.ignore = false;
    }

    @Override
    public String toString() {
        return "BranchElement{" +
                "branch=" + branch +
                ", done=" + done +
                ", pathConstraintIndex=" + pathConstraintIndex +
                ", iid=" + iid +
                '}';
    }
}
