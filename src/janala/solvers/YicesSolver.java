/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import janala.config.Config;
import janala.interpreters.Constraint;
import janala.interpreters.StringConstants;
import janala.interpreters.SymbolicInt;
import janala.interpreters.Value;
import janala.utils.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/24/12
 * Time: 12:10 PM
 */
public class YicesSolver implements Solver {
    boolean first = true;
    ArrayList<Value> inputs;
    ArrayList<Constraint> constraints;
    private final static Logger logger = MyLogger.getLogger(YicesSolver.class.getName());

    public void setInputs(ArrayList<Value> inputs) {
        this.inputs = inputs;
        this.first = true;
    }

    private void initSolver(Constraint c) {
        if (first) {
            first = false;
            constraints = new ArrayList<Constraint>();
            c.not();
        }
    }

    public void visitSymbolicInt(SymbolicInt c) {
        initSolver(c);
        logger.log(Level.INFO,"{0}",c);
        constraints.add(c);
    }


    public boolean solve() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(Config.yicesCommand);

            InputReader soln = new InputReader(proc.getInputStream(),inputs);
            PrintStream out = new PrintStream(new BufferedOutputStream(proc.getOutputStream()));

            int nInputs = inputs.size();
            for (int i = 0; i < nInputs; i++) {
                out.print("(define x");
                out.print(i+1);
                out.println("::int)");
            }
            for (Constraint next : constraints) {
                out.print("(assert ");
                next.print(out);
                out.println(")");
            }
            out.println("(check)");
            out.println("(show-model)");
            out.close();
            soln.start();
            proc.waitFor();
            soln.join();
            proc.getOutputStream().close();
            proc.getInputStream().close();
            proc.getErrorStream().close();
            proc.destroy();
            return soln.result;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Runtime.getRuntime().halt(1);
            return false;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            Runtime.getRuntime().halt(1);
            return false;
        }


    }





}

class InputReader extends Thread {
    private InputStream is;
    boolean result;
    ArrayList<Value> inputs;
    private final static Logger logger = MyLogger.getLogger(InputReader.class.getName());


    InputReader(InputStream is, ArrayList<Value> inputs) {
        this.is = is;
        this.inputs = inputs;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            line = br.readLine();
            if (!line.startsWith("sat")) {
                if (!line.startsWith("unsat")) {
                    logger.log(Level.SEVERE, "Call to Yices failed (concolic.yices = "
                            + Config.yicesCommand + ")");
                    Runtime.getRuntime().halt(1);
                }
                logger.log(Level.INFO,"-- Infeasible");
                this.result = false;
                return;
            }
            this.result = true;

            TreeMap<Integer,Long> soln = new TreeMap<Integer, Long>();
            while ((line = br.readLine()) != null) {
                if (line.startsWith("(")) {
                    String[] tokens = line.split(" ");
                    int xi = Integer.parseInt(tokens[1].substring(1));
                    String tmp = tokens[2].trim();
                    long val = Long.parseLong(tmp.substring(0, tmp.length() - 1));
                    soln.put(xi, val);
                }
            }

            PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(Config.inputs)));
            int len = inputs.size();
            for(int i=0; i<len; i++) {
                Long l = soln.get(i+1);
                Value input = inputs.get(i);
                if (l!=null) {
                    if (input instanceof janala.interpreters.StringValue) {
                        out.print(StringConstants.instance.get((int)(long)l));
                    } else {
                        out.println(l);
                    }
                } else {
                    out.println(input.getConcrete());
                }
            }
            out.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Runtime.getRuntime().halt(1);
        }
    }

}