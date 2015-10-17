package janala.interpreters;

import janala.solvers.History;

public final class LongObjectValue extends ObjectValue {
  private LongValue longValue;
  public Value getLongValue() { return longValue; }

  public LongObjectValue() {
    super(100, -1);
  }

  public LongObjectValue(LongValue v, int address) {
    super(100, address);
    longValue = v;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (o instanceof LongObjectValue) {
      LongObjectValue other = (LongObjectValue) o;
      return (longValue.equals(other.longValue));
    } else if (o instanceof LongValue) {
      LongValue otherVal = (LongValue) o;
      return longValue.equals(otherVal);
    } else {
      return false;
    }
  }

  @Override
  public Value invokeMethod(String name, Value[] args, History history) {
    if (name.equals("<init>")) {
      if (args[0] instanceof LongValue) {
        this.longValue = (LongValue) args[0];
      } else {
        this.longValue = new LongValue(Long.parseLong(((StringValue) args[0]).getConcrete()));
      }
      return PlaceHolder.instance;
    } else if (name.equals("intValue")) {
      return new IntValue((int) longValue.getConcreteLong(), longValue.getSymbolic());
    } else if (name.equals("longValue")) {
      return new LongValue((long) longValue.getConcreteLong(), longValue.getSymbolic());
    } else if (name.equals("shortValue")) {
      return new IntValue((short) longValue.getConcreteLong(), longValue.getSymbolic());
    } else if (name.equals("byteValue")) {
      return new IntValue((byte) longValue.getConcreteLong(), longValue.getSymbolic());
    } else if (name.equals("equals")) {
      if (args[0] instanceof LongObjectValue) {
        LongObjectValue i2 = (LongObjectValue) args[0];
        LongValue ret = longValue.LSUB(i2.longValue);
        int concreteVal = ret.getConcreteLong() == 0L ? 1 : 0;
        if (ret.getSymbolic() != null) {
          SymbolicInt retSymbolic = ret.getSymbolic().setop(COMPARISON_OPS.EQ);
          return new IntValue((int) concreteVal, retSymbolic);
        } else {
          return new IntValue((int) concreteVal);
        }
      }
    } else if (name.equals("compareTo")) {
      if (args[0] instanceof LongObjectValue) {
        LongObjectValue i2 = (LongObjectValue) args[0];
        LongValue ret = longValue.LSUB(i2.longValue);
        int concreteVal = -1;
        if (ret.getConcreteLong() > 0) {
          concreteVal = 1;
        } else if (ret.getConcreteLong() == 0) {
          concreteVal = 0;
        } else {
          concreteVal = -1;
        }
        IntValue ret2 = new IntValue(concreteVal, ret.getSymbolic());
        return ret2;
      }
    }
    return PlaceHolder.instance;
  }
}
