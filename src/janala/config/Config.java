/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.config;

import janala.solvers.Solver;

public class Config {
    public static final String mainClass = System.getProperty("janala.mainClass",null);
    public static final int iteration = Integer.getInteger("janala.iteration",0);
    public static final boolean isTest = System.getProperty("janala.isTest","false").equals("true");

    public static final boolean verbose = System.getProperty("janala.verbose","false").equals("true");
    public static final boolean printTrace = System.getProperty("janala.printTrace","false").equals("true");
    public static final String analysisClass = System.getProperty("janala.analysisClass", "janala/logger/DJVM");
    public static final String traceFileName = System.getProperty("janala.trace","trace");
    public static final String traceAuxFileName = System.getProperty("janala.trace.aux","trace.aux");
    public static final String history = System.getProperty("janala.history","history");
    public static final String inputs = System.getProperty("janala.inputs","inputs");
    public static final String yicesCommand = System.getProperty("janala.yices", "yices");
    public static final String formulaFile = System.getProperty("janala.formulaFile", "formula");
    public static final String testLog = System.getProperty("janala.testLog", "test.log");
    public static String cvc3Command = System.getProperty("janala.cvc3", "cvc3");

    public static Solver getSolver() {
        try {
            Class solverClass = Class.forName(System.getProperty("janala.solver", "janala.solvers.YicesSolver2"));
            Solver ret = (Solver)solverClass.newInstance();
            return ret;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    //public static Solver solver = new ChocoSolver();
}
