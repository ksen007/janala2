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

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public IntValue(int i) {
        concrete = i;
        symbolic = null;
    }

    public IntValue(int i, SymbolicInt symbolic) {
        concrete = i;
        this.symbolic = symbolic;
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

    public ConstraintAndResult IFEQ() {
        boolean result = concrete==0;
        if (symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else {
            return new ConstraintAndResult(result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.NE),result);
        }
    }

    public ConstraintAndResult IFNE() {
        boolean result = concrete!=0;
        if (symbolic==null) {
            return (concrete!=0)?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else {
            return new ConstraintAndResult(result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.NE):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ),result);
        } 
    }

    public ConstraintAndResult IFLT() {
        if (symbolic==null) {
            return (concrete<0)?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else {
            boolean result = concrete<0;
            return new ConstraintAndResult(result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LT):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GE),result);
        }
    }

    public ConstraintAndResult IFGE() {
        if (symbolic==null) {
            return (concrete>=0)?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else {
            boolean result = concrete>=0;
            return new ConstraintAndResult(result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GE):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LT),result);
        }
    }

    public ConstraintAndResult IFGT() {
        if (symbolic==null) {
            return (concrete>0)?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else {
            boolean result = concrete>0;
            return new ConstraintAndResult(result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GT):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LE),result);
        }
    }

    public ConstraintAndResult IFLE() {
        if (symbolic==null) {
            return (concrete<=0)?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else {
            boolean result = concrete<=0;
            return new ConstraintAndResult(result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LE):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GT),result);
        }
    }

    public ConstraintAndResult IF_ICMPEQ(IntValue i2) {
        boolean result = (concrete==i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.EQ:SymbolicInt.COMPARISON_OPS.NE;
        if (symbolic==null && i2.symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.constraint = tmp.setop(op);
            return ret;
        } else if (symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = symbolic.subtract(i2.concrete).setop(op);
            return ret;
        } else {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = i2.symbolic.subtractFrom(concrete).setop(op);
            return ret;
        }
    }

    public ConstraintAndResult IF_ICMPNE(IntValue i2) {
        boolean result = (concrete!=i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.NE:SymbolicInt.COMPARISON_OPS.EQ;
        if (symbolic==null && i2.symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.constraint = tmp.setop(op);
            return ret;
        } else if (symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = symbolic.subtract(i2.concrete).setop(op);
            return ret;
        } else {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = i2.symbolic.subtractFrom(concrete).setop(op);
            return ret;
        }
    }

    public ConstraintAndResult IF_ICMPLT(IntValue i2) {
        boolean result = (concrete<i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.LT:SymbolicInt.COMPARISON_OPS.GE;
        if (symbolic==null && i2.symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.constraint = tmp.setop(op);
            return ret;
        } else if (symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = symbolic.subtract(i2.concrete).setop(op);
            return ret;
        } else {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = i2.symbolic.subtractFrom(concrete).setop(op);
            return ret;
        }
    }

    public ConstraintAndResult IF_ICMPGE(IntValue i2) {
        boolean result = (concrete>=i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.GE:SymbolicInt.COMPARISON_OPS.LT;
        if (symbolic==null && i2.symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.constraint = tmp.setop(op);
            return ret;
        } else if (symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = symbolic.subtract(i2.concrete).setop(op);
            return ret;
        } else {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = i2.symbolic.subtractFrom(concrete).setop(op);
            return ret;
        }
    }

    public ConstraintAndResult IF_ICMPGT(IntValue i2) {
        boolean result = (concrete>i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.GT:SymbolicInt.COMPARISON_OPS.LE;
        if (symbolic==null && i2.symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.constraint = tmp.setop(op);
            return ret;
        } else if (symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = symbolic.subtract(i2.concrete).setop(op);
            return ret;
        } else {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = i2.symbolic.subtractFrom(concrete).setop(op);
            return ret;
        }
    }

    public ConstraintAndResult IF_ICMPLE(IntValue i2) {
        boolean result = (concrete<=i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.LE:SymbolicInt.COMPARISON_OPS.GT;
        if (symbolic==null && i2.symbolic==null) {
            return result?ConstraintAndResult.TRUE:ConstraintAndResult.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                ret.constraint = tmp.setop(op);
            return ret;
        } else if (symbolic!=null) {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = symbolic.subtract(i2.concrete).setop(op);
            return ret;
        } else {
            ConstraintAndResult ret = new ConstraintAndResult(null,result);
            ret.constraint = i2.symbolic.subtractFrom(concrete).setop(op);
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
        if (symbolic==null)
            return new IntValue(-concrete);
        else {
            IntValue ret = new IntValue(-concrete);
            ret.symbolic = symbolic.subtractFrom(0);
            return ret;
        }
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
        return new LongValue((long)concrete,symbolic);
    }

    public FloatValue I2F() {
        return new FloatValue((float )concrete);
    }

    public DoubleValue I2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue I2B() {
        return new IntValue((byte)concrete,symbolic);
    }

    public IntValue I2C() {
        return new IntValue((char)concrete,symbolic);
    }

    public IntValue I2S() {
        return new IntValue((short)concrete,symbolic);
    }

    @Override
    public String toString() {
        return "IntValue{" +
                "symbolic=" + symbolic +
                ", concrete=" + concrete +
                '}';
    }
}
