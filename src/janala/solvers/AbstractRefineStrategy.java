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

package janala.solvers;

import janala.instrument.Coverage;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class AbstractRefineStrategy extends Strategy  {
    @Override
    public int solve(ArrayList<Element> history, int historySize, History solver) {
        int beginIndex = findUnsatBeginScopeIndex(history, historySize);
        int endIndex = findMatchingEndScopeIndex(history, historySize, beginIndex);
        int ret;
        if (beginIndex == -1) {
            Coverage.instance.commitBranches();
            System.out.println("******************* Found a real input. *************************");
        } else {
            System.out.println("******************* Found an intermediate input.  It should not be used for testing. *************************");
        }
        while ((ret = searchWithIfPossibleAssert(history, beginIndex, endIndex, historySize, solver)) == -1) {
            if (beginIndex == -1) {
                return ret;
            }
            beginIndex = findPreviousBeginScopeIndex(history, historySize, beginIndex);
            endIndex = findMatchingEndScopeIndex(history, historySize, beginIndex);
        }
        return ret;
    }

    private int findPreviousBeginScopeIndex(ArrayList<Element> history, int historySize, int beginScopeIndex) {
        int ret = -1;
        for (int i=0; i<=beginScopeIndex; i++) {
            Element tmp = history.get(i);
            if (tmp instanceof MethodElement) {
                if (i == beginScopeIndex) {
                    return ret;
                }
                if (((MethodElement)tmp).isBegin){
                    ret = i;
                }
            }
        }
        throw new RuntimeException("Should not reach here beginScopeIndex ="+beginScopeIndex+" history "+history);
    }

    private int findNextBeginScopeIndex(ArrayList<Element> history, int start, int end) {
        for (int i=start+1; i<end; i++) {
            Element tmp = history.get(i);
            if (tmp instanceof MethodElement) {
                if (((MethodElement)tmp).isBegin){
                    return i;
                } else {
                    return -1;
                }
            }
        }
        return -1;
    }

    class RemovalPair {
        int b, e;

        RemovalPair(int b, int e) {
            this.b = b;
            this.e = e;
        }
    }

    private void removeElements(ArrayList<Element> history, int low, int high, int i, int historySize) {
        int from = low;
        int bi, ei;

        LinkedList<RemovalPair> toBeRemovedRanges = new LinkedList<RemovalPair>();

        while((bi = findNextBeginScopeIndex(history, from, i)) != -1) {
            ei = findMatchingEndScopeIndex(history, i, bi);
            toBeRemovedRanges.addFirst(new RemovalPair(bi, ei));
            from = ei;
        }
        toBeRemovedRanges.addFirst(new RemovalPair(i, high));

        from = high;
        while((bi = findNextBeginScopeIndex(history, from, historySize)) != -1) {
            ei = findMatchingEndScopeIndex(history, historySize, bi);
            toBeRemovedRanges.addFirst(new RemovalPair(bi, ei));
            from = ei;
        }
        for (RemovalPair pair: toBeRemovedRanges) {
            for (int j=pair.e-1; j > pair.b; j--) {
                history.remove(j);
            }
        }
//        System.out.println(history);
    }


    private int findUnsatBeginScopeIndex(ArrayList<Element> history, int historySize) {
        for (int i=0; i<historySize; i++) {
            Element tmp = history.get(i);
            if (tmp.isInvalidScopeBegin()) {
                return i;
            }
        }
        return -1;
    }

    private int findMatchingEndScopeIndex(ArrayList<Element> history, int historySize, int beginScopeIndex) {
        int skip = 0;
        for (int i=beginScopeIndex+1; i<historySize; i++) {
            Element tmp = history.get(i);
            if (tmp instanceof MethodElement) {
                if (((MethodElement)tmp).isBegin){
                    skip++;
                } else {
                    if (skip==0) {
                        return i;
                    } else {
                        skip--;
                    }
                }
            }
        }
        return historySize;
    }

    public int searchWithIfPossibleAssert(ArrayList<Element> history, int low, int high, int historySize, History solver) {
        int to, from = low, ret;

        for (to = low+1; to < high; to++) {
            Element tmp = history.get(to);
            BranchElement current;
            if (tmp instanceof BranchElement) {
                current = (BranchElement)tmp;
                if (current.isForceTruth && !current.branch) {
                    if ((ret = dfs(history, from, to + 1, high, historySize, solver)) != -1) {
                        return ret;
                    }
                    from = to;
                } else if (current.isForceTruth) {
                    from = to;
                }
            }
        }

        return dfs(history, from, to, high, historySize, solver);
    }


    private int dfs(ArrayList<Element> history, int low, int start, int high, int historySize, History solver) {
        LinkedList<Integer> indices = new LinkedList<Integer>();
        int skip = 0;
        for (int i= start -1; i > low; i--) {
            Element tmp = history.get(i);
            if (tmp instanceof MethodElement) {
                if (((MethodElement)tmp).isBegin)
                    skip++;
                else
                    skip--;
            }
            if (tmp instanceof BranchElement && skip == 0) {
                indices.addLast(i);
            }
        }
        for (int i: indices) {
            Element tmp = history.get(i);
            if (tmp instanceof BranchElement) {
                BranchElement current = (BranchElement) tmp;
                if (!current.done && current.pathConstraintIndex != -1) {
                    if (solver.solveAt(low, i)) {
                        current.done = true;
                        current.branch = !current.branch;
                        removeElements(history, low, high, i, historySize);
                        return Integer.MAX_VALUE;
                    }
                }
            }
        }
        return -1;
    }

}
