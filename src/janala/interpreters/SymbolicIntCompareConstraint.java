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

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 1/3/13
 * Time: 12:13 AM
 */
public class SymbolicIntCompareConstraint extends Constraint {

    public enum COMPARISON_OPS {EQ, NE, GT, GE, LT, LE, UN};

    public String prefix;
    public int sym;
    public long constant;
    public COMPARISON_OPS op;

    public SymbolicIntCompareConstraint(String prefix, int sym, long constant, COMPARISON_OPS op) {
        this.prefix = prefix;
        this.sym = sym;
        this.constant = constant;
        this.op = op;
    }

    public SymbolicIntCompareConstraint(SymbolicIntCompareConstraint from) {
        this.prefix = from.prefix;
        this.sym = from.sym;
        this.constant = from.constant;
        this.op = from.op;
    }

    @Override
    public void accept(ConstraintVisitor v) {
        v.visitSymbolicIntCompare(this);
    }

    @Override
    public Constraint not() {
        SymbolicIntCompareConstraint ret = new SymbolicIntCompareConstraint(this);
        if (ret.op == COMPARISON_OPS.EQ) ret.op = COMPARISON_OPS.NE;
        else if (ret.op == COMPARISON_OPS.NE) ret.op = COMPARISON_OPS.EQ;
        else if (ret.op == COMPARISON_OPS.GT) ret.op = COMPARISON_OPS.LE;
        else if (ret.op == COMPARISON_OPS.GE) ret.op = COMPARISON_OPS.LT;
        else if (ret.op == COMPARISON_OPS.LT) ret.op = COMPARISON_OPS.GE;
        else if (ret.op == COMPARISON_OPS.LE) ret.op = COMPARISON_OPS.GT;
        return ret;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
                sb.append(prefix);
                sb.append(sym);
        if (constant != 0) {
            if (constant > 0) {
                sb.append('-');
                sb.append(constant);
            } else {
                sb.append('+');
                sb.append(-constant);
            }
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

}
