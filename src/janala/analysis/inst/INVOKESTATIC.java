/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.analysis.inst;

public class INVOKESTATIC extends Instruction {
    String owner;
    String name;
    String desc;

    public INVOKESTATIC(int iid, int mid, String owner, String name, String desc) {
        super(iid, mid);
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "INVOKESTATIC iid="+iid+" mid="+mid+" owner="+owner+" name="+name+" desc="+desc;
    }
}
