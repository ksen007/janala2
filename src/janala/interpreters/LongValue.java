/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 10:00 AM
 */
public class LongValue extends Value {
    SymbolicInt symbolic;
    long concrete;

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public LongValue(long concrete) {
        this.concrete = concrete;
        symbolic = null;
    }

    public LongValue(long concrete, SymbolicInt symbolic) {
        this.concrete = concrete;
        this.symbolic = symbolic;
    }

    public void MAKE_SYMBOLIC(int symbol) {
         symbolic = new SymbolicInt(symbol);
     }

    public LongValue LADD(LongValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new LongValue(concrete+i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            LongValue ret = new LongValue(concrete+i.concrete);
            ret.symbolic = symbolic.add(i.symbolic);
            return ret;
        } else if (symbolic!=null) {
            LongValue ret = new LongValue(concrete+i.concrete);
            ret.symbolic = symbolic.add(i.concrete);
            return ret;
        } else {
            LongValue ret = new LongValue(concrete+i.concrete);
            ret.symbolic = i.symbolic.add(concrete);
            return ret;
        }
    }

    public LongValue LSUB(LongValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new LongValue(concrete-i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            LongValue ret = new LongValue(concrete-i.concrete);
            ret.symbolic = symbolic.subtract(i.symbolic);
            return ret;
        } else if (symbolic!=null) {
            LongValue ret = new LongValue(concrete-i.concrete);
            ret.symbolic = symbolic.subtract(i.concrete);
            return ret;
        } else {
            LongValue ret = new LongValue(concrete-i.concrete);
            ret.symbolic = i.symbolic.subtractFrom(concrete);
            return ret;
        }
    }

    public LongValue LMUL(LongValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new LongValue(concrete*i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            LongValue ret = new LongValue(concrete*i.concrete);
            ret.symbolic = symbolic.multiply(i.concrete);
            return ret;
        } else if (symbolic!=null) {
            LongValue ret = new LongValue(concrete*i.concrete);
            ret.symbolic = symbolic.multiply(i.concrete);
            return ret;
        } else {
            LongValue ret = new LongValue(concrete*i.concrete);
            ret.symbolic = i.symbolic.multiply(concrete);
            return ret;
        }
    }

    public LongValue LDIV(LongValue i) {
        return new LongValue(concrete/i.concrete);
    }

    public LongValue LREM(LongValue i) {
        return new LongValue(concrete%i.concrete);
    }

    public LongValue LNEG() {
        if (symbolic==null)
            return new LongValue(-concrete);
        else {
            LongValue ret = new LongValue(-concrete);
            ret.symbolic = symbolic.subtractFrom(0);
            return ret;
        }
    }

    public LongValue LSHL(LongValue i) {
        return new LongValue(concrete<<i.concrete);
    }

    public LongValue LSHR(LongValue i) {
        return new LongValue(concrete>>i.concrete);
    }

    public LongValue LUSHR(LongValue i) {
        return new LongValue(concrete>>>i.concrete);
    }

    public LongValue LAND(LongValue i) {
        return new LongValue(concrete&i.concrete);
    }

    public LongValue LOR(LongValue i) {
        return new LongValue(concrete|i.concrete);
    }

    public LongValue LXOR(LongValue i) {
        return new LongValue(concrete^i.concrete);
    }

    public IntValue L2I() {
        return new IntValue((int)concrete,symbolic);
    }

    public FloatValue L2F() {
        return new FloatValue((float )concrete);
    }

    public DoubleValue L2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue LCMP(LongValue i2) {
        IntValue ret;
        if (concrete == i2.concrete) {
            ret = new IntValue(0);
        } else if (concrete > i2.concrete) {
            ret = new IntValue(1);
        } else {
            ret = new IntValue(-1);
        }
        if (symbolic==null && i2.symbolic==null) {
            return ret;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ret.symbolic = symbolic.subtract(i2.symbolic);
//            if (tmp!=null) {
//                if (ret.concrete==0)
//                    ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.EQ);
//                if (ret.concrete==1)
//                    ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.GT);
//                if (ret.concrete==-1)
//                    ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.LT);
//            }
            return ret;
        } else if (symbolic!=null) {
            if (ret.concrete==0)
                ret.symbolic = symbolic.subtract(i2.concrete);//.setop(SymbolicInt.COMPARISON_OPS.EQ);
            if (ret.concrete==1)
                ret.symbolic = symbolic.subtract(i2.concrete);//.setop(SymbolicInt.COMPARISON_OPS.GT);
            if (ret.concrete==-1)
                ret.symbolic = symbolic.subtract(i2.concrete);//.setop(SymbolicInt.COMPARISON_OPS.LT);
            return ret;
        } else {
            if (ret.concrete==0)
                ret.symbolic = i2.symbolic.subtractFrom(concrete);//.setop(SymbolicInt.COMPARISON_OPS.EQ);
            if (ret.concrete==1)
                ret.symbolic = i2.symbolic.subtractFrom(concrete);//.setop(SymbolicInt.COMPARISON_OPS.GT);
            if (ret.concrete==-1)
                ret.symbolic = i2.symbolic.subtractFrom(concrete);//.setop(SymbolicInt.COMPARISON_OPS.LT);
            return ret;
        }
    }

    @Override
    public String toString() {
        return "LongValue{" +
                "symbolic=" + symbolic +
                ", concrete=" + concrete +
                '}';
    }
}
