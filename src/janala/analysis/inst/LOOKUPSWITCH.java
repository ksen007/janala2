/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class LOOKUPSWITCH extends Instruction{
    int dflt;
    int[] keys;
    int[] labels;

    public LOOKUPSWITCH(int iid, int mid, int dflt, int[] keys, int[] labels) {
        super(iid, mid);
        this.dflt = dflt;
        this.keys = keys;
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "LOOKUPSWITCH iid="+iid+" mid="+mid+" dflt="+dflt+" keys="+keys+" labels="+labels;
    }
}
