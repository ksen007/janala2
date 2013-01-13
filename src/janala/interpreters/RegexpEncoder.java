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

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.IdentityHashMap;
import java.util.List;

//interface Formula {
//    String toCVC3FormulaString(String x);
//}

//class RelConstant implements Formula {
//    int varIdx;
//    char constant;
//    String op;
//
//    RelConstant(int varIdx, char constant, String op) {
//        this.varIdx = varIdx;
//        this.constant = constant;
//        this.op = op;
//    }
//
//    public String toCVC3FormulaString(String x) {
//        return " ( "+x+varIdx+" "+op+ " "+((int)constant)+" ) ";
//    }
//}
//
//class AndFormula implements Formula {
//    Formula left;
//    Formula right;
//
//    AndFormula(Formula left, Formula right) {
//        this.left = left;
//        this.right = right;
//    }
//
//    public String toCVC3FormulaString(String x) {
//        return "("+left.toCVC3FormulaString(x)+" AND "+right.toCVC3FormulaString(x)+ ")";
//    }
//}
//
//class OrFormula implements Formula {
//    Formula left;
//    Formula right;
//
//    OrFormula(Formula left, Formula right) {
//        this.left = left;
//        this.right = right;
//    }
//
//    public String toCVC3FormulaString(String x) {
//        return "("+left.toCVC3FormulaString(x)+" OR "+right.toCVC3FormulaString(x)+ ")";
//    }
//}
//
//class TrueFormula implements Formula {
//    public String toCVC3FormulaString(String x) {
//        return "TRUE";
//    }
//
//}
//
//class FalseFormula implements Formula {
//    public String toCVC3FormulaString(String x) {
//        return "FALSE";
//    }
//}

public class RegexpEncoder {
    public static Constraint getRegexpFormulaString(String regexp, String prefix, int length) {
        RegExp r = new RegExp(regexp);
        Automaton a = r.toAutomaton();
        length--;
        if (length==-1) {
            return SymbolicTrueConstraint.instance;
        } else {
            return createFormula(a, prefix, length);
        }
    }

    public static Constraint getLengthFormulaString(String regexp, String prefix, int sym, boolean accept) {
        RegExp r = new RegExp(regexp);
        Automaton a = r.toAutomaton();
        String example = a.getShortestExample(accept);
        return intCompare(prefix, sym, example.length(), SymbolicIntCompareConstraint.COMPARISON_OPS.GE);
    }


    public static Constraint createFormula(Automaton A, String prefix, int n) {
        State root = A.getInitialState();
        if (n==0) {
            if (root.isAccept()) {
                return SymbolicTrueConstraint.instance;
            } else {
                return SymbolicFalseConstraint.instance;
            }
        } else {
            Constraint ret = createFormula(root, prefix, 0,n);
            return ret==null? SymbolicFalseConstraint.instance : ret;
        }
    }

    private static Constraint intCompare(String prefix, int sym, int constant, SymbolicIntCompareConstraint.COMPARISON_OPS op) {
        return new SymbolicIntCompareConstraint(new SymOrInt(prefix+sym), new SymOrInt(constant), op);
    }

    private static Constraint andFormula(Constraint f1, Constraint f2) {
        if (f1 instanceof SymbolicAndConstraint) {
            return ((SymbolicAndConstraint)f1).AND(f2);
        } else if (f2 instanceof SymbolicAndConstraint) {
            return ((SymbolicAndConstraint)f2).AND(f1);
        } else {
            return (new SymbolicAndConstraint(f1)).AND(f2);
        }
    }

    private static Constraint orFormula(Constraint f1, Constraint f2) {
        if (f1 instanceof SymbolicOrConstraint) {
            return ((SymbolicOrConstraint)f1).OR(f2);
        } else if (f2 instanceof SymbolicOrConstraint) {
            return ((SymbolicOrConstraint)f2).OR(f1);
        } else {
            return (new SymbolicOrConstraint(f1)).OR(f2);
        }
    }


    public static Constraint createFormula(State root, String prefix, int i, int n) {
        IdentityHashMap<State, Constraint> ret = new IdentityHashMap<State, Constraint>();
        Constraint collect = null;

        List<Transition> transitions = root.getSortedTransitions(false);
        for (Transition transition : transitions) {
            State next = transition.getDest();
            Constraint tmp1 = andFormula(intCompare(prefix, i, transition.getMin(), SymbolicIntCompareConstraint.COMPARISON_OPS.GE),
                    intCompare(prefix, i, transition.getMax(), SymbolicIntCompareConstraint.COMPARISON_OPS.LE));
            Constraint tmp2 = ret.get(next);
            if (tmp2 != null) {
                Constraint tmp3 = orFormula(tmp2,tmp1);
                ret.put(next, tmp3);
            } else {
                ret.put(next,tmp1);
            }
        }
        if (i < n) {
            for (State next : ret.keySet()) {
                Constraint suffix = createFormula(next, prefix, i + 1, n);
                if (suffix != null) {
                    Constraint tmp4 = andFormula(ret.get(next), suffix);
                    if (collect == null) {
                        collect = tmp4;
                    } else {
                        collect = orFormula(collect, tmp4);
                    }
                }
            }
        } else {
            for (State next : ret.keySet()) {
                if (next.isAccept()) {
                    if (collect == null) {
                        collect = ret.get(next);
                    } else {
                        collect = orFormula(collect, ret.get(next));
                    }
                }
            }
        }
        return collect;
    }

}
