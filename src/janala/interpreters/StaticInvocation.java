/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/1/12
 * Time: 9:56 AM
 */
public class StaticInvocation {
    public static Value invokeMethod(String owner, String name, Value[] tmpValues) {
        return PlaceHolder.instance;
    }
}
