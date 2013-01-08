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

import java.util.LinkedList;
import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/8/12
 * Time: 1:46 PM
 */
public class SymbolicOrConstraint extends Constraint {
    public LinkedList<Constraint> constraints;

    private SymbolicOrConstraint() {

    }

    public SymbolicOrConstraint(Constraint c) {
        constraints = new LinkedList<Constraint>();
        if (c!=null)
            constraints.add(c);
    }

    private SymbolicOrConstraint(SymbolicOrConstraint c) {
        constraints = new LinkedList<Constraint>();
        constraints.addAll(c.constraints);
    }

    public SymbolicOrConstraint OR(Constraint c) {
        if (c!=null) {
            SymbolicOrConstraint ret = new SymbolicOrConstraint(this);
            ret.constraints.add(c);
            return ret;
        } else {
            return this;
        }
    }

    @Override
    public void accept(ConstraintVisitor v) {
        v.visitSymbolicOr(this);
    }

    @Override
    public Constraint not() {
        return new SymbolicNotConstraint(this);
    }

    @Override
    public Constraint substitute(Map<String, Long> assignments) {
        LinkedList<Constraint> tmp = new LinkedList<Constraint>();
        Constraint c2;
        if (constraints.isEmpty()) {
            return SymbolicTrueConstraint.instance;
        }
        for(Constraint c: constraints) {
            c2 = c.substitute(assignments);
            if (c2 == SymbolicTrueConstraint.instance) {
                return SymbolicTrueConstraint.instance;
            } else if (c2 != SymbolicFalseConstraint.instance) {
                tmp.add(c2);
            }
        }
        if (!tmp.isEmpty()) {
            SymbolicOrConstraint ret = new SymbolicOrConstraint();
            ret.constraints = tmp;
            return ret;
        } else {
            return SymbolicFalseConstraint.instance;
        }
    }

//    @Override
//    public void print(PrintStream out) {
//        if (isNegated) {
//            out.print("(not (or ");
//        } else {
//            out.print("(or ");
//        }
//        for(Constraint c:constraints) {
//            c.print(out);
//            out.print(" ");
//        }
//        if (isNegated) {
//            out.print("))");
//        } else {
//            out.print(")");
//        }
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Constraint c:constraints) {
            if (first) {
                first = false;
            } else {
                sb.append(" || ");
            }
            sb.append("(");
            sb.append(c);
            sb.append(")");
        }
        return sb.toString();
    }
}
