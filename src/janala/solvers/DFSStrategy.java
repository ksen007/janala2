/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/9/12
 * Time: 7:21 PM
 */
public class DFSStrategy extends Strategy {
    @Override
    public int solve(ArrayList<BranchElement> history, int historySize, History solver) {
        for (int i=historySize-1; i>=0; i--) {
            BranchElement current = history.get(i);
            if (!current.done && current.pathConstraintIndex != -1) {
                if (solver.solveAt(current.pathConstraintIndex)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
