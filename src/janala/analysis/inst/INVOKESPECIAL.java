/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class INVOKESPECIAL extends Instruction {
    String owner;
    String name;
    public String desc;

    public INVOKESPECIAL(int iid, int mid, String owner, String name, String desc) {
        super(iid, mid);
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public void visit(IVisitor visitor) {
	visitor.visitINVOKESPECIAL(this);
    }

    @Override
    public String toString() {
        return "INVOKESPECIAL iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc;
    }
}