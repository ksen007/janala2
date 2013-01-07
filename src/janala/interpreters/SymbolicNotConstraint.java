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
 * Date: 1/2/13
 * Time: 10:52 PM
 */
public class SymbolicNotConstraint extends Constraint {
        public Constraint constraint;

        public SymbolicNotConstraint(Constraint c) {
            constraint = c;
        }

        @Override
        public void accept(ConstraintVisitor v) {
            v.visitSymbolicNot(this);
        }

        @Override
        public Constraint not() {
            return constraint;
        }

    @Override
    public Constraint substitute(Map<String, Long> assignments) {
        Constraint constraint = this.constraint.substitute(assignments);

        if (constraint == SymbolicTrueConstraint.instance) {
            return SymbolicFalseConstraint.instance;
        } else if (constraint == SymbolicFalseConstraint.instance) {
            return SymbolicTrueConstraint.instance;
        } else if (constraint == this.constraint) {
            return this;
        } else {
            return new SymbolicNotConstraint(constraint);
        }

    }

    @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(" ! ");
            sb.append(constraint);
            return sb.toString();
        }
}
