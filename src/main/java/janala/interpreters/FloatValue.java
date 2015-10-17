package janala.interpreters;

public class FloatValue extends Value {
  float concrete;

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
    } else if (other instanceof FloatValue) {
      FloatValue otherVal = (FloatValue) other;
      return (concrete == otherVal.concrete);
    } else {
      return false;
    }
  }

  public FloatValue(float concrete) {
    this.concrete = concrete;
  }

  public FloatValue FADD(FloatValue d) {
    return new FloatValue(concrete + d.concrete);
  }

  public FloatValue FSUB(FloatValue d) {
    return new FloatValue(concrete - d.concrete);
  }

  public FloatValue FMUL(FloatValue d) {
    return new FloatValue(concrete * d.concrete);
  }

  public FloatValue FDIV(FloatValue d) {
    return new FloatValue(concrete / d.concrete);
  }

  public FloatValue FREM(FloatValue d) {
    return new FloatValue(concrete % d.concrete);
  }

  public FloatValue FNEG() {
    return new FloatValue(-concrete);
  }

  public IntValue F2I() {
    return new IntValue((int) concrete);
  }

  public LongValue F2L() {
    return new LongValue((long) concrete);
  }

  public DoubleValue F2D() {
    return new DoubleValue((double) concrete);
  }

  public IntValue FCMPL(FloatValue d) {
    if (Float.isNaN(concrete) || Float.isNaN(d.concrete)) {
      return new IntValue(-1);
    } else {
      return new IntValue(-((Float)concrete).compareTo(d.concrete));
    }
  }

  public IntValue FCMPG(FloatValue d) {
    if (Float.isNaN(concrete) || Float.isNaN(d.concrete)) {
      return new IntValue(1);
    } else {
      return new IntValue(((Float)concrete).compareTo(d.concrete));
    }
  }
}
