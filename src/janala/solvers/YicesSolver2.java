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
 * Date: 7/7/12
 * Time: 7:32 PM
 */
public class YicesSolver2 implements Solver {
    boolean first = true;
    ArrayList<Value> inputs;
    ArrayList<Constraint> constraints;
    private final static Logger logger = MyLogger.getLogger(YicesSolver.class.getName());
    private final static Logger tester = MyLogger.getTestLogger(Config.mainClass+"."+Config.iteration);

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
            PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(Config.formulaFile)));
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


            ProcessBuilder builder = new ProcessBuilder(new String[]{Config.yicesCommand,Config.formulaFile});
            builder.redirectErrorStream(true);
            Process process = builder.start();

            (new StreamGobbler(process.getErrorStream(),"ERROR")).start();

            boolean result = true;
            InputStream is = process.getInputStream();
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
                tester.log(Level.INFO,"-- Infeasible");
                logger.log(Level.INFO,"-- Infeasible");
                result = false;
                while ((line = br.readLine()) != null) { }
                br.close();
            } else {
                result = true;
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
                br.close();

                out = new PrintStream(
                        new BufferedOutputStream(
                                new FileOutputStream(Config.inputs)));
                int len = inputs.size();
                for(int i=0; i<len; i++) {
                    Long l = soln.get(i+1);
                    Value input = inputs.get(i);
                    if (l!=null) {
                        if (input instanceof janala.interpreters.StringValue) {
                            tester.log(Level.INFO, StringConstants.instance.get((int)(long)l));
                            out.println(StringConstants.instance.get((int)(long)l));
                        } else {
                            tester.log(Level.INFO,l+"");
                            out.println(l);
                        }
                    } else {
                        tester.log(Level.INFO,input.getConcrete().toString());
                        out.println(input.getConcrete());
                    }
                }
                out.close();
            }

            process.waitFor();
            return result;

        } catch (IOException ioe) {
            ioe.printStackTrace();
            logger.log(Level.SEVERE,"{0}",ioe);
            Runtime.getRuntime().halt(1);
            return false;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            logger.log(Level.SEVERE,"{0}",ie);
            Runtime.getRuntime().halt(1);
            return false;
        }
    }
}

class StreamGobbler extends Thread
{
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null) { }
                //System.out.println(type + ">" + line);
            } catch (IOException ioe)
              {
                ioe.printStackTrace();
              }
    }
}