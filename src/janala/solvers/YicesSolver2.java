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

import gnu.trove.iterator.TIntLongIterator;
import janala.config.Config;
import janala.interpreters.*;
import janala.utils.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
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
    LinkedList<InputElement> inputs;
    ArrayList<Constraint> constraints;
    private final static Logger logger = MyLogger.getLogger(YicesSolver.class.getName());
    private final static Logger tester = MyLogger.getTestLogger(Config.mainClass+"."+Config.iteration);

    public void setInputs(LinkedList<InputElement> inputs) {
        this.inputs = inputs;
        this.first = true;
    }

    public void setPathConstraint(ArrayList<Constraint> pathConstraint) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPathConstraintIndex(int pathConstraintIndex) {
        //To change body of implemented methods use File | Settings | File Templates.
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
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    public void visitSymbolicIntCompare(SymbolicIntCompareConstraint c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    public void visitSymbolicOr(SymbolicOrConstraint c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    public void visitSymbolicAnd(SymbolicAndConstraint c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    public void visitSymbolicNot(SymbolicNotConstraint c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    public void visitSymbolicTrue(SymbolicTrueConstraint c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    public void visitSymbolicFalse(SymbolicFalseConstraint c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }


    public void visitSymbolicStringPredicate(SymbolicStringPredicate c) {
        Constraint c2 = initSolver(c);
        logger.log(Level.INFO,"{0}",c2);
        constraints.add(c2);
    }

    private void print(Constraint con, PrintStream out) {
        if (con instanceof SymbolicInt) {
            SymbolicInt c = (SymbolicInt)con;
            out.print("(");
            if (c.op == SymbolicInt.COMPARISON_OPS.EQ) {
                out.print("=");
            } else
            if (c.op == SymbolicInt.COMPARISON_OPS.NE) {
                out.print("/=");
            } else
            if (c.op == SymbolicInt.COMPARISON_OPS.LE) {
                out.print("<=");
            } else
            if (c.op == SymbolicInt.COMPARISON_OPS.LT) {
                out.print("<");
            } else
            if (c.op == SymbolicInt.COMPARISON_OPS.GE) {
                out.print(">=");
            } else
            if (c.op == SymbolicInt.COMPARISON_OPS.GT) {
                out.print(">");
            }
            out.print(" (+ ");
            for ( TIntLongIterator it = c.linear.iterator(); it.hasNext(); ) {
                it.advance();

                int key = it.key();
                long val = it.value();
                if (val < 0) {
                    out.print("(* (- 0 ");
                    out.print(-val);
                    out.print(") x");
                } else {
                    out.print("(* ");
                    out.print(val);
                    out.print(" x");
                }
                out.print(key);
                out.print(") ");
            }
            if (c.constant < 0) {
                out.print("(- 0 ");
                out.print(-c.constant);
                out.print(")");
            } else if (c.constant > 0) {
                out.print(c.constant);
            }
            out.print(") 0)");
        } else if (con instanceof SymbolicOrConstraint) {
            SymbolicOrConstraint or = (SymbolicOrConstraint)con;
            if (or.constraints.size()>1) {
                out.print("(or ");
            }
            for(Constraint c:or.constraints) {
                print(c,out);
                out.print(" ");
            }
            if (or.constraints.size()>1) {
                out.print(")");
            }
            if (or.constraints.isEmpty()) {
                out.print(" true ");
            }
        } else if (con instanceof SymbolicNotConstraint) {
            SymbolicNotConstraint not = (SymbolicNotConstraint)con;
            out.print("(NOT ");
            print(not.constraint,out);
            out.print(")");
        } else {
            throw new RuntimeException("Unimplemented constraint type "+con);
        }

    }

    public boolean solve() {
        try {
            PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(Config.instance.formulaFile)));
            int nInputs = inputs.size();
            for (int i = 0; i < nInputs; i++) {
                out.print("(define x");
                out.print(i+1);
                out.println("::int)");
            }
            for (Constraint next : constraints) {
                out.print("(assert ");
                print(next,out);
                out.println(")");
            }
            out.println("(check)");
            out.println("(show-model)");
            out.close();


            ProcessBuilder builder = new ProcessBuilder(new String[]{Config.instance.yicesCommand,Config.instance.formulaFile});
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
                if (!line.contains("unsat")) {
                    logger.log(Level.SEVERE,line);
                    logger.log(Level.SEVERE, "Call to Yices failed (concolic.yices = "
                            + Config.instance.yicesCommand + ")");
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
                                new FileOutputStream(Config.instance.inputs)));
                int len = inputs.size();
                for(int i=0; i<len; i++) {
                    Long l = soln.get(i+1);
                    Value input = inputs.get(i).value;
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