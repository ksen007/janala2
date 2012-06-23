/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import janala.config.Config;
import janala.interpreters.Constraint;
import janala.interpreters.ConstraintAndResult;

import java.io.*;
import java.util.ArrayList;

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
                ex.printStackTrace();
            }
        }
        return ret;
    }

    public void checkAndSetBranch(ConstraintAndResult result) {
        BranchElement current;
        if (index < history.size()) {
            current = history.get(index);
            if (current.branch != result.result) {
                System.err.println("!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!!");
                int len = history.size();
                for (int j=len-1; j>index; j--) {
                    history.remove(j);
                }
                current.branch = result.result;
                current.done = false;
            }
        } else {
            current = new BranchElement(result.result,false,-1);
            history.add(current);
        }
        if (result.constraint!=null) {
            pathConstraint.add(result.constraint);
            current.pathConstraintIndex = pathConstraint.size()-1;
        } else {
            current.pathConstraintIndex = -1;
        }
        index++;
    }

    public void solveAndSave(ArrayList<Object> inputs) {
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

    private boolean solveAt(int pathConstraintIndex, ArrayList<Object> inputs) {
        solver.setInputs(inputs);
        for (int i=pathConstraintIndex; i>=0; i--) {
            pathConstraint.get(i).accept(solver);
        }
        return solver.solve();
    }

    private void removeHistory() {
        File f = new File(Config.history);
        f.delete();
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
            e.printStackTrace();
            System.exit(1);
        }

    }
}
