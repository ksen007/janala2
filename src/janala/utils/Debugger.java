/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.utils;

public class Debugger {
	public static void Debug(DebugAction debugAction){
		debugAction.execute();
	}
}
