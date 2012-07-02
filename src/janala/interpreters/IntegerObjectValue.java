/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 2:47 PM
 */
public class IntegerObjectValue extends ObjectValue {
    IntValue intValue;

    public IntegerObjectValue() {
        super(100,-1);
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("<init>")) {
            if (args[0] instanceof IntValue)
                this.intValue = (IntValue)args[0];
            else
                this.intValue = new IntValue(Integer.parseInt(((StringValue)args[0]).getConcrete()));
            return PlaceHolder.instance;
        } else if(name.equals("intValue")) {
            return intValue;
        } else if(name.equals("longValue")) {
            return new LongValue((long)intValue.concrete,intValue.symbolic);
        } else if(name.equals("shortValue")) {
            return new IntValue((short)intValue.concrete,intValue.symbolic);
        } else if(name.equals("byteValue")) {
            return new IntValue((byte)intValue.concrete,intValue.symbolic);
        } else if(name.equals("equals")) {
            if (args[0] instanceof IntegerObjectValue) {
                IntegerObjectValue i2 = (IntegerObjectValue)args[0];
                IntValue ret = intValue.ISUB(i2.intValue);
                ret.concrete = ret.concrete==0?1:0;
                if (ret.symbolic!=null) {
                    ret.symbolic = ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
                    return ret;
                }
            }
        } else if(name.equals("compareTo")) {
            if (args[0] instanceof IntegerObjectValue) {
                IntegerObjectValue i2 = (IntegerObjectValue)args[0];
                IntValue ret = intValue.ISUB(i2.intValue);
                return ret;
            }
        }
        return PlaceHolder.instance;
    }
}
