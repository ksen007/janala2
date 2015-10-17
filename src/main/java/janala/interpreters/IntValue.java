package janala.interpreters;


import janala.solvers.History;
import java.util.Map;

/** 
 * IntValue contains a pair of concrete value and a path constraint.  
 * Note that the path constraint flip the boolean signs according to the 
 * evaluated concrete value.
 */
public class IntValue extends Value {
  public SymbolicInt symbolic;
  public Constraint nonIntConstraint; 
  public int concrete;

  final public static IntValue TRUE = new IntValue(1);
  final public static IntValue FALSE = new IntValue(0);

  @Override
  public Object getConcrete() {
    return concrete;
  }

  public IntValue(int i) {
    concrete = i;
    symbolic = null;
    nonIntConstraint = null;
  }

  public IntValue(int concrete, Constraint nonIntConstraint) {
    this.concrete = concrete;
    if (nonIntConstraint instanceof SymbolicInt) {
      this.symbolic = (SymbolicInt) nonIntConstraint;
    } else {
      this.nonIntConstraint = nonIntConstraint;
    }
  }

  public boolean equals(Object other) {
    if (other == null ) {
      return false;
    } 
    if (other == this) {
      return true;
    }
    if (other instanceof IntValue) {
      IntValue otherVal = (IntValue) other;
      return (this.concrete == otherVal.concrete &&
        (this.symbolic == otherVal.symbolic || 
          this.symbolic.equals(otherVal.symbolic)) && 
        (this.nonIntConstraint == otherVal.nonIntConstraint ||
          this.nonIntConstraint.equals(otherVal.nonIntConstraint)));
    } else {
      return false;
    }
  }

  public int getSymbol() {
    if (symbolic == null) {
      throw new RuntimeException("No symbols created.");
    }
    Integer[] result = symbolic.getLinear().keySet().toArray(new Integer[0]);
    return result[0];
  }

  @Override
  public int MAKE_SYMBOLIC(History history) {
    symbol = symbol + inc;
    symbolic = new SymbolicInt(symbol - inc);
    return symbol - inc;
  }

  public long substituteInLinear(Map<String, Long> assignments) {
    long val = 0;

    if (symbolic == null) {
      return concrete;
    }
    for (Map.Entry<Integer, Long> it : symbolic.getLinear().entrySet()) {
      int key = it.getKey();
      long l = it.getValue();
      if (assignments.containsKey("x" + key)) {
        val += assignments.get("x" + key) * l;
      } else {
        return this.concrete;
      }
    }
    val += symbolic.getConstant();
    return val;
  }

  public IntValue IINC(int increment) {
    IntValue ret = new IntValue(concrete + increment);
    if (symbolic != null) {
      ret.symbolic = symbolic.add(increment);
    }
    return ret;
  }

  public IntValue IFEQ() {
    boolean result = concrete == 0;
    if (symbolic == null && nonIntConstraint == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null) {
      if (symbolic.getOp() == COMPARISON_OPS.UN)
        return new IntValue(
            result ? 1 : 0,
            result
                ? symbolic.setop(COMPARISON_OPS.EQ)
                : symbolic.setop(COMPARISON_OPS.NE));
      else return new IntValue(result ? 1 : 0, result ? (SymbolicInt) symbolic.not() : symbolic);
    } else {
      return new IntValue(result ? 1 : 0, result ? nonIntConstraint.not() : nonIntConstraint);
    }
  }

  public IntValue IFNE() {
    boolean result = (concrete != 0);
    if (symbolic == null && nonIntConstraint == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null) {
      if (symbolic.getOp() == COMPARISON_OPS.UN)
        return new IntValue(
            result ? 1 : 0,
            result
                ? symbolic.setop(COMPARISON_OPS.NE)
                : symbolic.setop(COMPARISON_OPS.EQ));
      else return new IntValue(result ? 1 : 0, result ? symbolic : (SymbolicInt) symbolic.not());
    } else {
      return new IntValue(result ? 1 : 0, result ? nonIntConstraint : nonIntConstraint.not());
    }
  }

  public IntValue IFLT() {
    if (symbolic == null) {
      return (concrete < 0) ? IntValue.TRUE : IntValue.FALSE;
    } else {
      boolean result = concrete < 0;
      return new IntValue(
          result ? 1 : 0,
          result
              ? symbolic.setop(COMPARISON_OPS.LT)
              : symbolic.setop(COMPARISON_OPS.GE));
    }
  }

  public IntValue IFGE() {
    if (symbolic == null) {
      return (concrete >= 0) ? IntValue.TRUE : IntValue.FALSE;
    } else {
      boolean result = concrete >= 0;
      return new IntValue(
          result ? 1 : 0,
          result
              ? symbolic.setop(COMPARISON_OPS.GE)
              : symbolic.setop(COMPARISON_OPS.LT));
    }
  }

