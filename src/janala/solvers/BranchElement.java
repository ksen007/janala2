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

    public BranchElement(boolean branch, boolean done, int pathConstraintIndex) {
        this.branch = branch;
        this.done = done;
        this.pathConstraintIndex = pathConstraintIndex;
    }
}
