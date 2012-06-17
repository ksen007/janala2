/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.config;

public class Config {
    public static String analysisClass = System.getProperty("janala.analysisClass",
            "janala/analysis/DJVM");
    public static String traceFileName = System.getProperty("janala.trace","trace");
    public static String traceAuxFileName = System.getProperty("janala.trace.aux","trace.aux");
}