  public IntValue IFGT() {
    if (symbolic == null) {
      return (concrete > 0) ? IntValue.TRUE : IntValue.FALSE;
    } else {
      boolean result = concrete > 0;
      return new IntValue(
          result ? 1 : 0,
          result
              ? symbolic.setop(COMPARISON_OPS.GT)
              : symbolic.setop(COMPARISON_OPS.LE));
    }
  }

  public IntValue IFLE() {
    if (symbolic == null) {
      return (concrete <= 0) ? IntValue.TRUE : IntValue.FALSE;
    } else {
      boolean result = concrete <= 0;
      return new IntValue(
          result ? 1 : 0,
          result
              ? symbolic.setop(COMPARISON_OPS.LE)
              : symbolic.setop(COMPARISON_OPS.GT));
    }
  }

  public IntValue IF_ICMPEQ(IntValue i2) {
    boolean result = (concrete == i2.concrete);
    COMPARISON_OPS op =
        result ? COMPARISON_OPS.EQ : COMPARISON_OPS.NE;
    if (symbolic == null && i2.symbolic == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null && i2.symbolic != null) {
      SymbolicInt tmp = symbolic.subtract(i2.symbolic);
      if (tmp != null) {
        tmp = tmp.setop(op);
      } 
      return new IntValue(result ? 1 : 0, tmp);
    } else if (symbolic != null) {
      return new IntValue(result ? 1 : 0, symbolic.subtract(i2.concrete).setop(op));
    } else {
      return new IntValue(result ? 1 : 0, i2.symbolic.subtractFrom(concrete).setop(op));
    }
  }

  public IntValue IF_ICMPNE(IntValue i2) {
    boolean result = (concrete != i2.concrete);
    COMPARISON_OPS op =
        result ? COMPARISON_OPS.NE : COMPARISON_OPS.EQ;
    if (symbolic == null && i2.symbolic == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null && i2.symbolic != null) {
      SymbolicInt tmp = symbolic.subtract(i2.symbolic);
      if (tmp != null) {
        tmp = tmp.setop(op);
      }
      return new IntValue(result ? 1 : 0, tmp);
    } else if (symbolic != null) {
      return new IntValue(result ? 1 : 0, symbolic.subtract(i2.concrete).setop(op));
    } else {
      return new IntValue(result ? 1 : 0, i2.symbolic.subtractFrom(concrete).setop(op));
    }
  }

  public IntValue IF_ICMPLT(IntValue i2) {
    boolean result = (concrete < i2.concrete);
    COMPARISON_OPS op =
        result ? COMPARISON_OPS.LT : COMPARISON_OPS.GE;
    if (symbolic == null && i2.symbolic == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null && i2.symbolic != null) {
      SymbolicInt tmp = symbolic.subtract(i2.symbolic);
      if (tmp != null) {
        tmp = tmp.setop(op);
      }
      return new IntValue(result ? 1 : 0, tmp);
    } else if (symbolic != null) {
      return new IntValue(result ? 1 : 0, symbolic.subtract(i2.concrete).setop(op));
    } else {
      return new IntValue(result ? 1 : 0, i2.symbolic.subtractFrom(concrete).setop(op));
    }
  }

  public IntValue IF_ICMPGE(IntValue i2) {
    boolean result = (concrete >= i2.concrete);
    COMPARISON_OPS op =
        result ? COMPARISON_OPS.GE : COMPARISON_OPS.LT;
    if (symbolic == null && i2.symbolic == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null && i2.symbolic != null) {
      SymbolicInt tmp = symbolic.subtract(i2.symbolic);
      if (tmp != null) {
        tmp = tmp.setop(op);
      } 
      return new IntValue(result ? 1 : 0, tmp);
    } else if (symbolic != null) {
      return new IntValue(result ? 1 : 0, symbolic.subtract(i2.concrete).setop(op));
    } else {
      return new IntValue(result ? 1 : 0, i2.symbolic.subtractFrom(concrete).setop(op));
    }
  }

  public IntValue IF_ICMPGT(IntValue i2) {
    boolean result = (concrete > i2.concrete);
    COMPARISON_OPS op =
        result ? COMPARISON_OPS.GT : COMPARISON_OPS.LE;
    if (symbolic == null && i2.symbolic == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null && i2.symbolic != null) {
      SymbolicInt tmp = symbolic.subtract(i2.symbolic);
      if (tmp != null) {
        tmp = tmp.setop(op);
      }
      return new IntValue(result ? 1 : 0, tmp);
    } else if (symbolic != null) {
      return new IntValue(result ? 1 : 0, symbolic.subtract(i2.concrete).setop(op));
    } else {
      return new IntValue(result ? 1 : 0, i2.symbolic.subtractFrom(concrete).setop(op));
    }
  }

