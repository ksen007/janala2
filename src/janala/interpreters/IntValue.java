/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 8:35 AM
 */
public class IntValue extends Value {
    SymbolicInt symbolic;
    int concrete;
    final static public IntValue TRUE = new IntValue(1);
    final static public IntValue FALSE = new IntValue(0);

    public IntValue(int i) {
        concrete = i;
        symbolic = null;
    }

    public void MAKE_SYMBOLIC(int symbol) {
        symbolic = new SymbolicInt(symbol);
    }

    public IntValue IINC(int increment) {
        IntValue ret = new IntValue(concrete+increment);
        if (symbolic!=null) {
            ret.symbolic = symbolic.add(increment);
        }
        return ret;
    }

    public IntValue IFEQ() {
        if (symbolic==null) {
            return (concrete==0)?TRUE:FALSE;
        } else {
            IntValue ret = new IntValue((concrete==0)?1:0);
            ret.symbolic = symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
            return ret;
        }
    }

    public IntValue IFNE() {
        if (symbolic==null) {
            return (concrete!=0)?TRUE:FALSE;
        } else {
            IntValue ret = new IntValue((concrete!=0)?1:0);
            ret.symbolic = symbolic.setop(SymbolicInt.COMPARISON_OPS.NE);
            return ret;
        }
    }

    public IntValue IFLT() {
        if (symbolic==null) {
            return (concrete<0)?TRUE:FALSE;
        } else {
            IntValue ret = new IntValue((concrete<0)?1:0);
            ret.symbolic = symbolic.setop(SymbolicInt.COMPARISON_OPS.LT);
            return ret;
        }
    }

    public IntValue IFGE() {
        if (symbolic==null) {
            return (concrete>=0)?TRUE:FALSE;
        } else {
            IntValue ret = new IntValue((concrete>=0)?1:0);
            ret.symbolic = symbolic.setop(SymbolicInt.COMPARISON_OPS.GE);
            return ret;
        }
    }

    public IntValue IFGT() {
        if (symbolic==null) {
            return (concrete>0)?TRUE:FALSE;
        } else {
            IntValue ret = new IntValue((concrete>0)?1:0);
            ret.symbolic = symbolic.setop(SymbolicInt.COMPARISON_OPS.GT);
            return ret;
        }
    }

    public IntValue IFLE() {
        if (symbolic==null) {
            return (concrete<=0)?TRUE:FALSE;
        } else {
            IntValue ret = new IntValue((concrete<=0)?1:0);
            ret.symbolic = symbolic.setop(SymbolicInt.COMPARISON_OPS.LE);
            return ret;
        }
    }

