/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.config;

import janala.solvers.Solver;

public class Config {
    public static final boolean printTrace = System.getProperty("janala.printTrace","false").equals("true");
    public static final String analysisClass = System.getProperty("janala.analysisClass","janala/logger/DJVM");
    public static final String traceFileName = System.getProperty("janala.trace","trace");
    public static final String traceAuxFileName = System.getProperty("janala.trace.aux","trace.aux");
    public static final String history = System.getProperty("janala.history","history");
    public static final String inputs = System.getProperty("janala.inputs","inputs");
    public static final String yicesCommand = System.getProperty("janala.yices", "yices");

    public static Solver getSolver() {
        try {
            Class solverClass = Class.forName(System.getProperty("janala.solver", "janala.solvers.YicesSolver"));
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
