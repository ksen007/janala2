package janala.interpreters;

import janala.solvers.History;

public final class IntegerObjectValue extends ObjectValue {
  private IntValue intValue;
  public IntValue getIntValue() {
    return intValue;
  }

  public IntegerObjectValue() {
    super(100, -1);
  }

  public IntegerObjectValue(IntValue x, int address) {
    super(100, address);
    intValue = x;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (o instanceof IntegerObjectValue) {
      IntegerObjectValue other = (IntegerObjectValue) o;
      return (intValue.equals(other.intValue));
    } else if (o instanceof IntValue) {
      IntValue otherVal = (IntValue) o;
      return intValue.equals(otherVal);
    } else {
      return false;
    }
  }


  @Override
  public Value invokeMethod(String name, Value[] args, History history) {
    if (name.equals("<init>")) {
      if (args[0] instanceof IntValue) {
        this.intValue = (IntValue) args[0];
      } else if (args[0] instanceof StringValue) {
        this.intValue = new IntValue(Integer.parseInt(((StringValue) args[0]).getConcrete()));
      }
      return PlaceHolder.instance;
    } else if (name.equals("intValue")) {
      return intValue;
    } else if (name.equals("longValue")) {
      return new LongValue((long) intValue.concrete, intValue.symbolic);
    } else if (name.equals("shortValue")) {
      return new IntValue((short) intValue.concrete, intValue.symbolic);
    } else if (name.equals("byteValue")) {
      return new IntValue((byte) intValue.concrete, intValue.symbolic);
    } else if (name.equals("equals")) {
      if (args[0] instanceof IntegerObjectValue) {
        IntegerObjectValue i2 = (IntegerObjectValue) args[0];
        IntValue ret = intValue.ISUB(i2.intValue);
        ret.concrete = ret.concrete == 0 ? 1 : 0;
        if (ret.symbolic != null) {
          ret.symbolic = ret.symbolic.setop(COMPARISON_OPS.EQ);
        }
        return ret;
      }
    } else if (name.equals("compareTo")) {
      if (args[0] instanceof IntegerObjectValue) {
        IntegerObjectValue i2 = (IntegerObjectValue) args[0];
        IntValue ret = intValue.ISUB(i2.intValue);
        if (ret.concrete > 0) {
          ret.concrete = 1;
        } else if (ret.concrete == 0) {
          ret.concrete = 0;
        } else {
          ret.concrete = -1;
        }
        return ret;
      }
    }
    return PlaceHolder.instance;
  }
}
