/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 8:58 AM
 */
public class ObjectValue extends Value {
    final public static ObjectValue NULL = new ObjectValue();
    private Value[] concrete;

    public boolean IF_ACMPEQ(ObjectValue o2) {
        return (this==o2);
    }

    public boolean IF_ACMPNE(ObjectValue o2) {
        return (this!=o2);
    }

    public boolean IFNULL() {
        return (this==NULL);
    }

    public boolean IFNONNULL() {
        return (this!=NULL);
    }

    public void GETSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {

    }

    public void PUTSTATIC(int iid, int mid, int cIdx, int fIdx, String desc) {

    }

    public void GETFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {

    }

    public void PUTFIELD(int iid, int mid, int cIdx, int fIdx, String desc) {

    }

}
