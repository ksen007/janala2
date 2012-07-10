/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/9/12
 * Time: 7:14 PM
 */
public abstract class Strategy {
    abstract public int solve(ArrayList<BranchElement> history, int historySize, History solver);
}
