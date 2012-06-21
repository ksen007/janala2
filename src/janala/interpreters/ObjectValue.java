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
    final public static ObjectValue NULL = new ObjectValue(0,0);
    Value[] concrete;
    int address; // address 0 is null, address -1 is uninitialized address

    public ObjectValue(int nFields) {
        concrete = new Value[nFields];
        address = -1;
    }

    public ObjectValue(int i, int v) {
        concrete = null;
        address = v;
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

    public Value getField(int fieldId, Frame currentFrame) {
        try {
            if (address==0) throw new NullPointerException("User NullPointerException");
            if (concrete==null) return PlaceHolder.instance;
            Value v = concrete[fieldId];
            if (v==null) return PlaceHolder.instance;
            else return v;
        } catch (Exception e) {
            System.err.println("User Exception in getField");
            e.printStackTrace();
            currentFrame.clear();
            return PlaceHolder.instance;
        }
    }

    public void setField(int fieldId, Value value, Frame currentFrame) {
        try {
            if (address==0) throw new NullPointerException("User NullPointerException");
            if (concrete!=null)
                concrete[fieldId] = value;
        } catch (Exception e) {
            System.err.println("User Exception in getField");
            e.printStackTrace();
            currentFrame.clear();
            currentFrame.push(PlaceHolder.instance);
        }
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
