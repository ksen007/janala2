/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class SqlDateObjectValue extends ObjectValue {
    LongValue longValue;

    public SqlDateObjectValue() {
        super(100,-1);
    }

    @Override
    public Value invokeMethod(String name, Value[] args) {
        if (name.equals("<init>")) {
            if (args[0] instanceof LongValue)
                this.longValue = (LongValue)args[0];
            return PlaceHolder.instance;
        } else if(name.equals("equals")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                ret.concrete = ret.concrete==0?1:0;
                if (ret.symbolic!=null) {
                    ret.symbolic = ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
                    IntValue ret2 = new IntValue((int)ret.concrete,ret.symbolic);
                    return ret2;
                }
            }
        } else if(name.equals("compareTo")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete>0) ret.concrete=1;
                else if (ret.concrete==0) ret.concrete = 0;
                else ret.concrete = -1;
                IntValue ret2 = new IntValue((int)ret.concrete,ret.symbolic);
                return ret2;
            }
        } else if(name.equals("after")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete>0) ret.concrete=1;
                else ret.concrete = 0;
                IntValue ret2 = new IntValue((int)ret.concrete,
                                ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.GT));
                return ret2;
            }
        } else if(name.equals("before")) {
            if (args[0] instanceof SqlDateObjectValue) {
                SqlDateObjectValue i2 = (SqlDateObjectValue)args[0];
                LongValue ret = longValue.LSUB(i2.longValue);
                if (ret.concrete<0) ret.concrete=1;
                else ret.concrete = 0;
                IntValue ret2 = new IntValue((int)ret.concrete,
                                ret.symbolic.setop(SymbolicInt.COMPARISON_OPS.LT));
                return ret2;
            }
        } else if(name.equals("getTime")) {
            return new LongValue((long) longValue.concrete, longValue.symbolic);
        } else if (name.equals("setTime")) {
            if (args[0] instanceof LongValue)
                this.longValue = (LongValue)args[0];
            return PlaceHolder.instance;
        }
        return PlaceHolder.instance;
    }
}
