package janala.interpreters;

import janala.solvers.History;

public class SqlDateObjectValue extends ObjectValue {
  LongValue longValue;

  public SqlDateObjectValue() {
    super(100, -1);
  }

  @Override
  public Value invokeMethod(String name, Value[] args, History history) {
    if (name.equals("<init>")) {
      if (args[0] instanceof LongValue) this.longValue = (LongValue) args[0];
      return PlaceHolder.instance;
    } else if (name.equals("equals")) {
      if (args[0] instanceof SqlDateObjectValue) {
        SqlDateObjectValue i2 = (SqlDateObjectValue) args[0];
        LongValue ret = longValue.LSUB(i2.longValue);
        int concreteVal = ret.getConcreteLong() == 0 ? 1 : 0;
        if (ret.getSymbolic() != null) {
          SymbolicInt retSymbol = ret.getSymbolic().setop(COMPARISON_OPS.EQ);
          return new IntValue(concreteVal, retSymbol);
        }
      }
    } else if (name.equals("compareTo")) {
      if (args[0] instanceof SqlDateObjectValue) {
        SqlDateObjectValue i2 = (SqlDateObjectValue) args[0];
        LongValue ret = longValue.LSUB(i2.longValue);
        int concreteVal = 0; 
        if (ret.getConcreteLong() > 0) {
          concreteVal = 1;
        } else if (ret.getConcreteLong() == 0) {
          concreteVal = 0;
        } else {
          concreteVal = -1;
        }
        return new IntValue(concreteVal, ret.getSymbolic());
      }
    } else if (name.equals("after")) {
      if (args[0] instanceof SqlDateObjectValue) {
        SqlDateObjectValue i2 = (SqlDateObjectValue) args[0];
        LongValue ret = longValue.LSUB(i2.longValue);
        int concreteVal = 0;
        if (ret.getConcreteLong() > 0) {
          concreteVal = 1;
        } else {
          concreteVal = 0;
        }
        return new IntValue(concreteVal, 
          ret.getSymbolic().setop(COMPARISON_OPS.GT));
      }
    } else if (name.equals("before")) {
      if (args[0] instanceof SqlDateObjectValue) {
        SqlDateObjectValue i2 = (SqlDateObjectValue) args[0];
        LongValue ret = longValue.LSUB(i2.longValue);
        int concreteVal = -1;
        if (ret.getConcreteLong() < 0) {
          concreteVal = 1;
        } else {
          concreteVal = 0;
        } 
        return new IntValue(concreteVal, 
          ret.getSymbolic().setop(COMPARISON_OPS.LT));
      }
    } else if (name.equals("getTime")) {
      return new LongValue(longValue.getConcreteLong(), longValue.getSymbolic());
    } else if (name.equals("setTime")) {
      if (args[0] instanceof LongValue) this.longValue = (LongValue) args[0];
      return PlaceHolder.instance;
    }
    return PlaceHolder.instance;
  }
}
