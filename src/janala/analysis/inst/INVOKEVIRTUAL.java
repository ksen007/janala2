/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class INVOKEVIRTUAL extends Instruction {
    String owner;
    String name;
    String desc;

    public INVOKEVIRTUAL(int iid, int mid, String owner, String name, String desc) {
        super(iid, mid);
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public void visit(IVisitor visitor) {
	visitor.visitINVOKEVIRTUAL(this);
    }

    @Override
    public String toString() {
        return "INVOKEVIRTUAL iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc;
    }
}
