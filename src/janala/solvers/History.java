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

import janala.config.Config;
import janala.interpreters.Constraint;
import janala.interpreters.Value;
import janala.utils.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 2:29 PM
 */
public class History {
    private ArrayList<BranchElement> history;
    private ArrayList<Constraint> pathConstraint;
    private int index;
    private Solver solver;
    private final static Logger logger = MyLogger.getLogger(History.class.getName());
    private final static Logger tester = MyLogger.getTestLogger(Config.mainClass+"."+Config.iteration);
    private boolean ignore;

    private LinkedHashMap<Integer,Value> inputs;
//    private ArrayList<Value> inputs;
    private Strategy strategy = Config.instance.getStrategy();


    private History(Solver solver) {
        history = new ArrayList<BranchElement>(1024);
        pathConstraint = new ArrayList<Constraint>(1024);
        inputs = new LinkedHashMap<Integer,Value>();
        index = 0;
        this.solver = solver;
        this.ignore = false;
    }

    public static History readHistory(Solver solver) {
        ObjectInputStream inputStream = null;
        History ret = new History(solver);

        try {
            inputStream = new ObjectInputStream(new FileInputStream(Config.instance.history));
            Object tmp = inputStream.readObject();
            if (tmp instanceof ArrayList) {
                ret.history = (ArrayList)tmp;
            }
        } catch (Exception e) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                logger.log(Level.WARNING, "", ex);
            }
        }
        return ret;
    }

    public void checkAndSetBranch(boolean result, Constraint constraint, int iid) {
        BranchElement current;
        if (index < history.size()) {
            current = history.get(index);
            if (!ignore && current.branch != result) {
                tester.log(Level.INFO,"Prediction failed");
                logger.log(Level.WARNING,"!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "
                        +index+" history.size() "+history.size());
                logger.log(Level.WARNING,"At old iid "+current.iid+ " at iid "+iid+ " constraint "+constraint);
                int len = history.size();
                for (int j=len-1; j>index; j--) {
                    history.remove(j);
                }
                current.branch = result;
                current.done = false;
                current.iid = iid;
            }
        } else {
            current = new BranchElement(result,false,-1,iid);
            history.add(current);
        }
        if (constraint!=null) {
            constraint.iid = iid;
            constraint.index = index;
            pathConstraint.add(constraint);
            current.pathConstraintIndex = pathConstraint.size()-1;
        } else {
            current.pathConstraintIndex = -1;
        }
        if (ignore) {
            ignore = false;
        }
//        current.branch = result.result;
        index++;
    }

    public void solveAndSave() {
        int i = 0;
        if (Config.instance.printConstraints) {
            for(Constraint c:pathConstraint) {
                System.out.println(i+":"+c);
                i++;
            }
        }
        if ((i=strategy.solve(history,index,this))>=0) {
            writeHistory(i);
        } else {
            removeHistory();
        }
    }

    boolean solveAt(int pathConstraintIndex) {
        solver.setInputs(inputs);
        solver.setPathConstraint(pathConstraint);
        solver.setPathConstraintIndex(pathConstraintIndex);
        for (int i=pathConstraintIndex; i>=0; i--) {
            pathConstraint.get(i).accept(solver);
        }
        return solver.solve();
    }

    private void removeHistory() {
        File f = new File(Config.instance.history);
        f.delete();
        logger.log(Level.INFO, "Done with search.");
    }

    private void writeHistory(int i) {
        BranchElement current = history.get(i);
        current.done = true;
        current.branch = !current.branch;
        int len = history.size();
        for (int j=len-1; j>i; j--) {
            history.remove(j);
        }
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(Config.instance.history));
            outputStream.writeObject(history);
            outputStream.close();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
            System.exit(1);
        }

    }

    public void setLastBranchDone() {
        if (index>=1 && index-1<history.size()) {
            history.get(index-1).done = true;
        }
    }

    public Constraint removeLastBranch() {
        index--;
        BranchElement current = history.get(index);
        Constraint ret = null;
        if (current.pathConstraintIndex!=-1) {
            ret =  pathConstraint.remove(pathConstraint.size()-1);
        }
        if (index==history.size()-1) {
            history.remove(history.size()-1);
        }
        return ret;
    }

    public void setIgnore() {
        ignore = true;
    }

    public void addInput(int symbol, Value value) {
        inputs.put(symbol, value);
    }
}
