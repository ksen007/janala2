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
    int symbolic; // if symbolic then >0, otherwise 0 for null and -1 for non-null
    int address; // address 0 is null, address -1 is uninitialized address

    @Override
    public Object getConcrete() {
        return address;
    }

    public ObjectValue(int nFields) {
        concrete = new Value[nFields];
        address = -1;
        symbolic = -1;
    }

    public ObjectValue(int i, int v) {
        concrete = null;
        address = v;
        symbolic = (address==0?0:-1);
    }

    public void MAKE_SYMBOLIC(int symbol) {
        symbolic = symbol;
    }


    public ConstraintAndResult IF_ACMPEQ(ObjectValue o2) {
        boolean result = this==o2;
        if (symbolic>0 || o2.symbolic>0) {
            return new ConstraintAndResult(new PointerConstraint(symbolic,o2.symbolic,result),result);
        } else {
            return new ConstraintAndResult(null,result);
        }
    }

    public ConstraintAndResult IF_ACMPNE(ObjectValue o2) {
        boolean result = this!=o2;
        if (symbolic>0 || o2.symbolic>0) {
            return new ConstraintAndResult(new PointerConstraint(symbolic,o2.symbolic,!result),result);
        } else {
            return new ConstraintAndResult(null,result);
        }
    }

    public ConstraintAndResult IFNULL() {
        boolean result = this.address==0;
        if (symbolic>0) {
            return new ConstraintAndResult(new PointerConstraint(symbolic,0,result),result);
        } else {
            return new ConstraintAndResult(null,result);
        }
    }

    public ConstraintAndResult IFNONNULL() {
        boolean result = this.address!=0;
        if (symbolic>0) {
            return new ConstraintAndResult(new PointerConstraint(symbolic,0,!result),result);
        } else {
            return new ConstraintAndResult(null,result);
        }
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
