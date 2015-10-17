package janala.interpreters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;

public final class SymbolicStringExpression {
  private final LinkedList list; // either String or SymbolicStringVar

  public SymbolicStringExpression(int sym, IntValue length) {
    this.list = new LinkedList();
    this.list.addLast(new SymbolicStringVar(sym, length));
  }

  public SymbolicStringExpression(SymbolicStringExpression sym) {
    this.list = new LinkedList();
    this.list.addAll(sym.list);
  }

  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (this == o) {
      return true;
    } else if (o instanceof SymbolicStringExpression) {
      SymbolicStringExpression other = (SymbolicStringExpression)o;
      return list.equals(other.list);
    } else {
      return false;
    }
  }
  
  public SymbolicStringExpression concatStr(String str) {
    SymbolicStringExpression ret = new SymbolicStringExpression(this);
    ret.addLast(str);
    return ret;
  }

  private void addLast(String entry) {
    Object last = list.getLast();
    if (last instanceof String) {
      list.removeLast();
      list.addLast(last + entry);
    } else {
      list.addLast(entry);
    }
  }

  public SymbolicStringExpression concat(SymbolicStringExpression expr) {
    SymbolicStringExpression ret = new SymbolicStringExpression(this);
    Object last = ret.list.getLast();
    Object first = expr.list.getFirst();
    if (first instanceof String) {
      ret.addLast((String)first);
    } else {
      ret.list.addLast(first);
    }
    
    ret.list.addAll(expr.list.subList(1, expr.list.size()));
    return ret;
  }

  public SymbolicStringExpression concatToStr(String str) {
    SymbolicStringExpression ret = new SymbolicStringExpression(this);
    Object first = ret.list.getFirst();
    if (first instanceof String) {
      ret.list.removeFirst();
      ret.list.addFirst(str + first);
    } else {
      ret.list.addFirst(str);
    }
    return ret;
  }

  public boolean isCompound() {
    return this.list.size() > 1;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean flag = true;
    for (Object elem : this.list) {
      if (flag) {
        flag = false;
      } else {
        sb.append('+');
      }
      if (elem instanceof SymbolicStringVar) {
        sb.append(elem.toString());
      } else { 
        sb.append('"');
        sb.append((String)elem);
        sb.append('"');
      }
    }
    return sb.toString();
  }

  public SymbolicStringExpression substitute(ArrayList<Value> assignments) {
    return this;
  }

  public IntValue getField(String offset) {
    if (offset.equals("length")) {
      IntValue ret = null, len;
      for (Object val : this.list) {
        if (val instanceof String) {
          len = new IntValue(((String) val).length());
        } else if (val instanceof SymbolicStringVar) {
          len = (IntValue) ((SymbolicStringVar) val).getField("length");
        } else {
          throw new RuntimeException("Unsupported string type.");
        }
        if (ret == null) {
          ret = len;
        } else {
          ret = ret.IADD(len);
        }
      }
      return ret;
    }
    throw new RuntimeException("Not implemented");
  }

  public SymOrInt getExprAt(int i, Set<String> freeVars, Map<String, Long> assignments) {
    for (Object s : list) {
      if (s instanceof String) {
        if (i < ((String) s).length()) {
          return new SymOrInt(((String) s).charAt(i));
        } else {
          i = i - ((String) s).length();
        }
      } else {
        String idx = s.toString();
        int length = (int) ((SymbolicStringVar) s)
          .getField("length").substituteInLinear(assignments);
        if (i < length) {
          freeVars.add("x" + idx + "__" + i);
          return new SymOrInt("x" + idx + "__" + i);
        } else {
          i = i - length;
        }
      }
    }
    throw new RuntimeException("Cannot find the exp");
  }
}
