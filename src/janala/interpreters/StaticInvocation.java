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
    public static Value invokeMethod(String owner, String name, Value[] args) {
        if (owner.equals("java/lang/Integer") && name.equals("valueOf")) {
            IntegerObjectValue ret = new IntegerObjectValue();
            if (args[0] instanceof IntValue) {
                ret.intValue = (IntValue)args[0];
                return ret;
            }
        }
        return PlaceHolder.instance;
    }
}
