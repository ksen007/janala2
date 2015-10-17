package janala.interpreters;

public class DoubleValue extends Value {
  double concrete;

  @Override
  public Object getConcrete() {
    return concrete;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    } else if (other == null) {
      return false;
    } else if (other instanceof DoubleValue) {
      DoubleValue otherVal = (DoubleValue) other;
      return (concrete == otherVal.concrete);
    } else {
      return false;
    }
  }

  public DoubleValue(double concrete) {
    this.concrete = concrete;
  }

  public DoubleValue DADD(DoubleValue d) {
    return new DoubleValue(concrete + d.concrete);
  }

  public DoubleValue DSUB(DoubleValue d) {
    return new DoubleValue(concrete - d.concrete);
  }

  public DoubleValue DMUL(DoubleValue d) {
    return new DoubleValue(concrete * d.concrete);
  }

  public DoubleValue DDIV(DoubleValue d) {
    return new DoubleValue(concrete / d.concrete);
  }

  public DoubleValue DREM(DoubleValue d) {
    return new DoubleValue(concrete % d.concrete);
  }

  public DoubleValue DNEG() {
    return new DoubleValue(-concrete);
  }

  public IntValue D2I() {
    return new IntValue((int) concrete);
  }

  public LongValue D2L() {
    return new LongValue((long) concrete);
  }

  public FloatValue D2F() {
    return new FloatValue((float) concrete);
  }

  public IntValue DCMPL(DoubleValue d) {
    if (Double.isNaN(concrete) || Double.isNaN(d.concrete)) {
      return new IntValue(-1);
    } else {
      return new IntValue(-((Double)concrete).compareTo(d.concrete));
    }
  }

  public IntValue DCMPG(DoubleValue d) {
    if (Double.isNaN(concrete) || Double.isNaN(d.concrete)) {
      return new IntValue(1);
    } else {
      return new IntValue(((Double)concrete).compareTo(d.concrete));
    }
  }
}
