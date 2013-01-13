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

//function execSync(cmd) {
//    var FFI = require("node-ffi");
//    var libc = new FFI.Library(null, {
//        "system": ["int32", ["string"]]
//    });
//
//    var run = libc.system;
//    run(cmd);
//}
//
//var stdoutCache = {};
//
//function stdout(cmd) {
//    var ret;
//    if ((ret = stdoutCache[cmd]) !== undefined) {
//        return ret;
//    }
//    //console.log(cmd);
//    execSync(cmd+" > jalangi_javaout");
//    var FileLineReader = require('./FileLineReader');
//    var fd = new FileLineReader("jalangi_javaout");
//    var line = "";
//
//    if (fd.hasNextLine()) {
//        line = fd.nextLine();
//    }
//    fd.close();
//    line = line.replace(/(\r\n|\n|\r)/gm,"")
//    //console.log("Java output:"+line);
//    stdoutCache[cmd] = line;
//    return line;
//}
//
//
//function regex_escape (text) {
//    return text.substring(1,text.length-1);
////    return text.replace(/[\\]/g, "\\\\$&");
//}
//
//function exprAt(sExpr, i, freeVars, assignments) {
//    var j, len, s, idx, tmp, length;
//    if (typeof sExpr === 'string') {
//        return sExpr.charCodeAt(i);
//    } else {
//        len = sExpr.list.length;
//        for (j=0; j<len; j++) {
//            s = sExpr.list[j];
//            if (typeof s === 'string') {
//                if (i < s.length) {
//                    return s.charCodeAt(i);
//                } else {
//                    i = i - s.length;
//                }
//            } else {
//                idx = s+"";
//                length = s.getField("length").symbolic.substitute(assignments);
//                if (i < length) {
//                    tmp = idx+"__"+i;
//                    freeVars[tmp] = true;
//                    return tmp;
//                } else {
//                    i = i - length;
//                }
//            }
//        }
//    }
//}
//
//function getStringEqualityFormula(left, right, length, freeVars, assignments) {
//    var i, sb = "(";
//
//    if (length <= 0) {
//        return "TRUE";
//    }
//    for(i=0; i<length; i++) {
//        if (i!==0) {
//            sb += " AND ";
//        }
//        sb += "("+exprAt(left,i,freeVars,assignments)+" = " + exprAt(right,i,freeVars,assignments)+")";
//    }
//    sb += ")";
//
//    return sb;
//}
//
//    not: function() {
//        var ret = new SymbolicStringPredicate(this);
//        switch(this.op) {
//            case SymbolicStringPredicate.EQ:
//                ret.op = SymbolicStringPredicate.NE;
//                break;
//            case SymbolicStringPredicate.NE:
//                ret.op = SymbolicStringPredicate.EQ;
//                break;
//            case SymbolicStringPredicate.IN:
//                ret.op = SymbolicStringPredicate.NOTIN;
//                break;
//            case SymbolicStringPredicate.NOTIN:
//                ret.op = SymbolicStringPredicate.IN;
//                break;
//        }
//        return ret;
//    },
//
//    substitute : function(assignments) {
//        return this;
//    },
//
//    getFormulaString : function(freeVars, mode, assignments) {
//        var sb = "", s1, s2, formula, cmd, length1 = 0, length2 = 0, j;
//        var classpath = __dirname+"/../jalangijava/out/production/jalangijava/:"+__dirname+"/../jalangijava/lib/automaton.jar ";
//        s1 = (typeof this.left === 'string')?this.left.length:this.left.getField("length");
//        s2 = (typeof this.right === 'string' || this.right instanceof RegExp)?this.right.length:this.right.getField("length");
////        s1 = $7.GF(0,this.left,"length");
////        s2 = $7.GF(0,this.right,"length");
//
//        if (mode === "integer") {
//            switch(this.op) {
//                case SymbolicStringPredicate.EQ:
//                    formula = $7.B(0,"==",s1,s2);
//                    return formula.symbolic.getFormulaString(freeVars,mode, assignments);
//                case SymbolicStringPredicate.NE:
//                    //formula = $7.B(0,"!=",s1,s2);
//                    //return formula.symbolic.getFormulaString(freeVars,mode, assignments);
//                    return "TRUE";
//                case SymbolicStringPredicate.IN:
//                    cmd = "java -cp " +
//                        classpath +
//                        "RegexpEncoder " +
//                        "length \""+
//                        regex_escape(this.right+"")+
//                        "\" "+s1.symbolic+" true";
//                    freeVars[s1.symbolic+""] = true;
//                    sb = stdout(cmd);
//                    break;
//                case SymbolicStringPredicate.NOTIN:
//                    cmd = "java -cp " +
//                        classpath +
//                        "RegexpEncoder " +
//                        "length \""+
//                        regex_escape(this.right+"")+
//                        "\" "+s1.symbolic+" false";
//                    freeVars[s1.symbolic+""] = true;
//                    sb = stdout(cmd);
//                    break;
//            }
//        } else if (mode === "string") {
//            switch(this.op) {
//                case SymbolicStringPredicate.EQ:
//                    if (s1.symbolic) {
//                        length1 = s1.symbolic.substitute(assignments);
//                    } else {
//                        length1 = s1;
//                    }
//                    if (s2.symbolic) {
//                        length2 = s2.symbolic.substitute(assignments);
//                    } else {
//                        length2 = s2;
//                    }
//                    if (length1 !== length2) {
//                        return "FALSE";
//                    } else {
//                        return getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments);
//                    }
//                case SymbolicStringPredicate.NE:
//                    if (s1.symbolic) {
//                        length1 = s1.symbolic.substitute(assignments);
//                    } else {
//                        length1 = s1;
//                    }
//                    if (s2.symbolic) {
//                        length2 = s2.symbolic.substitute(assignments);
//                    } else {
//                        length2 = s2;
//                    }
//                    if (length1 !== length2) {
//                        return "TRUE";
//                    } else {
//                        return "(NOT "+getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments)+")";
//                    }
////                    return (length1 !== length2)?"TRUE":"FALSE";
//                case SymbolicStringPredicate.IN:
//                    length1 = s1.symbolic.substitute(assignments);
//                    cmd = "java -cp " +
//                        classpath +
//                        "RegexpEncoder content \""+
//                        regex_escape(this.right+"")+
//                        "\" "+
//                        this.left+
//                        "__ "+length1;
//                    for(j=0; j<length1; j++) {
//                        freeVars[this.left+"__"+j] = true;
//                    }
//                    sb = stdout(cmd);
//                    break;
//                case SymbolicStringPredicate.NOTIN:
//                    length1 = s1.symbolic.substitute(assignments);
//                    cmd = "java -cp " +
//                        classpath +
//                        "RegexpEncoder content \""+
//                        "~("+regex_escape(this.right+"")+
//                        ")\" "+
//                        this.left+
//                        "__ "+length1;
//                    for(j=0; j<length1; j++) {
//                        freeVars[this.left+"__"+j] = true;
//                    }
//                    sb = stdout(cmd);
//                    break;
//            }
//
//        }
//
//        return sb;
//    },
//
//
//
//
//};

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

    public String toString() {
        switch(this.op) {
            case EQ:
                return this.left+" == " + this.right;
            case NE:
                return this.left+" != " + this.right;
            case IN:
                return this.left+" regexin " + this.right;
            case NOTIN:
                return this.left+" regexnotin " + this.right;
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
                and.AND(c);
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
                    formula = s1.IF_ICMPEQ(s2);
                    return formula.symbolic;
                case NE:
                    return SymbolicTrueConstraint.instance;
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
                        freeVars.add(this.left+"__"+j);
                    }
                    // @todo regex_escape
                    return RegexpEncoder.getRegexpFormulaString((String)this.right, "x"+this.left+"__", (int)length1);
                case NOTIN:
                    length1 = s1.substituteInLinear(assignments);
                    for(j=0; j<length1; j++) {
                        freeVars.add(this.left+"__"+j);
                    }
                    // @todo regex_escape
                    return RegexpEncoder.getRegexpFormulaString("~("+(String)this.right+")", "x"+this.left+"__", (int)length1);
            }

        }
        return null;
    }

}
