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
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/22/12
 * Time: 2:29 PM
 */
public class History {
    private ArrayList<Element> history;
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
        history = new ArrayList<Element>(1024);
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

    private boolean isEnd(Element tmp) {
        return tmp instanceof MethodElement && !((MethodElement)tmp).isBegin;
    }

    Stack<MethodElement> scopeStack = new Stack<MethodElement>();
    MethodElement lastScope;
    int skip = 0;

    public void beginScope(int iid) {
        MethodElement current;
        if (index < history.size()) {
            Element tmp = history.get(index);
            if (isEnd(tmp)) {
                current = new MethodElement(true,iid);
                history.add(index, current);
                skip++;
            } else if (!ignore && (!(tmp instanceof MethodElement) || !((MethodElement)tmp).isBegin)) {
                tester.log(Level.INFO,"Prediction failed");
                logger.log(Level.WARNING,"!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "
                        +index+" history.size() "+history.size());
                logger.log(Level.WARNING,"At old iid "+tmp.iid+ " at iid "+iid+ " beginScope");
                int len = history.size();
                for (int j=len-1; j>=index; j--) {
                    history.remove(j);
                }
                current = new MethodElement(true, iid);
                history.add(current);
            } else {
                current = (MethodElement) tmp;
                current.isValidExpansion = true;
            }
        } else {
            current = new MethodElement(true,iid);
            history.add(current);
        }
        scopeStack.push(current);
        index++;
    }

    public void endScope(int iid) {
        MethodElement current;
        if (index < history.size()) {
            Element tmp = history.get(index);
            if (isEnd(tmp) && skip > 0) {
                current = new MethodElement(false,iid);
                history.add(index, current);
                skip--;
            } else if (!ignore && (!(tmp instanceof MethodElement) || ((MethodElement)tmp).isBegin)) {
                tester.log(Level.INFO,"Prediction failed");
                logger.log(Level.WARNING,"!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "
                        +index+" history.size() "+history.size());
                logger.log(Level.WARNING,"At old iid "+tmp.iid+ " at iid "+iid+ " endScope");
                int len = history.size();
                for (int j=len-1; j>=index; j--) {
                    history.remove(j);
                }
                current = new MethodElement(false, iid);
                history.add(current);
            } else {
                current = (MethodElement) tmp;
            }
        } else {
            current = new MethodElement(false,iid);
            history.add(current);
        }
        lastScope = scopeStack.pop();
        index++;

    }

    public void abstractData(boolean isEqual, int iid) {
        lastScope.isValidExpansion = lastScope.isValidExpansion && isEqual;
    }

    public void checkAndSetBranch(boolean result, Constraint constraint, int iid) {
        BranchElement current;
        if (index < history.size()) {
            Element tmp = history.get(index);
            if (isEnd(tmp)) {
                current = new BranchElement(result,false,-1,iid);
                history.add(index, current);
            } else if (!ignore && (!(tmp instanceof BranchElement) || ((BranchElement)tmp).branch != result)) {
                tester.log(Level.INFO,"Prediction failed "+ignore);
                logger.log(Level.WARNING,"!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "
                        +index+" history.size() "+history.size());
                logger.log(Level.WARNING,"At old iid "+tmp.iid+ " at iid "+iid+ " constraint "+constraint);
                int len = history.size();
                for (int j=len-1; j>=index; j--) {
                    history.remove(j);
                }
                current = new BranchElement(result,false,-1,iid);
                history.add(current);
            } else {
                current = (BranchElement) tmp;
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

    private ArrayList<Constraint> collectPathConstraints(int head, int n) {
        ArrayList<Constraint> ret = new ArrayList<Constraint>();
        for (int i=0; i<= head; i++) {
            Element tmp = history.get(i);
            if (tmp instanceof BranchElement) {
                BranchElement current = (BranchElement) tmp;
                if (current.pathConstraintIndex != -1) {
                    ret.add(pathConstraint.get(current.pathConstraintIndex));
                }
            }
        }
        int skip = 0;
        for (int i=head+1; i<= n; i++) {
            Element tmp = history.get(i);
            if (tmp instanceof BranchElement) {
                BranchElement current = (BranchElement) tmp;
                if (skip == 0 && current.pathConstraintIndex != -1) {
                    ret.add(pathConstraint.get(current.pathConstraintIndex));
                }
            } else if (tmp instanceof MethodElement) {
                MethodElement melem = (MethodElement) tmp;
                if (melem.isBegin) {
                    skip++;
                } else {
                    skip--;
                }
            }
        }
        return ret;
    }

    boolean solveAt(int head, int pathConstraintIndex) {
        ArrayList<Constraint> pathConstraint;
        solver.setInputs(inputs);
        solver.setPathConstraint(pathConstraint = collectPathConstraints(head, pathConstraintIndex));
        solver.setPathConstraintIndex(pathConstraint.size()-1);
        for (int i=pathConstraint.size()-1; i>=0; i--) {
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
        if (i != Integer.MAX_VALUE) {
            BranchElement current = (BranchElement)history.get(i);
            current.done = true;
            current.branch = !current.branch;
            int len = history.size();
            for (int j=len-1; j>i; j--) {
                history.remove(j);
            }
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
            ((BranchElement)history.get(index-1)).done = true;
        }
    }

    public Constraint removeLastBranch() {
        index--;
        BranchElement current = (BranchElement)history.get(index);
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

    public void setLastForceTruth() {
        if (index>=1 && index-1<history.size()) {
            ((BranchElement)history.get(index-1)).isForceTruth = true;
        }
    }
}
