/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.config;

public class Config {
    public static String analysisClass = System.getProperty("janala.analysisClass","janala/logger/DJVM");
    public static String traceFileName = System.getProperty("janala.trace","trace");
    public static String traceAuxFileName = System.getProperty("janala.trace.aux","trace.aux");
    public static String history = System.getProperty("janala.history","history");
    public static String inputs = System.getProperty("janala.inputs","inputs");
}
