/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.solvers;

import gnu.trove.iterator.TIntLongIterator;
import janala.config.Config;
import janala.interpreters.*;
import janala.utils.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/12/12
 * Time: 10:03 AM
 */
public class CVC3Solver implements Solver {
    boolean first = true;
    ArrayList<Value> inputs;
    ArrayList<Constraint> constraints;
    private final static Logger logger = MyLogger.getLogger(CVC3Solver.class.getName());
    private final static Logger tester = MyLogger.getTestLogger(Config.mainClass+"."+Config.iteration);

    public void setInputs(ArrayList<Value> inputs) {
        this.inputs = inputs;
        this.first = true;
    }

    private Constraint initSolver(Constraint c) {
        if (first) {
            first = false;
            constraints = new ArrayList<Constraint>();
            return c.not();
        }
        return c;
    }

    public void visitSymbolicInt(SymbolicInt c) {
        c = (SymbolicInt)initSolver(c);
        logger.log(Level.INFO,"{0}",c);
        constraints.add(c);
    }

    public void visitSymbolicOr(SymbolicOrConstraint c) {
        c = (SymbolicOrConstraint)initSolver(c);
        logger.log(Level.INFO,"{0}",c);
        constraints.add(c);
    }

    private void print(Constraint con, PrintStream out) {
        if (con instanceof SymbolicInt) {
            SymbolicInt c = (SymbolicInt)con;
            boolean first2 = true;
            for ( TIntLongIterator it = c.linear.iterator(); it.hasNext(); ) {
                it.advance();

                int integer = it.key();
                long l = it.value();
                if (first2) {
                    first2 = false;
                } else {
                    out.print(" + ");
                }
                out.print('x');
                out.print(integer);
                out.print("*(");
                out.print(l);
                out.print(')');

            }
            if (c.constant != 0) {
                out.print("+(");
                out.print(c.constant);
                out.print(')');
            }
            if (c.op == SymbolicInt.COMPARISON_OPS.EQ) {
                out.print(" = ");
            } else if (c.op == SymbolicInt.COMPARISON_OPS.NE) {
                out.print(" /= ");
            } else if (c.op == SymbolicInt.COMPARISON_OPS.LE) {
                out.print(" <= ");
            } else if (c.op == SymbolicInt.COMPARISON_OPS.LT) {
                out.print(" < ");
            } else if (c.op == SymbolicInt.COMPARISON_OPS.GE) {
                out.print(" >= ");
            } else if (c.op == SymbolicInt.COMPARISON_OPS.GT) {
                out.print(" > ");
            }
            out.print("0");
        } else if (con instanceof SymbolicOrConstraint) {
            SymbolicOrConstraint or = (SymbolicOrConstraint)con;

            if (or.isNegated) {
                out.print("NOT (");
            }
            boolean first2 = true;
            for(Constraint c:or.constraints) {
                if (first2) {
                    first2 = false;
                } else {
                    out.print(" OR ");
                }
                out.print("(");
                print(c,out);
                out.print(")");
            }
            if (or.constraints.isEmpty()) {
                out.print(" TRUE ");
            }
            if (or.isNegated) {
                out.print(")");
            }

        } else {
            throw new RuntimeException("Unimplemented constraint type "+con);
        }
    }

    public boolean solve() {
        try {
            PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(Config.instance.formulaFile)));
            int nInputs = inputs.size();
            for (int i = 0; i < nInputs; i++) {
                out.print("x");
                out.print(i+1);
                out.println(" : INT;");
            }
            Constraint last = null;
            for (Constraint next : constraints) {
                if (last==null) {
                    last = next;
                } else {
                    out.print("ASSERT ");
                    print(next,out);
                    out.println(";");
                }
            }
            if (last!=null) {
                out.print("CHECKSAT ");
                print(last,out);
                out.println(";");
            }
            out.println("COUNTERMODEL;");
            out.close();


            ProcessBuilder builder = new ProcessBuilder(new String[]{Config.instance.cvc3Command,Config.instance.formulaFile});
            builder.redirectErrorStream(true);
            Process process = builder.start();

            (new StreamGobbler(process.getErrorStream(),"ERROR")).start();

            boolean result = true;
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            line = br.readLine();
            if (!line.startsWith("Satisfiable")) {
                if (!line.contains("Unsat")) {
                    logger.log(Level.SEVERE,line);
                    logger.log(Level.SEVERE, "Call to CVC3 failed (concolic.cvc3 = "
                            + Config.instance.cvc3Command + ")");
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
                    if (line.startsWith("ASSERT")) {
                        String[] tokens = line.split(" ");
                        int xi = Integer.parseInt(tokens[1].substring(2));
                        String tmp = tokens[3].trim();
                        long val = Long.parseLong(tmp.substring(0, tmp.indexOf(")")));
                        soln.put(xi, val);
                    }
                }
                br.close();

                out = new PrintStream(
                        new BufferedOutputStream(
                                new FileOutputStream(Config.instance.inputs)));
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

