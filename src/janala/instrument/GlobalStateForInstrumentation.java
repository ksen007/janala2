/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.instrument;

public class GlobalStateForInstrumentation {
    public static GlobalStateForInstrumentation instance = new GlobalStateForInstrumentation();
    private int iid = 0;
    private int mid = 0;

//    private TObjectIntHashMap<String> classNameToInternalID = new TObjectIntHashMap<String>();

    public int getIid(int line) {
        //System.out.println("iid="+iid+" line="+line);
        return iid++;
    }

    public int getMid() {
        return mid;
    }

    public void incMid() {
        mid++;
    }
}
