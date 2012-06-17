/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class TABLESWITCH extends Instruction {
    int min;
    int max;
    int dflt;
    int[] labels;

    public TABLESWITCH(int iid, int mid, int min, int max, int dflt, int[] labels) {
        super(iid, mid);
        this.min = min;
        this.max = max;
        this.dflt = dflt;
        this.labels = labels;
    }

    public void visit(IVisitor visitor) {
	visitor.visitTABLESWITCH(this);
    }

    @Override
    public String toString() {
        return "TABLESWITCH iid="+iid+" mid="+mid+" min="+min+" max="+max+" dflt="+dflt+" labels="+labels;
    }
}
