package janala.interpreters;

import java.util.Map;
import java.util.HashMap;

public final class SymbolicInt extends Constraint {
  private COMPARISON_OPS op;
  public COMPARISON_OPS getOp() {
    return op;
  }

  public void setOp(COMPARISON_OPS op) {
    this.op = op;
  }
  
  private final Map<Integer, Long> linear; // coefficients
  public Map<Integer, Long> getLinear() {
    return linear;
  }
  private final long constant; // nominal value
  public long getConstant() {
    return constant;
  }

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicInt(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o == null) {
      return false;
    } else if (o instanceof SymbolicInt) {
      SymbolicInt e = (SymbolicInt) o;
      return (linear.equals(e.linear) && (constant == e.constant) && (op == e.op));
    } else {
      return false;
    }
  }

  public int hashCode() {
    int ret = 37;
    ret = 71 * ret + linear.hashCode();
    ret = 71 * ret + (int) constant;
    ret = 71 * ret + op.hashCode();
    return ret;
  }

  // Construct a symbolic int i := x
  public SymbolicInt(int i) {
    linear = new HashMap<Integer, Long>();
    linear.put(i, 1L);
    constant = 0;
    op = COMPARISON_OPS.UN;
  }

  public SymbolicInt(Map<Integer, Long> linear, long constant) {
    this.linear = linear;
    this.constant = constant;
    op = COMPARISON_OPS.UN;
  }

  public SymbolicInt(Map<Integer, Long> linear, long constant, COMPARISON_OPS op) {
    this.linear = linear;
    this.constant = constant;
    this.op = op;
  }

  private SymbolicInt(SymbolicInt e) {
    this.linear = new HashMap<Integer, Long>(e.linear);
    constant = e.constant;
    op = e.op;
  }

  public SymbolicInt negate() {
    Map<Integer, Long> tmpMap = new HashMap<Integer, Long>(getLinear());
    for (Map.Entry<Integer, Long> it : tmpMap.entrySet()) {
      it.setValue(-it.getValue());
    }
    return new SymbolicInt(tmpMap, -constant);
  }

  public SymbolicInt add(long l) {
    return add(l, true);
  }

  private SymbolicInt add(long l, boolean add) {
    long tmpConstant;
    if (add) {
      tmpConstant = constant + l;
    } else {  
      tmpConstant = constant - l;
    } 
    return new SymbolicInt(linear, tmpConstant);
  }

  public SymbolicInt add(SymbolicInt l) {
    return add(l, true);
  }

  private SymbolicInt add(SymbolicInt l, boolean add) {
    Map<Integer, Long> tmpLinear = new HashMap<Integer, Long>(linear);
    SymbolicInt e = (SymbolicInt) l;
    for (Map.Entry<Integer, Long> it : e.linear.entrySet()) {
      int integer = it.getKey();
      Long coeff = linear.get(integer); // 0 is default value
      if (coeff == null) {
        coeff = 0L;
      }
      long toadd = add ? coeff + it.getValue() : coeff - it.getValue();
      if (toadd == 0L) {
        tmpLinear.remove(integer);
      } else {
        tmpLinear.put(integer, toadd);
      }
    }
    if (tmpLinear.isEmpty()) {
      return null; // Shouldn't this returns the constant value?
    }
    long tmpConstant = add ? (constant + e.constant) : (constant - e.constant);
    return new SymbolicInt(tmpLinear, tmpConstant);
  }

  public SymbolicInt subtractFrom(long l) {
    SymbolicInt e = (SymbolicInt) negate();
    return new SymbolicInt(e.linear, l + e.constant);
  }

  public SymbolicInt subtract(long l) {
    return add(l, false);
  }

  public SymbolicInt subtract(SymbolicInt l) {
    return add(l, false);
  }

  public SymbolicInt multiply(long l) {
    if (l == 0) return null;
    if (l == 1) return this;
    Map<Integer, Long> tmpMap = new HashMap<Integer, Long>();
    for (Map.Entry<Integer, Long> it : linear.entrySet()) {
      int integer = it.getKey();
      tmpMap.put(integer, l * it.getValue());
    }
    return new SymbolicInt(tmpMap, l * constant);
  }

  public SymbolicInt setop(COMPARISON_OPS op) {
    if (op == COMPARISON_OPS.UN) {
      throw new RuntimeException("Cannot unset an operator");
    }
    SymbolicInt ret = new SymbolicInt(this);
    if (ret.op != COMPARISON_OPS.UN) {
      if (op == COMPARISON_OPS.EQ) { // (x op 0)==0 is same as !(x op 0)
        // ret = ret.not();
        ret.op = op;
      } else if (op != COMPARISON_OPS.NE) {
        throw new RuntimeException("Cannot process non-logical constraint.");
      }
    } else {
      ret.op = op;
    }
    return ret;
  }

  public SymbolicInt not() {
    COMPARISON_OPS retOp = COMPARISON_OPS.UN;
    if (op == COMPARISON_OPS.EQ) {
      retOp = COMPARISON_OPS.NE;
    } else if (op == COMPARISON_OPS.NE) {
      retOp = COMPARISON_OPS.EQ;
    } else if (op == COMPARISON_OPS.GT) {
      retOp = COMPARISON_OPS.LE;
    } else if (op == COMPARISON_OPS.GE) {
      retOp = COMPARISON_OPS.LT;
    } else if (op == COMPARISON_OPS.LT) {
      retOp = COMPARISON_OPS.GE;
    } else if (op == COMPARISON_OPS.LE) {
      retOp = COMPARISON_OPS.GT;
    }
    return new SymbolicInt(linear, constant, retOp);
  }

  public Constraint substitute(Map<String, Long> assignments) {
    long val = 0;
    Map<Integer, Long> retLinear = null;
    long retConstant = 0L;
    boolean isSymbolic = false;
    

    for (Map.Entry<Integer, Long> it : linear.entrySet()) {
      int key = it.getKey();
      long l = it.getValue();
      if (assignments.containsKey("x" + key)) {
        val += assignments.get("x" + key) * l;
      } else {
        isSymbolic = true;
        if (retLinear == null) {
          retLinear = new HashMap<Integer, Long>(); 
        }
        retLinear.put(key, l);
      }
    }
    
    if (retLinear != null) {
      retConstant = val + constant;
    }

    Constraint ret2 = null;
    if (!isSymbolic) {
      if (this.op == COMPARISON_OPS.EQ) {
        ret2 =
            (val == -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.NE) {
        ret2 =
            (val != -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.LE) {
        ret2 =
            (val <= -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.LT) {
        ret2 =
            (val < -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.GE) {
        ret2 =
            (val >= -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.GT) {
        ret2 =
            (val > -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else {
        return null;
      }
      return ret2;
    } else {
      return new SymbolicInt(retLinear, retConstant, op);
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Map.Entry<Integer, Long> it : linear.entrySet()) {
      int integer = it.getKey(); // Index of variable
      long l = it.getValue();
      if (first) {
        first = false;
      } else {
        sb.append('+');
      }
      if (l < 0) {
        sb.append('(');
        sb.append(l);
        sb.append(")*x");
        sb.append(integer);
      } else if (l == 1) {
        sb.append("x");
        sb.append(integer);
      } else if (l > 0) {
        sb.append(l);
        sb.append("*x");
        sb.append(integer);
      }
    }
    if (constant != 0) {
      if (constant > 0) sb.append('+');
      sb.append(constant);
    }
    if (op == COMPARISON_OPS.EQ) {
      sb.append("==");
      sb.append('0');
    } else if (op == COMPARISON_OPS.NE) {
      sb.append("!=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.LE) {
      sb.append("<=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.LT) {
      sb.append("<");
      sb.append('0');
    } else if (op == COMPARISON_OPS.GE) {
      sb.append(">=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.GT) {
      sb.append(">");
      sb.append('0');
    }
    sb.append(" at iid ");
    sb.append(iid);
    sb.append(" and index ");
    sb.append(index);
    return sb.toString();
  }

}
