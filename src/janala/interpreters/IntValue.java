/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import gnu.trove.iterator.TIntLongIterator;
import janala.solvers.History;

import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 8:35 AM
 */
public class IntValue extends Value {
    public SymbolicInt symbolic;
    public Constraint nonIntConstraint;
    public int concrete;
    final static public IntValue TRUE = new IntValue(1);
    final static public IntValue FALSE = new IntValue(0);

    @Override
    public Object getConcrete() {
        return concrete;
    }

    public IntValue(int i) {
        concrete = i;
        symbolic = null;
        nonIntConstraint = null;
    }

//    public IntValue(int i, SymbolicInt symbolic) {
//        concrete = i;
//        this.symbolic = symbolic;
//        nonIntConstraint = null;
//    }

    public IntValue(int concrete, Constraint nonIntConstraint) {
        this.concrete = concrete;
        if (nonIntConstraint instanceof SymbolicInt) {
            this.symbolic = (SymbolicInt)nonIntConstraint;
        } else {
            this.nonIntConstraint = nonIntConstraint;
        }
    }

    public int getSymbol() {
        return symbolic.linear.keys()[0];
    }

    public int MAKE_SYMBOLIC(History history) {
        symbolic = new SymbolicInt(symbol++);
        //System.out.println("Int symbol x"+(symbol-1)+" = "+concrete);
        return symbol-1;
    }

    public long substituteInLinear(Map<String, Long> assignments) {
        long val = 0;

        if (symbolic == null) {
            return concrete;
        }
        for ( TIntLongIterator it = symbolic.linear.iterator(); it.hasNext(); ) {
            it.advance();

            int key = it.key();
            long l = it.value();
            if (assignments.containsKey("x"+key)) {
                val += assignments.get("x"+key)*l;
            } else {
                return this.concrete;
            }
        }
        val += symbolic.constant;
        return val;
    }



    public IntValue IINC(int increment) {
        IntValue ret = new IntValue(concrete+increment);
        if (symbolic!=null) {
            ret.symbolic = symbolic.add(increment);
        }
        return ret;
    }