    public IntValue IF_ICMPEQ(IntValue i2) {
        if (symbolic==null && i2.symbolic==null) {
            return (concrete==i2.concrete)?TRUE:FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            IntValue ret = new IntValue((concrete==i2.concrete)?1:0);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.EQ);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue((concrete==i2.concrete)?1:0);
            ret.symbolic = symbolic.subtract(i2.concrete).setop(SymbolicInt.COMPARISON_OPS.EQ);
            return ret;
        } else {
            IntValue ret = new IntValue((concrete==i2.concrete)?1:0);
            ret.symbolic = i2.symbolic.subtractFrom(concrete).setop(SymbolicInt.COMPARISON_OPS.EQ);
            return ret;
        }
    }

    public IntValue IF_ICMPNE(IntValue i2) {
        if (symbolic==null && i2.symbolic==null) {
            return (concrete!=i2.concrete)?TRUE:FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            IntValue ret = new IntValue((concrete!=i2.concrete)?1:0);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.NE);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue((concrete!=i2.concrete)?1:0);
            ret.symbolic = symbolic.subtract(i2.concrete).setop(SymbolicInt.COMPARISON_OPS.NE);
            return ret;
        } else {
            IntValue ret = new IntValue((concrete!=i2.concrete)?1:0);
            ret.symbolic = i2.symbolic.subtractFrom(concrete).setop(SymbolicInt.COMPARISON_OPS.NE);
            return ret;
        }
    }

    public IntValue IF_ICMPLT(IntValue i2) {
        if (symbolic==null && i2.symbolic==null) {
            return (concrete<i2.concrete)?TRUE:FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            IntValue ret = new IntValue((concrete<i2.concrete)?1:0);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.LT);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue((concrete<i2.concrete)?1:0);
            ret.symbolic = symbolic.subtract(i2.concrete).setop(SymbolicInt.COMPARISON_OPS.LT);
            return ret;
        } else {
            IntValue ret = new IntValue((concrete<i2.concrete)?1:0);
            ret.symbolic = i2.symbolic.subtractFrom(concrete).setop(SymbolicInt.COMPARISON_OPS.LT);
            return ret;
        }
    }

    public IntValue IF_ICMPGE(IntValue i2) {
        if (symbolic==null && i2.symbolic==null) {
            return (concrete>=i2.concrete)?TRUE:FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            IntValue ret = new IntValue((concrete>=i2.concrete)?1:0);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.GE);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue((concrete>=i2.concrete)?1:0);
            ret.symbolic = symbolic.subtract(i2.concrete).setop(SymbolicInt.COMPARISON_OPS.GE);
            return ret;
        } else {
            IntValue ret = new IntValue((concrete>=i2.concrete)?1:0);
            ret.symbolic = i2.symbolic.subtractFrom(concrete).setop(SymbolicInt.COMPARISON_OPS.GE);
            return ret;
        }
    }

    public IntValue IF_ICMPGT(IntValue i2) {
        if (symbolic==null && i2.symbolic==null) {
            return (concrete>i2.concrete)?TRUE:FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            IntValue ret = new IntValue((concrete>i2.concrete)?1:0);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.GT);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue((concrete>i2.concrete)?1:0);
            ret.symbolic = symbolic.subtract(i2.concrete).setop(SymbolicInt.COMPARISON_OPS.GT);
            return ret;
        } else {
            IntValue ret = new IntValue((concrete>i2.concrete)?1:0);
            ret.symbolic = i2.symbolic.subtractFrom(concrete).setop(SymbolicInt.COMPARISON_OPS.GT);
            return ret;
        }
    }

    public IntValue IF_ICMPLE(IntValue i2) {
        if (symbolic==null && i2.symbolic==null) {
            return (concrete<=i2.concrete)?TRUE:FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            IntValue ret = new IntValue((concrete<=i2.concrete)?1:0);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.symbolic = tmp.setop(SymbolicInt.COMPARISON_OPS.LE);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue((concrete<=i2.concrete)?1:0);
            ret.symbolic = symbolic.subtract(i2.concrete).setop(SymbolicInt.COMPARISON_OPS.LE);
            return ret;
        } else {
            IntValue ret = new IntValue((concrete<=i2.concrete)?1:0);
            ret.symbolic = i2.symbolic.subtractFrom(concrete).setop(SymbolicInt.COMPARISON_OPS.LE);
            return ret;
        }
    }

    public IntValue IADD(IntValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new IntValue(concrete+i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            IntValue ret = new IntValue(concrete+i.concrete);
            ret.symbolic = symbolic.add(i.symbolic);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(concrete+i.concrete);
            ret.symbolic = symbolic.add(i.concrete);
            return ret;
        } else {
            IntValue ret = new IntValue(concrete+i.concrete);
            ret.symbolic = i.symbolic.add(concrete);
            return ret;
        }
    }

    public IntValue ISUB(IntValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new IntValue(concrete-i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            IntValue ret = new IntValue(concrete-i.concrete);
            ret.symbolic = symbolic.subtract(i.symbolic);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(concrete-i.concrete);
            ret.symbolic = symbolic.subtract(i.concrete);
            return ret;
        } else {
            IntValue ret = new IntValue(concrete-i.concrete);
            ret.symbolic = i.symbolic.subtractFrom(concrete);
            return ret;
        }
    }

    public IntValue IMUL(IntValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new IntValue(concrete*i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            IntValue ret = new IntValue(concrete*i.concrete);
            ret.symbolic = symbolic.multiply(i.concrete);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(concrete*i.concrete);
            ret.symbolic = symbolic.multiply(i.concrete);
            return ret;
        } else {
            IntValue ret = new IntValue(concrete*i.concrete);
            ret.symbolic = i.symbolic.multiply(concrete);
            return ret;
        }
    }

    public IntValue IDIV(IntValue i) {
        return new IntValue(concrete/i.concrete);
    }

    public IntValue IREM(IntValue i) {
        return new IntValue(concrete%i.concrete);
    }

    public IntValue INEG() {
        return new IntValue(-concrete);
    }

    public IntValue ISHL(IntValue i) {
        return new IntValue(concrete<<i.concrete);
    }

    public IntValue ISHR(IntValue i) {
        return new IntValue(concrete>>i.concrete);
    }

    public IntValue IUSHR(IntValue i) {
        return new IntValue(concrete>>>i.concrete);
    }

    public IntValue IAND(IntValue i) {
        return new IntValue(concrete&i.concrete);
    }

    public IntValue IOR(IntValue i) {
        return new IntValue(concrete|i.concrete);
    }

    public IntValue IXOR(IntValue i) {
        return new IntValue(concrete^i.concrete);
    }

    public LongValue I2L() {
        return new LongValue((long)concrete); //@todo: make sure that symbolic values flow
    }

    public FloatValue I2F() {
        return new FloatValue((float )concrete);
    }

    public DoubleValue I2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue I2B() {
        return new IntValue((byte)concrete);
    }

    public IntValue I2C() {
        return new IntValue((char)concrete);
    }

    public IntValue I2S() {
        return new IntValue((short)concrete);
    }

    @Override
    public String toString() {
        return "IntValue{" +
                "symbolic=" + symbolic +
                ", concrete=" + concrete +
                '}';
    }
}
