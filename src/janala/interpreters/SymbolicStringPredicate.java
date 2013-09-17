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
 */

import janala.solvers.CVC3Solver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;


public class SymbolicStringPredicate extends Constraint {

    public enum COMPARISON_OPS {EQ, NE, IN, NOTIN};

    COMPARISON_OPS op;
    Object left;
    Object right;

    public SymbolicStringPredicate(COMPARISON_OPS op, Object left, Object right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public SymbolicStringPredicate(SymbolicStringPredicate other) {
        this.op = other.op;
        this.left = other.left;
        this.right = other.right;
    }

    @Override
    public void accept(ConstraintVisitor v) {
        v.visitSymbolicStringPredicate(this);
    }

    @Override
    public Constraint not() {
        SymbolicStringPredicate ret = new SymbolicStringPredicate(this);
        switch(this.op) {
            case EQ:
                ret.op = COMPARISON_OPS.NE;
                break;
            case NE:
                ret.op = COMPARISON_OPS.EQ;
                break;
            case IN:
                ret.op = COMPARISON_OPS.NOTIN;
                break;
            case NOTIN:
                ret.op = COMPARISON_OPS.IN;
                break;
        }
        return ret;
    }

    @Override
    public Constraint substitute(Map<String, Long> assignments) {
        return this;
    }

    public Constraint substitute(ArrayList<Value> assignments) {
        return this;
    }

    private String stringfy(Object s) {
        if (s instanceof String) {
            return "\""+s+"\"";
        } else {
            return s.toString();
        }

    }

    public String toString() {
        switch(this.op) {
            case EQ:
                return stringfy(this.left)+" == " + stringfy(this.right);
            case NE:
                return stringfy(this.left)+" != " + stringfy(this.right);
            case IN:
                return stringfy(this.left)+" regexin " + stringfy(this.right);
            case NOTIN:
                return stringfy(this.left)+" regexnotin " + stringfy(this.right);
        }
        return null;
    }

    class ExprAt {
        public boolean isSymbolic;
        public String prefix;
        public int symOrVal;

        ExprAt(boolean symbolic, String prefix, int symOrVal) {
            isSymbolic = symbolic;
            this.prefix = prefix;
            this.symOrVal = symOrVal;
        }
    }

    private SymOrInt exprAt(Object sExpr, int i, LinkedHashSet<String> freeVars, Map<String, Long> assignments) {
        //var j, len, s, idx, tmp, length;
        if (sExpr instanceof String) {
            return new SymOrInt(((String)sExpr).charAt(i));
        } else {
            SymbolicStringExpression tmp = (SymbolicStringExpression) sExpr;
            int len = tmp.list.size();
            for (int j=0; j<len; j++) {
                Object s = tmp.list.get(j);
                if (s instanceof String) {
                    if (i < ((String) s).length()) {
                        return new SymOrInt(((String)s).charAt(i));
                    } else {
                        i = i - ((String) s).length();
                    }
                } else {
                    String idx = s.toString();
                    int length = (int)((SymbolicStringVar)s).getField("length").substituteInLinear(assignments);
                    if (i < length) {
                        freeVars.add("x"+idx+"__"+i);
                        return new SymOrInt("x"+idx+"__"+i);
                    } else {
                        i = i - length;
                    }
                }
            }
        }
        return null;
    }


    private Constraint getStringEqualityFormula(Object left, Object right, long length, LinkedHashSet<String> freeVars, Map<String, Long> assignments) {
        SymbolicAndConstraint and = null;

        if (length <= 0) {
            return SymbolicTrueConstraint.instance;
        }
        for(int i=0; i<length; i++) {
            SymOrInt e1 = exprAt(left,i,freeVars,assignments);
            SymOrInt e2 = exprAt(right,i,freeVars,assignments);
            Constraint c;
            c = new SymbolicIntCompareConstraint(e1,e2,SymbolicIntCompareConstraint.COMPARISON_OPS.EQ);

            if (i !=0 ) {
                and = and.AND(c);
            } else {
                and = new SymbolicAndConstraint(c);
            }
        }
        return and;
    }

    public Constraint getFormula(LinkedHashSet<String> freeVars, CVC3Solver.CONSTRAINT_TYPE mode, Map<String, Long> assignments) {
        StringBuilder sb = new StringBuilder();
        long length1, length2;
        int j;
        //, s1, s2, formula, cmd, length1 = 0, length2 = 0, j;
        IntValue s1 = (this.left instanceof String)?
                new IntValue(((String)this.left).length()):
                ((SymbolicStringExpression)this.left).getField("length");
        IntValue s2 = (this.right instanceof String)?
                new IntValue(((String)this.right).length()):
                ((SymbolicStringExpression)this.right).getField("length");
        IntValue formula;

        if (mode == CVC3Solver.CONSTRAINT_TYPE.INT) {
            switch(this.op) {
                case EQ:
                    formula = s1.ISUB(s2);
                    return formula.symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
                case NE:
                    SymbolicInt int1 = s1.symbolic != null?s1.symbolic.setop(SymbolicInt.COMPARISON_OPS.GT):null;
                    SymbolicInt int2 = s2.symbolic != null?s2.symbolic.setop(SymbolicInt.COMPARISON_OPS.GT):null;
                    if (int1 != null && int2 != null) {
                        SymbolicAndConstraint ret = new SymbolicAndConstraint(int1);
                        ret = ret.AND(int2);
                        return ret;
                    } else if (int1 != null) {
                        return int1;
                    } else if (int2 != null) {
                        return int2;
                    } else {
                        return SymbolicTrueConstraint.instance;
                    }
//                    return SymbolicTrueConstraint.instance;
                case IN:
                    // @todo regex_escape
                    return RegexpEncoder.getLengthFormulaString((String)this.right,"x", s1.getSymbol(),true);
                case NOTIN:
                    // @todo regex_escape
                    return RegexpEncoder.getLengthFormulaString((String) this.right, "x", s1.getSymbol(), false);
            }
        } else if (mode == CVC3Solver.CONSTRAINT_TYPE.STR) {
            switch(this.op) {
                case EQ:
                    if (s1.symbolic != null) {
                        length1 = s1.substituteInLinear(assignments);
                    } else {
                        length1 = s1.concrete;
                    }
                    if (s2.symbolic != null) {
                        length2 = s2.substituteInLinear(assignments);
                    } else {
                        length2 = s2.concrete;
                    }
                    if (length1 != length2) {
                        return SymbolicFalseConstraint.instance;
                    } else {
                        return getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments);
                    }
                case NE:
                    if (s1.symbolic != null) {
                        length1 = s1.substituteInLinear(assignments);
                    } else {
                        length1 = s1.concrete;
                    }
                    if (s2.symbolic != null) {
                        length2 = s2.substituteInLinear(assignments);
                    } else {
                        length2 = s2.concrete;
                    }
                    if (length1 != length2) {
                        return SymbolicTrueConstraint.instance;
                    } else {
                        return new SymbolicNotConstraint(getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments));
                    }
//                    return (length1 !== length2)?"TRUE":"FALSE";
                case IN:
                    length1 = s1.substituteInLinear(assignments);
                    for(j=0; j<length1; j++) {
                        freeVars.add("x"+this.left+"__"+j);
                    }
                    // @todo regex_escape
                    return RegexpEncoder.getRegexpFormulaString((String)this.right, "x"+this.left+"__", (int)length1);
                case NOTIN:
                    length1 = s1.substituteInLinear(assignments);
                    for(j=0; j<length1; j++) {
                        freeVars.add("x"+this.left+"__"+j);
                    }
                    // @todo regex_escape
                    return RegexpEncoder.getRegexpFormulaString("~("+(String)this.right+")", "x"+this.left+"__", (int)length1);
            }

        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SymbolicStringPredicate))
            return false;
        SymbolicStringPredicate tmp = (SymbolicStringPredicate)o;
        if (this.op != tmp.op) return false;
        String s1 = stringfy(left);
        String s2 = stringfy(right);

        String s3 = stringfy(tmp.left);
        String s4 = stringfy(tmp.right);

        if (s1.equals(s3) && s2.equals(s4)) return true;
        if (s1.equals(s4) && s2.equals(s3)) return true;
        return false;
    }
}