    public IntValue IFEQ() {
        boolean result = concrete==0;
        if (symbolic==null && nonIntConstraint == null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic != null) {
            if (symbolic.op== SymbolicInt.COMPARISON_OPS.UN)
                return new IntValue(result?1:0, result?
                        symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ):
                        symbolic.setop(SymbolicInt.COMPARISON_OPS.NE));
            else
                return new IntValue(result?1:0,result?
                        (SymbolicInt)symbolic.not():
                        symbolic);
        } else {
            return new IntValue(result?1:0,result?nonIntConstraint.not():nonIntConstraint);
        }
    }

    public IntValue IFNE() {
        boolean result = concrete!=0;
        if (symbolic==null && nonIntConstraint == null) {
            return (concrete!=0)?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic != null) {
            if (symbolic.op== SymbolicInt.COMPARISON_OPS.UN)
                return new IntValue(result?1:0,result?
                        symbolic.setop(SymbolicInt.COMPARISON_OPS.NE):
                        symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ));
            else
                return new IntValue(result?1:0, result?
                        symbolic:
                        (SymbolicInt)symbolic.not());
        } else {
            return new IntValue(result?1:0,result?nonIntConstraint : nonIntConstraint.not());
        }
    }

    public IntValue IFLT() {
        if (symbolic==null) {
            return (concrete<0)?IntValue.TRUE:IntValue.FALSE;
        } else {
            boolean result = concrete<0;
            return new IntValue(result?1:0, result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LT):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GE));
        }
    }

    public IntValue IFGE() {
        if (symbolic==null) {
            return (concrete>=0)?IntValue.TRUE:IntValue.FALSE;
        } else {
            boolean result = concrete>=0;
            return new IntValue(result?1:0,result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GE):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LT));
        }
    }

    public IntValue IFGT() {
        if (symbolic==null) {
            return (concrete>0)?IntValue.TRUE:IntValue.FALSE;
        } else {
            boolean result = concrete>0;
            return new IntValue(result?1:0,result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GT):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LE));
        }
    }

    public IntValue IFLE() {
        if (symbolic==null) {
            return (concrete<=0)?IntValue.TRUE:IntValue.FALSE;
        } else {
            boolean result = concrete<=0;
            return new IntValue(result?1:0,result?
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.LE):
                    symbolic.setop(SymbolicInt.COMPARISON_OPS.GT));
        }
    }

    public IntValue IF_ICMPEQ(IntValue i2) {
        boolean result = (concrete==i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.EQ:SymbolicInt.COMPARISON_OPS.NE;
        if (symbolic==null && i2.symbolic==null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                tmp = tmp.setop(op);
            else
                tmp = null;
            IntValue ret = new IntValue(result?1:0, tmp);

            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(result?1:0,symbolic.subtract(i2.concrete).setop(op));
            return ret;
        } else {
            IntValue ret = new IntValue(result?1:0, i2.symbolic.subtractFrom(concrete).setop(op));
            return ret;
        }
    }

    public IntValue IF_ICMPNE(IntValue i2) {
        boolean result = (concrete!=i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.NE:SymbolicInt.COMPARISON_OPS.EQ;
        if (symbolic==null && i2.symbolic==null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                tmp = tmp.setop(op);
            else
                tmp = null;
            IntValue ret = new IntValue(result?1:0, tmp);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(result?1:0,symbolic.subtract(i2.concrete).setop(op));
            return ret;
        } else {
            IntValue ret = new IntValue(result?1:0,i2.symbolic.subtractFrom(concrete).setop(op));
            return ret;
        }
    }

    public IntValue IF_ICMPLT(IntValue i2) {
        boolean result = (concrete<i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.LT:SymbolicInt.COMPARISON_OPS.GE;
        if (symbolic==null && i2.symbolic==null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                tmp = tmp.setop(op);
            else
                tmp = null;
            IntValue ret = new IntValue(result?1:0, tmp);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(result?1:0,symbolic.subtract(i2.concrete).setop(op));
            return ret;
        } else {
            IntValue ret = new IntValue(result?1:0,i2.symbolic.subtractFrom(concrete).setop(op));
            return ret;
        }
    }

    public IntValue IF_ICMPGE(IntValue i2) {
        boolean result = (concrete>=i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.GE:SymbolicInt.COMPARISON_OPS.LT;
        if (symbolic==null && i2.symbolic==null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                tmp = tmp.setop(op);
            else
                tmp = null;
            IntValue ret = new IntValue(result?1:0, tmp);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(result?1:0,symbolic.subtract(i2.concrete).setop(op));
            return ret;
        } else {
            IntValue ret = new IntValue(result?1:0,i2.symbolic.subtractFrom(concrete).setop(op));
            return ret;
        }
    }

    public IntValue IF_ICMPGT(IntValue i2) {
        boolean result = (concrete>i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.GT:SymbolicInt.COMPARISON_OPS.LE;
        if (symbolic==null && i2.symbolic==null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                tmp = tmp.setop(op);
            else
                tmp = null;
            IntValue ret = new IntValue(result?1:0, tmp);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(result?1:0,symbolic.subtract(i2.concrete).setop(op));
            return ret;
        } else {
            IntValue ret = new IntValue(result?1:0,i2.symbolic.subtractFrom(concrete).setop(op));
            return ret;
        }
    }

    public IntValue IF_ICMPLE(IntValue i2) {
        boolean result = (concrete<=i2.concrete);
        SymbolicInt.COMPARISON_OPS op = result?SymbolicInt.COMPARISON_OPS.LE:SymbolicInt.COMPARISON_OPS.GT;
        if (symbolic==null && i2.symbolic==null) {
            return result?IntValue.TRUE:IntValue.FALSE;
        } else if (symbolic!=null && i2.symbolic!=null) {
            SymbolicInt tmp = symbolic.subtract(i2.symbolic);
            if (tmp!=null)
                tmp = tmp.setop(op);
            else
                tmp = null;
            IntValue ret = new IntValue(result?1:0, tmp);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(result?1:0,symbolic.subtract(i2.concrete).setop(op));
            return ret;
        } else {
            IntValue ret = new IntValue(result?1:0,i2.symbolic.subtractFrom(concrete).setop(op));
            return ret;
        }
    }

    public IntValue IADD(IntValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new IntValue(concrete+i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            IntValue ret = new IntValue(concrete+i.concrete);
            ret.symbolic = symbolic.add(i.symbolic);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(concrete+i.concrete);
            ret.symbolic = symbolic.add(i.concrete);
            return ret;
        } else {
            IntValue ret = new IntValue(concrete+i.concrete);
            ret.symbolic = i.symbolic.add(concrete);
            return ret;
        }
    }

    public IntValue ISUB(IntValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new IntValue(concrete-i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            IntValue ret = new IntValue(concrete-i.concrete);
            ret.symbolic = symbolic.subtract(i.symbolic);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(concrete-i.concrete);
            ret.symbolic = symbolic.subtract(i.concrete);
            return ret;
        } else {
            IntValue ret = new IntValue(concrete-i.concrete);
            ret.symbolic = i.symbolic.subtractFrom(concrete);
            return ret;
        }
    }

    public IntValue IMUL(IntValue i) {
        if (symbolic==null && i.symbolic==null) {
            return new IntValue(concrete*i.concrete);
        } else if (symbolic!=null && i.symbolic!=null) {
            IntValue ret = new IntValue(concrete*i.concrete);
            ret.symbolic = symbolic.multiply(i.concrete);
            return ret;
        } else if (symbolic!=null) {
            IntValue ret = new IntValue(concrete*i.concrete);
            ret.symbolic = symbolic.multiply(i.concrete);
            return ret;
        } else {
            IntValue ret = new IntValue(concrete*i.concrete);
            ret.symbolic = i.symbolic.multiply(concrete);
            return ret;
        }
    }

    public IntValue IDIV(IntValue i) {
        return new IntValue(concrete/i.concrete);
    }

    public IntValue IREM(IntValue i) {
        return new IntValue(concrete%i.concrete);
    }

    public IntValue INEG() {
        if (symbolic==null)
            return new IntValue(-concrete);
        else {
            IntValue ret = new IntValue(-concrete);
            ret.symbolic = symbolic.subtractFrom(0);
            return ret;
        }
    }

    public IntValue ISHL(IntValue i) {
        return new IntValue(concrete<<i.concrete);
    }

    public IntValue ISHR(IntValue i) {
        return new IntValue(concrete>>i.concrete);
    }

    public IntValue IUSHR(IntValue i) {
        return new IntValue(concrete>>>i.concrete);
    }

    public IntValue IAND(IntValue i) {
        return new IntValue(concrete&i.concrete);
    }

    public IntValue IOR(IntValue i) {
        return new IntValue(concrete|i.concrete);
    }

    public IntValue IXOR(IntValue i) {
        return new IntValue(concrete^i.concrete);
    }

    public LongValue I2L() {
        return new LongValue((long)concrete,symbolic);
    }

    public FloatValue I2F() {
        return new FloatValue((float )concrete);
    }

    public DoubleValue I2D() {
        return new DoubleValue((double)concrete);
    }

    public IntValue I2B() {
        return new IntValue((byte)concrete,symbolic);
    }

    public IntValue I2C() {
        return new IntValue((char)concrete,symbolic);
    }

    public IntValue I2S() {
        return new IntValue((short)concrete,symbolic);
    }

    @Override
    public String toString() {
        return "IntValue{" +
                "symbolic=" + symbolic +
                ", concrete=" + concrete +
                '}';
    }

    public Constraint getSymbolic() {
        return symbolic !=null? symbolic:(nonIntConstraint != null? nonIntConstraint : null);
    }
}
