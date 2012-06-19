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
    final public static ObjectValue NULL = new ObjectValue(0);
    private Value[] concrete;

    public ObjectValue(int nFields) {
        concrete = new Value[nFields];
    }

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

    public Value getField(int fieldId) {
        return concrete[fieldId];
    }

    public void setField(int fieldId, Value value) {
        concrete[fieldId] = value;
    }
}
