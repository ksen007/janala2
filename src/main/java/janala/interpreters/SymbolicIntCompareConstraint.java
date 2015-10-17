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

import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 1/3/13
 * Time: 12:13 AM
 */
public class SymbolicIntCompareConstraint extends Constraint {

    public enum COMPARISON_OPS {EQ, NE, GT, GE, LT, LE, UN};

    public SymOrInt left;
    public SymOrInt right;
    public COMPARISON_OPS op;

    public SymbolicIntCompareConstraint(SymOrInt left, SymOrInt right, COMPARISON_OPS op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public SymbolicIntCompareConstraint(SymbolicIntCompareConstraint from) {
        this.left = from.left;
        this.right = from.right;
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

    public Constraint substitute(Map<String, Long> assignments) {
        long val;
        SymOrInt tmp1, tmp2;
        Constraint ret2 = null;

        if (left.sym != null && assignments.containsKey(left.sym)) {
            tmp1 = new SymOrInt(assignments.get(left.sym));
        } else {
            tmp1 = left;
        }
        if (right.sym != null && assignments.containsKey(right.sym)) {
            tmp2 = new SymOrInt(assignments.get(right.sym));
        } else {
            tmp2 = right;
        }

        if (tmp1.sym == null && tmp2.sym == null) {
            val = tmp1.constant - tmp2.constant;
        } else {
            return new SymbolicIntCompareConstraint(tmp1,tmp2,this.op);
        }

        if (this.op == COMPARISON_OPS.EQ) {
            ret2 = (val == 0)?SymbolicTrueConstraint.instance:SymbolicFalseConstraint.instance;
        } else if (this.op == COMPARISON_OPS.NE) {
            ret2 = (val != 0)?SymbolicTrueConstraint.instance:SymbolicFalseConstraint.instance;
        } else if (this.op == COMPARISON_OPS.LE) {
            ret2 = (val <= 0)?SymbolicTrueConstraint.instance:SymbolicFalseConstraint.instance;
        } else if (this.op == COMPARISON_OPS.LT) {
            ret2 = (val < 0)?SymbolicTrueConstraint.instance:SymbolicFalseConstraint.instance;
        } else if (this.op == COMPARISON_OPS.GE) {
            ret2 = (val >= 0)?SymbolicTrueConstraint.instance:SymbolicFalseConstraint.instance;
        } else if (this.op == COMPARISON_OPS.GT) {
            ret2 = (val > 0)?SymbolicTrueConstraint.instance:SymbolicFalseConstraint.instance;
        }
        return ret2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(left);
        sb.append('-');
        sb.append(right);
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