  public IntValue IF_ICMPLE(IntValue i2) {
    boolean result = (concrete <= i2.concrete);
    COMPARISON_OPS op =
        result ? COMPARISON_OPS.LE : COMPARISON_OPS.GT;
    if (symbolic == null && i2.symbolic == null) {
      return result ? IntValue.TRUE : IntValue.FALSE;
    } else if (symbolic != null && i2.symbolic != null) {
      SymbolicInt tmp = symbolic.subtract(i2.symbolic);
      if (tmp != null) {
        tmp = tmp.setop(op);
      }
      return new IntValue(result ? 1 : 0, tmp);
    } else if (symbolic != null) {
      return new IntValue(result ? 1 : 0, symbolic.subtract(i2.concrete).setop(op));
    } else {
      return new IntValue(result ? 1 : 0, i2.symbolic.subtractFrom(concrete).setop(op));
    }
  }

  public IntValue IADD(IntValue i) {
    if (symbolic == null && i.symbolic == null) {
      return new IntValue(concrete + i.concrete);
    } else if (symbolic != null && i.symbolic != null) {
      return new IntValue(concrete + i.concrete, symbolic.add(i.symbolic));
    } else if (symbolic != null) {
      return new IntValue(concrete + i.concrete, symbolic.add(i.concrete));
    } else {
      return new IntValue(concrete + i.concrete, i.symbolic.add(concrete));
    }
  }

  public IntValue ISUB(IntValue i) {
    if (symbolic == null && i.symbolic == null) {
      return new IntValue(concrete - i.concrete);
    } else if (symbolic != null && i.symbolic != null) {
      return new IntValue(concrete - i.concrete, symbolic.subtract(i.symbolic));
    } else if (symbolic != null) {
      return new IntValue(concrete - i.concrete, symbolic.subtract(i.concrete));
    } else {
      return new IntValue(concrete - i.concrete, i.symbolic.subtractFrom(concrete));
    }
  }

  public IntValue IMUL(IntValue i) {
    if (symbolic == null && i.symbolic == null) {
      return new IntValue(concrete * i.concrete);
    } else if (symbolic != null && i.symbolic != null) {
      return new IntValue(concrete * i.concrete, symbolic.multiply(i.concrete));
    } else if (symbolic != null) {
      return new IntValue(concrete * i.concrete, symbolic.multiply(i.concrete));
    } else {
      return new IntValue(concrete * i.concrete, i.symbolic.multiply(concrete));
    }
  }

  // TODO: this does not look like properly supported.
  public IntValue IDIV(IntValue i) {
    return new IntValue(concrete / i.concrete);
  }

  public IntValue IREM(IntValue i) {
    return new IntValue(concrete % i.concrete);
  }

  public IntValue INEG() {
    if (symbolic == null) return new IntValue(-concrete);
    else {
      IntValue ret = new IntValue(-concrete);
      ret.symbolic = symbolic.subtractFrom(0);
      return ret;
    }
  }

  public IntValue ISHL(IntValue i) {
    return new IntValue(concrete << i.concrete);
  }

  public IntValue ISHR(IntValue i) {
    return new IntValue(concrete >> i.concrete);
  }

  public IntValue IUSHR(IntValue i) {
    return new IntValue(concrete >>> i.concrete);
  }

  public IntValue IAND(IntValue i) {
    return new IntValue(concrete & i.concrete);
  }

  public IntValue IOR(IntValue i) {
    return new IntValue(concrete | i.concrete);
  }

  public IntValue IXOR(IntValue i) {
    return new IntValue(concrete ^ i.concrete);
  }

  public LongValue I2L() {
    return new LongValue((long) concrete, symbolic);
  }

  public FloatValue I2F() {
    return new FloatValue((float) concrete);
  }

  public DoubleValue I2D() {
    return new DoubleValue((double) concrete);
  }

  public IntValue I2B() {
    return new IntValue((byte) concrete, symbolic);
  }

  public IntValue I2C() {
    return new IntValue((char) concrete, symbolic);
  }

  public IntValue I2S() {
    return new IntValue((short) concrete, symbolic);
  }

  @Override
  public String toString() {
    return "IntValue{" + "symbolic=" + 
      symbolic + ", concrete=" + concrete + 
      ", nonIntConstraint=" + nonIntConstraint +  '}';
  }

  public Constraint getSymbolic() {
    return symbolic != null ? symbolic : (nonIntConstraint != null ? nonIntConstraint : null);
  }

  public SymbolicInt getSymbolicInt() {
    return symbolic;
  }
}
