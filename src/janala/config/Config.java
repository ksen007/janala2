/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.config;

public class Config {
    public static String analysisClass = System.getProperty("janala.analysisClass",
            "janala/analysis/DJVM");
}
