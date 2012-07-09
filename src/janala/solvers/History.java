/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import janala.config.Config;
import janala.interpreters.Constraint;
import janala.interpreters.ConstraintAndResult;
import janala.interpreters.Value;
import janala.utils.MyLogger;

import java.io.*;
import java.util.ArrayList;
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

    private History(Solver solver) {
        history = new ArrayList<BranchElement>(1024);
        pathConstraint = new ArrayList<Constraint>(1024);
        index = 0;
        this.solver = solver;
    }

    public static History readHistory(Solver solver) {
        ObjectInputStream inputStream = null;
        History ret = new History(solver);

        try {
            inputStream = new ObjectInputStream(new FileInputStream(Config.history));
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

    public void checkAndSetBranchIgnore(ConstraintAndResult result,int iid) {
        checkAndSetBranch(result,iid);
        history.get(index-1).ignore = true;
    }

    public void checkAndSetBranch(ConstraintAndResult result,int iid) {
        BranchElement current;
        if (index < history.size()) {
            current = history.get(index);
            if (!current.ignore && current.branch != result.result) {
                tester.log(Level.INFO,"Prediction failed");
                logger.log(Level.WARNING,"!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "+index+" history.size() "+history.size());
                logger.log(Level.WARNING,"At old iid "+current.iid+ " at iid "+iid+ " constraint "+result.constraint);
//                int i = 0;
//                for(BranchElement b:history) {
//                    System.out.println(i+" "+b);
//                    i++;
//                }
                int len = history.size();
                for (int j=len-1; j>index; j--) {
                    history.remove(j);
                }
                current.branch = result.result;
                current.done = false;
                current.iid = iid;
            }
        } else {
            current = new BranchElement(result.result,false,-1,iid);
            history.add(current);
        }
        if (result.constraint!=null) {
            result.constraint.iid = iid;
            result.constraint.index = index;
            pathConstraint.add(result.constraint);
            current.pathConstraintIndex = pathConstraint.size()-1;
        } else {
            current.pathConstraintIndex = -1;
        }
        current.branch = result.result;
        index++;
    }

    public void solveAndSave(ArrayList<Value> inputs) {
        for (int i=index-1; i>=0; i--) {
            BranchElement current = history.get(i);
            if (!current.done && current.pathConstraintIndex != -1) {
                if (solveAt(current.pathConstraintIndex,inputs)) {
                    writeHistory(i);
                    return;
                }
            }
        }
        removeHistory();
    }

    private boolean solveAt(int pathConstraintIndex, ArrayList<Value> inputs) {
        solver.setInputs(inputs);
        for (int i=pathConstraintIndex; i>=0; i--) {
            pathConstraint.get(i).accept(solver);
        }
        return solver.solve();
    }

    private void removeHistory() {
        File f = new File(Config.history);
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
            outputStream = new ObjectOutputStream(new FileOutputStream(Config.history));
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
        if (current.pathConstraintIndex!=-1) {
            return  pathConstraint.remove(pathConstraint.size()-1);
        }
        return null;
    }
}
