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

package tests;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.IdentityHashMap;
import java.util.List;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 11/29/12
 * Time: 1:54 PM
 */

interface Formula {
    String toCVC3FormulaString(String x);
}

class RelConstant implements Formula {
    int varIdx;
    char constant;
    String op;

    RelConstant(int varIdx, char constant, String op) {
        this.varIdx = varIdx;
        this.constant = constant;
        this.op = op;
    }

    public String toCVC3FormulaString(String x) {
        return " ( "+x+varIdx+" "+op+ " "+((int)constant)+" ) ";
    }
}

class AndFormula implements Formula {
    Formula left;
    Formula right;

    AndFormula(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public String toCVC3FormulaString(String x) {
        return " ( "+left.toCVC3FormulaString(x)+" AND "+right.toCVC3FormulaString(x)+ " ) ";
    }
}

class OrFormula implements Formula {
    Formula left;
    Formula right;

    OrFormula(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public String toCVC3FormulaString(String x) {
        return " ( "+left.toCVC3FormulaString(x)+" OR "+right.toCVC3FormulaString(x)+ " ) ";
    }
}

class TrueFormula implements Formula {
    public String toCVC3FormulaString(String x) {
        return " TRUE ";
    }

}

class FalseFormula implements Formula {
    public String toCVC3FormulaString(String x) {
        return " FALSE ";
    }
}

public class AutomatonTest1 {
    public static void main(String[] args) {
        RegExp r = new RegExp("^(([^:]+)://)?([^:/]+)(:([0-9]+))?(/.*)");
        Automaton a = r.toAutomaton();
        System.out.println(a.toDot());
        System.out.println(toCVC3FormulaString(a,"x",2));
    }


    public static String toCVC3FormulaString(Automaton A, String x, int n) {
        Formula tmp = createFormula(A, n);
        String ret = tmp.toCVC3FormulaString(x);
        return ret;
    }

    public static Formula createFormula(Automaton A, int n) {
        State root = A.getInitialState();
        if (n==0) {
            if (root.isAccept()) {
                return new TrueFormula();
            } else {
                return new FalseFormula();
            }
        } else {
            Formula ret = createFormula(root,1,n);
            return ret==null? new FalseFormula() : ret;
        }
    }

    public static Formula createFormula(State root, int i, int n) {
        IdentityHashMap<State, Formula> ret = new IdentityHashMap<State, Formula>();
        Formula collect = null;

        List<Transition> transitions = root.getSortedTransitions(false);
        for (Transition transition : transitions) {
            State next = transition.getDest();
            AndFormula tmp1 = new AndFormula(new RelConstant(i,transition.getMin(),">="), new RelConstant(i,transition.getMax(),"<="));
            Formula tmp2 = ret.get(next);
            if (tmp2 != null) {
                OrFormula tmp3 = new OrFormula(tmp2,tmp1);
                ret.put(next, tmp3);
            } else {
                ret.put(next,tmp1);
            }
        }
        if (i < n) {
            for (State next : ret.keySet()) {
                Formula suffix = createFormula(next, i + 1, n);
                if (suffix != null) {
                    Formula tmp4 = new AndFormula(ret.get(next), suffix);
                    if (collect == null) {
                        collect = tmp4;
                    } else {
                        collect = new OrFormula(collect, tmp4);
                    }
                }
            }
        } else {
            for (State next : ret.keySet()) {
                if (next.isAccept()) {
                    if (collect == null) {
                        collect = ret.get(next);
                    } else {
                        collect = new OrFormula(collect, ret.get(next));
                    }
                }
            }
        }
        return collect;
    }

}
