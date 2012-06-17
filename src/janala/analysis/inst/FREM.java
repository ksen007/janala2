/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class FREM extends Instruction {
    public FREM(int iid, int mid) {
        super(iid, mid);
    }

    @Override
    public String toString() {
        return "FREM iid="+iid+" mid="+mid;
    }
}
