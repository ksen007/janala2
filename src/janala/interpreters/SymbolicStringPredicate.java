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

import java.util.ArrayList;

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

//    public Constraint getFormulaString(ArrayList<String> freeVars, String mode, ArrayList<Value> assignments) {
//        StringBuilder sb = new StringBuilder();
//        //, s1, s2, formula, cmd, length1 = 0, length2 = 0, j;
//        IntValue s1 = (this.left instanceof String)?
//                new IntValue(((String)this.left).length()):
//                ((SymbolicStringExpression)this.left).getField("length");
//        IntValue s2 = (this.right instanceof String)?
//                new IntValue(((String)this.right).length()):
//                ((SymbolicStringExpression)this.right).getField("length");
//        IntValue formula;
////        s1 = $7.GF(0,this.left,"length");
////        s2 = $7.GF(0,this.right,"length");
//
//        if (mode.equals("integer")) {
//            switch(this.op) {
//                case EQ:
//                    formula = s1.IF_ICMPEQ(s2);
//                    return formula.symbolic;
//                case NE:
//                    //formula = $7.B(0,"!=",s1,s2);
//                    //return formula.symbolic.getFormulaString(freeVars,mode, assignments);
//                    return "TRUE";
//                case IN:
//                    RegexpEncoder.
//                    cmd = "java -cp " +
//                        classpath +
//                        "RegexpEncoder " +
//                        "length \""+
//                        regex_escape(this.right+"")+
//                        "\" "+s1.symbolic+" true";
//                    freeVars[s1.symbolic+""] = true;
//                    sb = stdout(cmd);
//                    break;
//                case NOTIN:
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
//    }

}
