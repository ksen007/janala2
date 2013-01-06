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

    public void visitSymbolicIntCompare(SymbolicIntCompareConstraint c) {
        throw new RuntimeException("Unimplemented feature");
    }

    public void visitSymbolicOr(SymbolicOrConstraint c) {
        throw new RuntimeException("Unimplemented feature");
    }

    public void visitSymbolicAnd(SymbolicAndConstraint c) {
        throw new RuntimeException("Unimplemented feature");
    }

    public void visitSymbolicNot(SymbolicNotConstraint c) {
        throw new RuntimeException("Unimplemented feature");
    }

    public void visitSymbolicTrue(SymbolicTrueConstraint c) {
        throw new RuntimeException("Unsupported");
    }

    public void visitSymbolicFalse(SymbolicFalseConstraint c) {
        throw new RuntimeException("Unsupported");
    }


    public void visitSymbolicStringPredicate(SymbolicStringPredicate symbolicStringPredicate) {
        throw new RuntimeException("String functions and regular expressions are not supported with Choco solver");
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
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(Config.instance.yicesCommand);

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
                print(next,out);
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
            logger.log(Level.SEVERE,"",ioe);
            Runtime.getRuntime().halt(1);
            return false;
        } catch (InterruptedException ie) {
            logger.log(Level.SEVERE,"",ie);
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
    private final static Logger tester = MyLogger.getTestLogger(Config.mainClass+"."+Config.iteration);


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
                            + Config.instance.yicesCommand + ")");
                    Runtime.getRuntime().halt(1);
                }
                tester.log(Level.INFO,"-- Infeasible");
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
                            new FileOutputStream(Config.instance.inputs)));
            int len = inputs.size();
            for(int i=0; i<len; i++) {
                Long l = soln.get(i+1);
                Value input = inputs.get(i);
                if (l!=null) {
                    if (input instanceof janala.interpreters.StringValue) {
                        tester.log(Level.INFO,StringConstants.instance.get((int)(long)l));
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
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Runtime.getRuntime().halt(1);
        }
    }

}