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

package janala.solvers;

import janala.utils.MyLogger;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomPathStrategy extends Strategy {
    Random rand = new Random();
    public final static int BOUND = 2;
    private final static Logger logger = MyLogger.getLogger(RandomPathStrategy.class.getName());

    @Override
    public int solve(ArrayList<Element> history, int historySize, History solver) {
        int begin = -1;
        for(int j=historySize-1; j>=0; j--) {
            Element tmp = history.get(j);
            if (tmp instanceof BranchElement){
                BranchElement current = (BranchElement)tmp;
                if (current.done) {
                    begin = j;
                    break;
                }
            }
        }
        begin = (begin+1)%historySize;

        int repeat = 0;
        for (int i=begin; true; i = ((i+1)%historySize)) {
            if (i==0) {
                if (repeat>BOUND) {
                    logger.log(Level.INFO,"Ending random search");
                    return -1;
                }
                repeat++;
            }
            if (rand.nextBoolean()) {
                Element tmp = history.get(i);
                if (tmp instanceof BranchElement) {
                    BranchElement current = (BranchElement)tmp;
                    if (current.pathConstraintIndex != -1) {
                        if (solver.solveAt(current.pathConstraintIndex)) {
                            return i;
                        }
                    }
                }
            }
        }
        //return -1;
    }
}
