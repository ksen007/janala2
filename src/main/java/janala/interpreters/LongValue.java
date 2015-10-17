package janala.interpreters;

import janala.solvers.History;

public class LongValue extends Value {
  private SymbolicInt symbolic; // mutable in make_symbolic
  public SymbolicInt getSymbolic() {
    return symbolic;
  }

  private final long concrete;

  @Override
  public Object getConcrete() {
    return concrete;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null ) {
      return false;
    } 
    if (other instanceof LongValue) {
      LongValue otherVal = (LongValue) other;
      return (this.concrete == otherVal.concrete &&
        this.symbolic == otherVal.symbolic);
    } else {
      return false;
    }
  }
  public long getConcreteLong() {
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

  public int MAKE_SYMBOLIC(History history) {
    symbol = symbol + inc;
    symbolic = new SymbolicInt(symbol - inc);
    return symbol - inc;
  }

  public LongValue LADD(LongValue i) {
    long concreteVal = concrete + i.concrete;
    SymbolicInt x = null;
    if (symbolic == null && i.symbolic == null) {
      return new LongValue(concreteVal);
    } else if (symbolic != null && i.symbolic != null) {
      x = symbolic.add(i.symbolic);
      return new LongValue(concreteVal, x);
    } else if (symbolic != null) {
      x = symbolic.add(i.concrete);
      return new LongValue(concreteVal, x);
    } else {
      x = i.symbolic.add(concrete);
      return new LongValue(concreteVal, x);
    }
  }

  public LongValue LSUB(LongValue i) {
    long concreteVal = concrete - i.concrete;
    SymbolicInt x = null;
    if (symbolic == null && i.symbolic == null) {
      return new LongValue(concreteVal);
    } else if (symbolic != null && i.symbolic != null) {
      x = symbolic.subtract(i.symbolic);
      return new LongValue(concreteVal, x);
    } else if (symbolic != null) {
      x = symbolic.subtract(i.concrete);
      return new LongValue(concreteVal, x);
    } else {
      x = i.symbolic.subtractFrom(concrete);
      return new LongValue(concreteVal, x);
    }
  }

  public LongValue LMUL(LongValue i) {
    long concreteVal = concrete * i.concrete;
    if (symbolic == null && i.symbolic == null) {
      return new LongValue(concreteVal);
    } else if (symbolic != null && i.symbolic != null) {
      SymbolicInt s = symbolic.multiply(i.concrete);
      return new LongValue(concreteVal, s);
    } else if (symbolic != null) {
      SymbolicInt s = symbolic.multiply(i.concrete);
      return new LongValue(concreteVal, s);
    } else {
      SymbolicInt s = i.symbolic.multiply(concrete);
      return new LongValue(concreteVal, s);
    }
  }

  public LongValue LDIV(LongValue i) {
    return new LongValue(concrete / i.concrete);
  }

  public LongValue LREM(LongValue i) {
    return new LongValue(concrete % i.concrete);
  }

  public LongValue LNEG() {
    if (symbolic == null) {
      return new LongValue(-concrete);
    } else {
      return new LongValue( -concrete, symbolic.subtractFrom(0));
    }
  }

  public LongValue LSHL(LongValue i) {
    return new LongValue(concrete << i.concrete);
  }

  public LongValue LSHR(LongValue i) {
    return new LongValue(concrete >> i.concrete);
  }

  public LongValue LUSHR(LongValue i) {
    return new LongValue(concrete >>> i.concrete);
  }

  public LongValue LAND(LongValue i) {
    return new LongValue(concrete & i.concrete);
  }

  public LongValue LOR(LongValue i) {
    return new LongValue(concrete | i.concrete);
  }

  public LongValue LXOR(LongValue i) {
    return new LongValue(concrete ^ i.concrete);
  }

  public IntValue L2I() {
    return new IntValue((int) concrete, symbolic);
  }

  public FloatValue L2F() {
    return new FloatValue((float) concrete);
  }

  public DoubleValue L2D() {
    return new DoubleValue((double) concrete);
  }

  public IntValue LCMP(LongValue i2) {
    int val = 0;
    if (concrete == i2.concrete) {
      val = 0;
    } else if (concrete > i2.concrete) {
      val = 1;
    } else {
      val = -1;
    }
    if (symbolic == null && i2.symbolic == null) {
      return new IntValue(val);
    } else if (symbolic != null && i2.symbolic != null) {
      return new IntValue(val, symbolic.subtract(i2.symbolic));
    } else if (symbolic != null) {
      return new IntValue(val, symbolic.subtract(i2.concrete));
    } else {
      return new IntValue(val, i2.symbolic.subtractFrom(concrete));
    }
  }

  @Override
  public String toString() {
    return "LongValue{" + "symbolic=" + symbolic + ", concrete=" + concrete + '}';
  }
}
