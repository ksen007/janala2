package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/21/12
 * Time: 12:20 PM
 */

import gnu.trove.iterator.TIntLongIterator;
import gnu.trove.map.hash.TIntLongHashMap;

import java.io.PrintStream;

public class SymbolicInt extends Constraint {
    public enum COMPARISON_OPS {EQ, NE, GT, GE, LT, LE, UN};

    public COMPARISON_OPS op;
    public TIntLongHashMap linear;
    public long constant;

    @Override
    public void accept(ConstraintVisitor v) {
        v.visitSymbolicInt(this);
    }


    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (getClass() != o.getClass()))
            return false;
        SymbolicInt e = (SymbolicInt) o;
        return (linear.equals(e.linear)
                && (constant == e.constant)
                && (op == e.op));
    }

    public int hashCode() {
        int ret = 37;
        ret = 71 * ret + linear.hashCode();
        ret = 71 * ret + (int) constant;
            ret = 71 * ret + op.hashCode();
        return ret;
    }

    public SymbolicInt(int i) {
        linear = new TIntLongHashMap();
        linear.put(i, 1L);
        constant = 0;
        op = COMPARISON_OPS.UN;
    }


    private SymbolicInt() {
        linear = new TIntLongHashMap();
        constant = 0;
        op = COMPARISON_OPS.UN;
    }

    private SymbolicInt(SymbolicInt e) {
        this.linear = new TIntLongHashMap(e.linear);
        constant = e.constant;
        op = e.op;
    }

    public SymbolicInt negate() {
        SymbolicInt tmp = new SymbolicInt();
        for ( TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
            it.advance();
            tmp.linear.put(it.key(), -it.value());

        }
        tmp.constant = -constant;
        return tmp;
    }

    public SymbolicInt add(long l) {
        return add(l, true);
    }

    private SymbolicInt add(long l, boolean add) {
        SymbolicInt tmp = new SymbolicInt(this);
        if (add)
            tmp.constant = constant + l;
        else
            tmp.constant = constant - l;
        return tmp;
    }

    public SymbolicInt add(SymbolicInt l) {
        return add(l, true);
    }

    private SymbolicInt add(SymbolicInt l, boolean add) {
        SymbolicInt tmp = new SymbolicInt(this);
        SymbolicInt e = (SymbolicInt) l;
        for ( TIntLongIterator it = e.linear.iterator(); it.hasNext(); ) {
            it.advance();

            int integer = it.key();
            long coeff = linear.get(integer);
            long toadd;
            if (add) {
                toadd = coeff + it.value();
            } else {
                toadd = coeff - it.value();
            }
            if (toadd == 0) {
                tmp.linear.remove(integer);
            } else {
                tmp.linear.put(integer, toadd);
            }
        }
        if (tmp.linear.isEmpty()) {
            return null;
        }

        if (add)
            tmp.constant = this.constant + e.constant;
        else
            tmp.constant = this.constant - e.constant;

        return tmp;
    }

    public SymbolicInt subtractFrom(long l) {
        SymbolicInt e = (SymbolicInt) negate();
        e.constant = l + e.constant;
        return e;
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
        SymbolicInt tmp = new SymbolicInt();
        for ( TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
            it.advance();
            
            int integer = it.key();
            tmp.linear.put(integer, l * it.value());
        }
        tmp.constant = l * constant;
        return tmp;
    }

    public SymbolicInt setop(COMPARISON_OPS op) {
        SymbolicInt ret = new SymbolicInt(this);
        if (ret.op!=COMPARISON_OPS.UN) {
            if(op==COMPARISON_OPS.EQ) { // (x op 0)==0 is same as !(x op 0)
                ret.not();
            }
        } else {
            ret.op = op;
        }
        return ret;
    }

    public void not() {
        if (op == COMPARISON_OPS.EQ) op = COMPARISON_OPS.NE;
        else if (op == COMPARISON_OPS.NE) op = COMPARISON_OPS.EQ;
        else if (op == COMPARISON_OPS.GT) op = COMPARISON_OPS.LE;
        else if (op == COMPARISON_OPS.GE) op = COMPARISON_OPS.LT;
        else if (op == COMPARISON_OPS.LT) op = COMPARISON_OPS.GE;
        else if (op == COMPARISON_OPS.LE) op = COMPARISON_OPS.GT;
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for ( TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
            it.advance();
            
            int integer = it.key();
            long l = it.value();
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
            if (constant > 0)
                sb.append('+');
            sb.append(constant);
        }
        if (op == COMPARISON_OPS.EQ) {
            sb.append("==");
            sb.append('0');
        } else
        if (op == COMPARISON_OPS.NE) {
            sb.append("!=");
            sb.append('0');
        } else
        if (op == COMPARISON_OPS.LE) {
            sb.append("<=");
            sb.append('0');
        } else
        if (op == COMPARISON_OPS.LT) {
            sb.append("<");
            sb.append('0');
        } else
        if (op == COMPARISON_OPS.GE) {
            sb.append(">=");
            sb.append('0');
        } else
        if (op == COMPARISON_OPS.GT) {
            sb.append(">");
            sb.append('0');
        }
        return sb.toString();
    }

    public void print(PrintStream out) {
        out.print("(");
        if (op == COMPARISON_OPS.EQ) {
            out.print("=");
        } else
        if (op == COMPARISON_OPS.NE) {
            out.print("/=");
        } else
        if (op == COMPARISON_OPS.LE) {
            out.print("<=");
        } else
        if (op == COMPARISON_OPS.LT) {
            out.print("<");
        } else
        if (op == COMPARISON_OPS.GE) {
            out.print(">=");
        } else
        if (op == COMPARISON_OPS.GT) {
            out.print(">");
        }
        out.print(" (+ ");
        for ( TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
            it.advance();
            
            int key = it.key();
            long val = it.value();
            if (val < 0) {
                out.print("(* (- 0 ");
                out.print(-val);
                out.print(") x");
            } else {
                out.print("(* ");
                out.print(val);
                out.print(" x");
            }
            out.print(key);
            out.print(") ");
        }
        if (constant < 0) {
            out.print("(- 0 ");
            out.print(-constant);
            out.print(")");
        } else if (constant > 0) {
            out.print(constant);
        }
        out.print(") 0)");
    }

}
